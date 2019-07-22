package by.pvt;


import by.pvt.dto.SystemUsers;
import by.pvt.service.SystemUserService;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Test;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HelloMysqlTest extends DBTestCase {

    private static Logger log = Logger.getLogger(SystemUserService.class.getName());


    public HelloMysqlTest(String name) {

        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
                "jdbc:mysql://localhost:3306/hello_mysql_junit?createDatabaseIfNotExist=true&amp;useSSL=false");
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
                             .getConnection("jdbc:mysql://localhost:3306/hello_mysql_junit?createDatabaseIfNotExist=true&amp;useSSL=false",
                                     "root", "pristijpriborF1539249f");
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
            assertEquals(2, activeUser);

            rs.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Test
    public void testUpdate() {
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/hello_mysql",
                        "root", "pristijpriborF1539249f")
        ) {
            SystemUsers systemUser = new SystemUsers();
            systemUser.setId(1);
            systemUser.setUsername("Test-updated");
            systemUser.setActive(true);
            new SystemUserService().update(systemUser);

            PreparedStatement ps = connection.prepareStatement
                    ("select * from system_users where id=1");
            ResultSet rs = ps.executeQuery();

            int id = 0;
            String username = "";
            boolean active = true;

            while (rs.next()) {
                id = rs.getInt("id");
                username = rs.getString("username");
                active = rs.getBoolean("active");
            }
            assertNotNull(systemUser);
            assertEquals(1, id);
            assertEquals("Test-updated", username);
            assertEquals(true, active);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    @Test
    public void testDelete() {
        try {
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/hello_mysql",
                            "root", "pristijpriborF1539249f");
            new SystemUserService().delete(4);
            PreparedStatement ps = connection
                    .prepareStatement("select * from system_users");
            ResultSet rs = ps.executeQuery();

            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }
            assertEquals(3, rowCount);

            SystemUsers systemUser = new SystemUsers();
            Assert.assertNotNull(systemUser);

            rs.close();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Test
    public void testInsert() {
        try(Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/hello_mysql",
                        "root", "pristijpriborF1539249f")){

            SystemUsers systemUser = new SystemUsers();
            systemUser.setId(8);
            systemUser.setUsername("Test-Insert");
            systemUser.setActive(false);
            new SystemUserService().insert(systemUser);

            PreparedStatement ps = connection.prepareStatement
                    ("select * from system_users where id=8");
            ResultSet rs = ps.executeQuery();

            int id = 0;
            String username = "";
            boolean active = true;
            while (rs.next()) {
                id = rs.getInt("id");
                username = rs.getString("username");
                active = rs.getBoolean("active");
            }
            assertNotNull(systemUser);
            assertEquals(8, id);
            assertEquals("Test-Insert", username);
            assertEquals(false, active);

            ps.close();
            rs.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}