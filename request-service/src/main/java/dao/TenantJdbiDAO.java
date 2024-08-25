package dao;

import domain.Tenant;
import helpers.ScryptHelper;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Collection;

public interface TenantJdbiDAO extends TenantDAO {

    @Override
    @SqlQuery("SELECT EXISTS (SELECT * FROM Tenant "
            + "WHERE Username = :username AND Password = :password)")
    public boolean checkTenantUsernamePassword(@Bind("username") String username, @Bind("password") String password, Tenant tenant);

//    @Override - does not work check with Mark this week??
//    default boolean checkTenantUsernamePassword(String username, String password, Tenant tenant){
//        String hash = getTenantByUsername(username).getPassword();
//        // check the hash against the hash version of the password, the cust provided.
//        return ScryptHelper.check(hash, password);
//    }

    @Override
    @SqlQuery("SELECT * FROM Tenant ORDER BY username")
    @RegisterBeanMapper(Tenant.class)
    public Collection<Tenant> getAllTenants();

    @Override
    @SqlUpdate("DELETE FROM Tenant WHERE username = :username")
    public void removeTenant(@BindBean Tenant tenant);

    @Override
    @SqlUpdate("INSERT INTO Tenant (firstName, lastName, phoneNumber, username, password) " +
            "VALUES(:firstName, :lastName, :phoneNumber, :username, :password)")
    public void saveTenant(@BindBean Tenant tenant);

    @Override
    @SqlQuery("SELECT * FROM Tenant WHERE username = :username")
    @RegisterBeanMapper(Tenant.class)
    public Tenant getTenantByUsername(@Bind("username") String username);
}
