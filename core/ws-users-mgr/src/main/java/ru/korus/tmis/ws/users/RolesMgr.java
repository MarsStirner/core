package ru.korus.tmis.ws.users;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.PersonProfile;
import ru.korus.tmis.core.entity.model.Role;
import ru.korus.tmis.core.entity.model.Staff;

import com.google.gson.Gson;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.05.2013, 9:41:54 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
@Stateless
public class RolesMgr {

    /**
     * 
     */

    @EJB
    private Database database = null;

    static private class Roles {
        private List<JsonRole> roles;
    }

    static public class UsersByRole {
        private List<JsonPerson> users;
    }

    /**
     * @param jsonRole
     * @return
     */
    public String create(final JsonRole jsonRole) {

        if (!getRoleByCode(jsonRole.getCode()).isEmpty()) {
            return UsersMgr.error(String.format("The role with code '%s' already exist", jsonRole.getCode()));
        }

        if (!getRoleByName(jsonRole.getTitle()).isEmpty()) {
            return UsersMgr.error(String.format("The role with title '%s' already exist", jsonRole.getTitle()));
        }

        Role role = new Role();
        role.setCode(jsonRole.getCode());
        role.setName(jsonRole.getTitle());
        database.getEntityMgr().persist(role);
        database.getEntityMgr().flush();
        return UsersMgr.ok();
    }

