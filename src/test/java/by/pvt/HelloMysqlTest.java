package by.pvt;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import by.pvt.dto.SystemUsers;
import by.pvt.service.SystemUserService;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.*;


public class HelloMysqlTest extends DBTestCase {
    private SystemUsers systemUsers;

    public HelloMysqlTest(String name) {

        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/hello_mysql_junit?createDatabaseIfNotExist=true&amp;useSSL=false");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "pristijpriborF1539249f");
    }


    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(HelloMysqlTest.class.getResourceAsStream("system_users.xml"));


    }

    @Test
    public void testConnection() {
        try (Connection connection =
                     DriverManager
                             .getConnection("jdbc:mysql://localhost:3306/hello_mysql_junit?createDatabaseIfNotExist=true&amp;useSSL=false", "root", "pristijpriborF1539249f");
             PreparedStatement ps = connection.prepareStatement("select * from system_users");
        ) {
            ResultSet rs = ps.executeQuery();
            assertNotNull(rs);

            int rawCount = 0;
            int activeUser = 0;

            while (rs.next()) {
                rawCount++;
                if (rs.getBoolean("active")) activeUser++;
            }
            assertEquals(4, rawCount);
            assertEquals(3, activeUser);

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

























    /*@Test
    public void testInsert() {
        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/hello_mysql?createDatabaseIfNotExist=true&amp;useSSL=false", "root", "pristijpriborF1539249f");

            SystemUsers systemUser = new SystemUsers();
            systemUser.setId(2);
            systemUser.setUsername("user2");
            systemUser.setActive(false);
            systemUser.setDateofbirth(new Date());
            new SystemUserService().add(systemUser);

            PreparedStatement ps = connection.prepareStatement("select * from system_users where id=2");
            ResultSet rs = ps.executeQuery();

            int id = 0;
            String username = "";
            boolean active = true;

            while (rs.next()) {
                id = rs.getInt("id");
                username = rs.getString("username");
                active = rs.getBoolean("active");
            }

            assertEquals(2, id);
            assertEquals("user2", username);
            assertEquals(false, active);

            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

   /* @Test
    public void testUpdate(){
        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/hello_mysql?createDatabaseIfNotExist=true&amp;useSSL=false", "root", "pristijpriborF1539249f");

            SystemUsers systemUser = new SystemUsers();
            systemUser.setId(2);
            systemUser.setUsername("user2tested");
            systemUser.setActive(true);
           // new SystemUserService().update(systemUser);

            PreparedStatement ps = connection.prepareStatement("UPDATE system_users SET username =?, active=? WHERE id=?");
            ResultSet rs = ps.executeQuery();

            int id = 2;
            String username = "Administrator2";
            boolean active = true;

            while (rs.next()) {
                id = rs.getInt("id");
                username = rs.getString("username");
                active = rs.getBoolean("active");
            }

            assertEquals(2, id);
            assertEquals("user6tested", username);
            assertEquals(true, active);

            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateUser() {
        SystemUsers user = new SystemUsers();
        user.setId(2);
        user.setUsername("update");
        user.setActive(true);
       // final SystemUsers newUser = systemUsers.addUser(user);
       // Assert.assertNotNull(newUser);
       // user.setId(newUser.getId());
        user.setUsername("updateTest");
        user.setId(2);
       //systemUsers.updateUser(user);
       ///Assert.assertNotNull(foundUser);
       // Assert.assertEquals(foundUser, user);
       // systemUsers.deleteUser(user.getId());
    }*/

    /*@Test
    public void testDelete(){
        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/hello_mysql?createDatabaseIfNotExist=true&amp;useSSL=false", "root", "pristijpriborF1539249f");

            new SystemUserService().delete(2);

            PreparedStatement ps = connection.prepareStatement("select * from system_users");
            ResultSet rs = ps.executeQuery();

            int rowCount = 0;

            while (rs.next()) {
                rowCount++;
            }

            assertEquals(2, rowCount);

            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }*/




