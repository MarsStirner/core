package ru.korus.tmis.ws.users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.joda.time.DateTime;
import org.joda.time.Days;

import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.RbPost;
import ru.korus.tmis.core.entity.model.Role;
import ru.korus.tmis.core.entity.model.Speciality;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.entity.model.UUID;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 23.04.13, 13:42 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

@Stateless
public class UsersMgr {

    private int MAX_CONNECTIONS = 1024 * 256;

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

    public String auth(final String login, final String password) {
        String res = null;

        List<Staff> users =
                database.getEntityMgr().createQuery("SELECT s FROM Staff s WHERE s.login  = :login", Staff.class).setParameter("login", login).getResultList();

        if (users == null || users.isEmpty()) {
            return error("User does not exists");
        }

        users =
                database.getEntityMgr()
                        .createQuery("SELECT s FROM Staff s WHERE s.login  = :login AND s.password = :password AND s.deleted = 0", Staff.class)
                        .setParameter("login", login).setParameter("password", getMD5(password))
                        .getResultList();
        if (users != null && !users.isEmpty()) {
            final String uuid = addToConnectionList(users.get(0));
            res = String.format("{\"OK\":True, \"type\": \"Basic\", \"token\": \"%s\" }", uuid);
        }
        else {
            res = error("Username or password are incorrect");
        }

        return res;
    }

    public List<JsonPerson> getAll() {
        List<Staff> users = database.getEntityMgr().createQuery("SELECT s FROM Staff s WHERE s.deleted = 0", Staff.class)
                .getResultList();
        List<JsonPerson> res = new LinkedList<JsonPerson>();
        for (Staff staff : users) {
            JsonPerson newPers = getJsonPerson(staff);
            res.add(newPers);
        }
        return res;
    }

    private JsonPerson getJsonPerson(Staff staff) {
        JsonPerson res = new JsonPerson();
        res.setFname(staff.getFirstName());
        res.setPname(staff.getPatrName());
        res.setLname(staff.getLastName());
        if (staff.getPost() != null) {
            res.setPosition(staff.getPost().getName());
        }
        if (staff.getOrgStructure() != null) {
            res.setSubdivision(staff.getOrgStructure().getUuid().getUuid());
        }
        res.setLogin(staff.getLogin());
        List<String> roleCodes = new LinkedList<String>();
        for (Role role : staff.getRoles()) {
            roleCodes.add(role.getCode());
        }
        res.setRoles(roleCodes);
        return res;
    }

    public JsonPerson getByUUID(String token) {
        JsonPerson res = null;
        Staff user = getStaffByUUID(token);
        if (user != null) {
            return getJsonPerson(user);
        }
        return res;
    }

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
        if (days > 0 || connectionsTime.size() > MAX_CONNECTIONS) {
            connections.remove(connectionsTime.getFirst().uuid);
            connectionsTime.pop();
        }
        return uuid;
    }

    public String create(JsonPerson jsonNewPerson) {
        String res = null;
        final boolean isUsed = isLoginUsed(jsonNewPerson.getLogin());
        if (isUsed) {
            return error("The user's login already exists");
        }

        Staff newStaff = new Staff();
        final RbPost post = getPost(jsonNewPerson.getPosition());
        if (post == null) {
            return error(String.format("The position '%s' is not exists", jsonNewPerson.getPosition()));
        }

        OrgStructure orgStruct = getOrgStrucureByUUID(jsonNewPerson.getSubdivision());
        if (orgStruct == null) {
            return error(String.format("The subdivision with UUID '%s' is not exists", jsonNewPerson.getSubdivision()));
        }
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
        newStaff.setPost(post);
        newStaff.setSpeciality(database.getEntityMgr().find(Speciality.class, 1));
        newStaff.setOrgStructure(orgStruct);
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
     * @param login
     * @return
     */
    public boolean isLoginUsed(final String login) {
        final List<Staff> users = database.getEntityMgr().createQuery("SELECT s FROM Staff s WHERE s.login = :login", Staff.class)
                .setParameter("login", login).getResultList();
        return users != null && !users.isEmpty();
    }

    public void updateStaff(Staff newStaff, JsonPerson jsonNewPerson) {
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
            newStaff.setPassword(getMD5(jsonNewPerson.getPassword()));
        }
        if (jsonNewPerson.getRoles() != null && !jsonNewPerson.getRoles().isEmpty()) {
            newStaff.setRoles(getRoles(jsonNewPerson.getRoles()));
        }
        if (jsonNewPerson.getLogin() != null && !isLoginUsed(jsonNewPerson.getLogin())) {
            newStaff.setLogin(jsonNewPerson.getLogin());
        }
        database.getEntityMgr().flush();

    }

    public void deleteStaff(Staff staff) {
        staff.setDeleted(true);
        database.getEntityMgr().flush();
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

    private String getMD5(final String pass) {
        try {
            byte[] md5sum = MessageDigest.getInstance("MD5").digest(pass.getBytes());
            return (new HexBinaryAdapter()).marshal(md5sum);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
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
        return "{“OK”: True}";
    }
}