    /**
     * @param jsonRole
     * @return
     */
    private List<Role> getRoleByName(final String name) {
        return database.getEntityMgr().createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class).
                setParameter("name", name).getResultList();
    }

    /**
     * @return
     */
    public String getAll() {
        List<Role> roles = database.getEntityMgr().createQuery("SELECT r FROM Role r", Role.class).getResultList();
        return new Gson().toJson(getRolesInfo(roles));
    }

    /**
     * @param roles
     * @return
     */
    protected Roles getRolesInfo(List<Role> roles) {
        Roles res = new Roles();
        res.roles = new LinkedList<JsonRole>();
        for (Role role : roles) {
            JsonRole newRole = new JsonRole();
            newRole.setCode(role.getCode());
            newRole.setTitle(role.getName());
            res.roles.add(newRole);
        }
        return res;
    }

    /**
     * @param code
     * @return
     */
    private List<Role> getRoleByCode(final String code) {
        return database.getEntityMgr().createQuery("SELECT r FROM Role r WHERE r.code = :code", Role.class).
                setParameter("code", code).getResultList();
    }

    /**
     * @param code
     * @return
     */
    public String getRoleInfo(String code) {
        List<Role> roles = getRoleByCode(code);
        String res = "";
        if (roles.isEmpty()) {
            res = errorRoleNotFound(code);
        } else {
            JsonRole role = new JsonRole();
            role.setCode(roles.iterator().next().getCode());
            role.setTitle(roles.iterator().next().getName());
            res = new Gson().toJson(role);
        }
        return res;
    }

    /**
     * @param code
     * @return
     */
    private String errorRoleNotFound(String code) {
        return UsersMgr.error(String.format("The role with code '%s' not found", code));
    }

    /**
     * @param code
     * @param jsonRole
     * @return
     */
    public String updateRoleInfo(String code, JsonRole jsonRole) {
        final List<Role> roles = getRoleByCode(code);
        if (roles.isEmpty()) {
            return errorRoleNotFound(jsonRole.getCode());
        }
        final Role role = roles.get(0);
        if (jsonRole.getCode() != null && !jsonRole.getCode().equals(role.getCode()) && !getRoleByCode(jsonRole.getCode()).isEmpty()) {
            return UsersMgr.error(String.format("The role code '%s' already exist", jsonRole.getCode()));
        }
        if (jsonRole.getTitle() != null && !jsonRole.getTitle().equals(role.getName()) && !getRoleByName(jsonRole.getTitle()).isEmpty()) {
            return UsersMgr.error(String.format("The role title '%s' already exist", jsonRole.getTitle()));
        }

        if (jsonRole.getCode() != null) {
            role.setCode(jsonRole.getCode());
        }
        if (jsonRole.getTitle() != null) {
            role.setName(jsonRole.getTitle());
        }
        database.getEntityMgr().flush();
        return UsersMgr.ok();
    }

    /**
     * @param code
     * @return
     */
    public String deleteRole(String code) {
        if (UsersMgr.ROLE_GUEST.equals(code)) {
            return UsersMgr.error("The role 'guest' cannot be deleted");
        }
        final Role role = getRoleByCode(code).iterator().next();
        if (role == null) {
            return errorRoleNotFound(code);
        }

        final Role guest = getRoleByCode(UsersMgr.ROLE_GUEST).iterator().next();
        if (guest == null) {
            return UsersMgr.error("The role 'guest' is not set");
        }

        final List<PersonProfile> personProfiles =
                database.getEntityMgr().createQuery("SELECT pp FROM PersonProfile pp WHERE pp.role.code = :code", PersonProfile.class).
                        setParameter("code", code).getResultList();

        removeProfiles(guest, personProfiles);

        database.getEntityMgr().flush();

        final List<Staff> staffs = database.getEntityMgr().createQuery("SELECT s FROM Staff s WHERE s.userProfileId.code = :code", Staff.class).
                setParameter("code", code).getResultList();

        for (Staff staff : staffs) {
            staff.setUserProfileId(database.getEntityMgr().createQuery("SELECT pp FROM PersonProfile pp WHERE pp.staff.id = :personId", PersonProfile.class)
                    .setParameter("personId", staff.getId())
                    .getResultList().iterator().next().getRole());
        }

        database.getEntityMgr().remove(role);
        database.getEntityMgr().flush();
        return UsersMgr.ok();
    }

    /**
     * @param guest
     * @param personProfiles
     */
    protected void removeProfiles(final Role guest, final List<PersonProfile> personProfiles) {
        for (PersonProfile personProfile : personProfiles) {
            final Staff staff1 = personProfile.getStaff();
            if (database.getEntityMgr().createQuery("SELECT pp FROM PersonProfile pp WHERE pp.staff.id = :personId", PersonProfile.class).
                    setParameter("personId", staff1.getId()).getResultList().size() <= 1) {
                addPersonProfile(guest, staff1);
            }
            database.getEntityMgr().remove(personProfile);
        }
    }

    /**
     * @param guest
     * @param personProfile
     */
    protected void addGuest(final Role guest, PersonProfile personProfile) {
        final Staff staff = personProfile.getStaff();
        if (database.getEntityMgr().createQuery("SELECT pp FROM PersonProfile pp WHERE pp.staff.id = :personId", PersonProfile.class).
                setParameter("personId", staff.getId()).getResultList().size() <= 1) {
            addPersonProfile(guest, staff);
        }
    }

    /**
     * @param role
     * @param staff
     */
    protected void addPersonProfile(final Role role, final Staff staff) {
        PersonProfile guestForPerson = new PersonProfile();
        guestForPerson.setStaff(staff);
        guestForPerson.setRole(role);
        database.getEntityMgr().persist(guestForPerson);
    }

    /**
     * @param code
     * @return
     */
    public String getUsersByCode(String code) {
        final List<Role> roles = getRoleByCode(code);
        if (roles.isEmpty()) {
            return errorRoleNotFound(code);
        }
        final List<PersonProfile> personProfiles =
                database.getEntityMgr().createQuery("SELECT pp FROM PersonProfile pp WHERE pp.role.code = :code", PersonProfile.class).
                        setParameter("code", code).getResultList();
        UsersByRole res = new UsersByRole();
        res.users = new LinkedList<JsonPerson>();
        for (PersonProfile personProfile : personProfiles) {
            if (personProfile.getStaff() != null) {
                res.users.add(JsonPerson.create(personProfile.getStaff()));
            }
        }

        return new Gson().toJson(res);
    }

    /**
     * @param jsonRoleMgr
     * @return
     */
    public Object addRole(JsonRoleMgr jsonRoleMgr) {
        final List<Role> roles = getRoleByCode(jsonRoleMgr.getRoleCode());
        if (roles.isEmpty()) {
            return errorRoleNotFound(jsonRoleMgr.getRoleCode());
        }
        final Role role = roles.iterator().next();

        final String personUuid = jsonRoleMgr.getPersonUuid();
        final List<Staff> staffs = database.getEntityMgr().createQuery("SELECT s FROM Staff s WHERE s.uuid.uuid = :uuid AND s.deleted = 0", Staff.class).
                setParameter("uuid", personUuid).getResultList();
        if (staffs.isEmpty()) {
            return UsersMgr.errorUserNotFound();
        }
        final Staff staff = staffs.iterator().next();

        addPersonProfile(role, staff);

        return UsersMgr.ok();
    }

    /**
     * @param code
     * @return
     */
    public String getRolesByUser(String token) {

        final List<PersonProfile> personProfiles =
                database.getEntityMgr()
                        .createQuery("SELECT pp FROM PersonProfile pp WHERE pp.staff.uuid.uuid = :uuid AND pp.staff.deleted = 0", PersonProfile.class).
                        setParameter("uuid", token).getResultList();
        if (personProfiles.isEmpty()) {
            return UsersMgr.errorUserNotFound();
        }

        final List<Role> roles = new LinkedList<Role>();
        for (PersonProfile personProfile : personProfiles) {
            roles.add(personProfile.getRole());
        }

        return new Gson().toJson(getRolesInfo(roles));
    }

    /**
     * @param token
     * @param code
     * @return
     */
    public Object removeRolesForUser(String token, String code) {
        final Role guest = getRoleByCode(UsersMgr.ROLE_GUEST).iterator().next();
        if (guest == null) {
            return UsersMgr.error("The role 'guest' is not set");
        }
        final List<PersonProfile> personProfiles =
                database.getEntityMgr()
                        .createQuery("SELECT pp FROM PersonProfile pp WHERE pp.staff.uuid.uuid = :uuid AND pp.staff.deleted = 0 AND pp.role.code = :code",
                                PersonProfile.class).
                        setParameter("uuid", token).setParameter("code", code).getResultList();
        if (personProfiles.isEmpty()) {
            return UsersMgr.error("User or role does not exists");
        }

        removeProfiles(guest, personProfiles);

        return UsersMgr.ok();
    }
}
