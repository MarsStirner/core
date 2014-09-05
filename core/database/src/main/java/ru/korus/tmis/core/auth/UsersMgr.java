package ru.korus.tmis.core.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.joda.time.DateTime;
import org.joda.time.Days;

import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.RbPost;
import ru.korus.tmis.core.entity.model.Role;
import ru.korus.tmis.core.entity.model.Speciality;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.entity.model.UUID;
import ru.korus.tmis.scala.util.ConfigManager;
import ru.korus.tmis.util.TextUtils;
import scala.actors.threadpool.Arrays;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 23.04.13, 13:42 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

@Stateless
public class UsersMgr implements UsersMgrLocal {

    private final int MAX_CONNECTIONS;
    private final int KEEPALIVE_DAYS;

    {
        MAX_CONNECTIONS = ConfigManager.UsersMgr().MaxConnections();
        KEEPALIVE_DAYS = Math.max(0, ConfigManager.UsersMgr().KeepAliveDays() - 1);
    }
    @EJB
    private Database database = null;

    private static Map<String, Integer> connections = new HashMap<String, Integer>();
    private static LinkedList<Pair> connectionsTime = new LinkedList<Pair>();

    private class Pair {
        private final String uuid;
        private final DateTime datetime;

        private Pair(final String uuid, final DateTime datetime) {
            this.uuid = uuid;
            this.datetime = datetime;
        }
    }

    @Override
    public String auth(final String login, final String password) {
        String res = null;

        List<Staff> users =
                database.getEntityMgr().createQuery("SELECT s FROM Staff s WHERE s.login  = :login", Staff.class).setParameter("login", login).getResultList();

        if (users == null || users.isEmpty()) {
            return errorUserNotFound();
        }

        users =
                database.getEntityMgr()
                        .createQuery("SELECT s FROM Staff s WHERE s.login  = :login AND s.password = :password AND s.deleted = 0", Staff.class)
                        .setParameter("login", login).setParameter("password", TextUtils.getMD5(password))
                        .getResultList();
        if (users != null && !users.isEmpty()) {
            final String uuid = addToConnectionList(users.get(0));
            res = String.format("{\"OK\":\"True\", \"type\": \"Basic\", \"token\": \"%s\" }", uuid);
        }
        else {
            res = error("Username or password are incorrect");
        }

        return res;
    }

    static public String errorUserNotFound() {
        return error("User does not exists");
    }

    @Override
    public List<JsonPerson> getAll() {
        List<Staff> users = database.getEntityMgr().createQuery("SELECT s FROM Staff s WHERE s.deleted = 0", Staff.class)
                .getResultList();
        List<JsonPerson> res = new LinkedList<JsonPerson>();
        for (Staff staff : users) {
            res.add(JsonPerson.create(staff));
        }
        return res;
    }

    @Override
    public JsonPerson getByUUID(String token) {
        JsonPerson res = null;
        Staff user = getStaffByUUID(token);
        if (user != null) {
            return JsonPerson.create(user);
        }
        return res;
    }

    @Override
    public Staff getStaffByUUID(String token) {
        Integer id = connections.get(token);
        if (id != null) {
            return database.getEntityMgr().find(Staff.class, id);
        }
        return null;
    }

    private String addToConnectionList(Staff user) {
        final String uuid = user.getUuid().getUuid();
        connections.put(uuid, user.getId());
        connectionsTime.add(new Pair(uuid, new DateTime()));
        DateTime now = new DateTime();
        int days = Days.daysBetween(((Pair) connectionsTime.getFirst()).datetime, now).getDays();
        if (days > KEEPALIVE_DAYS || connectionsTime.size() > MAX_CONNECTIONS) {
            connections.remove(connectionsTime.getFirst().uuid);
            connectionsTime.pop();
        }
        return uuid;
    }

    @Override
    public String create(JsonPerson jsonNewPerson) {
        String res = null;
        final boolean isUsed = isLoginUsed(jsonNewPerson.getLogin());
        if (isUsed) {
            return error("The user's login already exists");
        }

        Staff newStaff = new Staff();

        updateStaff(newStaff, jsonNewPerson);

        Date now = new Date();
        Staff coreStaff = database.getCoreUser();
        newStaff.setCreateDatetime(now);
        newStaff.setCreatePerson(coreStaff);
        newStaff.setModifyDatetime(now);
        newStaff.setModifyPerson(coreStaff);
        newStaff.setDeleted(false);
        newStaff.setCode("code");
        newStaff.setFederalCode("");
        newStaff.setRegionalCode("");
        newStaff.setSpeciality(database.getEntityMgr().find(Speciality.class, 1));
        newStaff.setOffice("");
        newStaff.setOffice2("");
        newStaff.setAmbPlan((short) 0);
        newStaff.setAmbPlan2((short) 0);
        newStaff.setAmbNorm((short) 0);
        newStaff.setHomPlan((short) 0);
        newStaff.setHomPlan2((short) 0);
        newStaff.setHomNorm((short) 0);
        newStaff.setExpPlan((short) 0);
        newStaff.setExpNorm((short) 0);
        newStaff.setRetired(false);
        newStaff.setBirthDate(new Date(0));
        newStaff.setBirthPlace("");
        newStaff.setSex((short) 0);
        newStaff.setSnils("");
        newStaff.setInn("");
        newStaff.setAvailableForExternal(0);
        newStaff.setPrimaryQuota((short) 50);
        newStaff.setOwnQuota((short) 25);
        newStaff.setConsultancyQuota((short) 25);
        newStaff.setExternalQuota((short) 10);
        newStaff.setLastAccessibleTimelineDate(null);
        newStaff.setTimelineAccessibleDays(0);
        newStaff.setTypeTimeLinePerson(0);
        newStaff.setUuid(createNewUUID());
        database.getEntityMgr().persist(newStaff);
        database.getEntityMgr().flush();
        res = String.format("{\"OK\": \"True\", \"uuid\": \"%s\"}", newStaff.getUuid().getUuid());
        return res;
    }

    /**
     * @param jsonNewPerson
     * @return
     */
    protected RbPost createPost(JsonPerson jsonNewPerson) {
        RbPost post;
        post = new RbPost();
        post.setName(jsonNewPerson.getPosition());
        post.setCode(jsonNewPerson.getCode());
        database.getEntityMgr().persist(post);
        return post;
    }

    /**
     * @param login
     * @return
     */
    @Override
    public boolean isLoginUsed(final String login) {
        final List<Staff> users = database.getEntityMgr().createQuery("SELECT s FROM Staff s WHERE s.login = :login", Staff.class)
                .setParameter("login", login).getResultList();
        return users != null && !users.isEmpty();
    }

    @Override
    public String updateStaff(Staff newStaff, JsonPerson jsonNewPerson) {

        OrgStructure orgStruct = null;
        if (jsonNewPerson.getSubdivision() != null) {
            orgStruct = getOrgStrucureByUUID(jsonNewPerson.getSubdivision());
            if (orgStruct == null) {
                return error(String.format("The subdivision with UUID '%s' is not exists", jsonNewPerson.getSubdivision()));
            }
        }

        RbPost post = null;
        if (jsonNewPerson.getPosition() != null) {
            post = getPost(jsonNewPerson.getPosition());
            if (post == null) {
                if (jsonNewPerson.getCode() == null) {
                    return error("The position code required");
                }
                post = createPost(jsonNewPerson);
            }
        }

        if (post != null) {
            newStaff.setPost(post);
        }
        if (orgStruct != null) {
            newStaff.setOrgStructure(orgStruct);
        }
        if (jsonNewPerson.getLname() != null) {
            newStaff.setLastName(jsonNewPerson.getLname());
        }
        if (jsonNewPerson.getFname() != null) {
            newStaff.setFirstName(jsonNewPerson.getFname());
        }
        if (jsonNewPerson.getPname() != null) {
            newStaff.setPatrName(jsonNewPerson.getPname());
        }
        if (jsonNewPerson.getPassword() != null) {
            newStaff.setPassword(TextUtils.getMD5(jsonNewPerson.getPassword()).toLowerCase());
        }
        @SuppressWarnings("unchecked")
        Set<Role> roles = getRoles(Arrays.asList(new String[] { ROLE_GUEST }));
        if (jsonNewPerson.getRoles() != null && !jsonNewPerson.getRoles().isEmpty()) {
            final Set<Role> requestRoles = getRoles(jsonNewPerson.getRoles());
            if (!requestRoles.isEmpty()) {
                roles = requestRoles;
            }
        }
        newStaff.setRoles(roles);
        newStaff.setUserProfileId(roles.iterator().next());
        if (jsonNewPerson.getLogin() != null && !isLoginUsed(jsonNewPerson.getLogin())) {
            newStaff.setLogin(jsonNewPerson.getLogin());
        }
        database.getEntityMgr().flush();

        return UsersMgr.ok();
    }

    @Override
    public void deleteStaff(Staff staff, String token) {
        staff.setDeleted(true);
        database.getEntityMgr().flush();
        connections.remove(token);
    }

    private UUID createNewUUID() {
        UUID res = new UUID();
        res.setUuid(java.util.UUID.randomUUID().toString());
        database.getEntityMgr().persist(res);
        return res;
    }

    private Set<Role> getRoles(List<String> roleCodes) {
        Set<Role> res = new HashSet<Role>();
        for (String code : roleCodes) {
            final List<Role> roles = database.getEntityMgr().createQuery("SELECT r FROM Role r WHERE r.code = :code", Role.class)
                    .setParameter("code", code).getResultList();
            if (roles != null && !roles.isEmpty()) {
                res.add(roles.get(0));
            }
        }
        return res;
    }

    private OrgStructure getOrgStrucureByUUID(String subdivisionUUID) {
        final List<OrgStructure> orgStructures =
                database.getEntityMgr().createQuery("SELECT s FROM OrgStructure s WHERE s.uuid.uuid = :sUUID", OrgStructure.class)
                        .setParameter("sUUID", subdivisionUUID).getResultList();
        if (orgStructures != null && !orgStructures.isEmpty()) {
            return orgStructures.get(0);
        }
        return null;
    }

    private RbPost getPost(final String postName) {
        final List<RbPost> posts = database.getEntityMgr().createQuery("SELECT p FROM RbPost p WHERE p.name = :postName", RbPost.class)
                .setParameter("postName", postName).getResultList();
        if (posts != null && !posts.isEmpty()) {
            return posts.get(0);
        }
        return null;
    }

    public static String error(final String errMsg) {
        return String.format("{\"OK\": \"False\", \"error\": [\"%s.\"]}", errMsg);
    }

    public static String ok() {
        return "{\"OK\": \"True\"}";
    }
}
