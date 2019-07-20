package by.pvt.service;

import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.*;

import by.pvt.HelloMysqlTest;
import by.pvt.dto.SystemUsers;

/**
 * @author alve
 */
public class SystemUserServiceTest extends DBTestCase {

    SystemUsersService objUnderTest;

    public SystemUserServiceTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/hello_mysql_junit");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "pristijpriborF1539249f");

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(HelloMysqlTest.class.getResourceAsStream("system_users.xml"));
    }

    @Test
    public void testGetSystemUsers() throws Exception {
        // given
        objUnderTest = new SystemUsersService();
        objUnderTest.setSqlSessionFactory(
                new SqlSessionFactoryBuilder().build(
                        Resources.getResourceAsStream("by/pvt/service/mybatis-config-junit.xml")

                ));

        // when
        List<SystemUsers> list = objUnderTest.getSystemUsers();

        // then
        assertEquals(4, list.size());
    }

    @Test
    public void add() {
        // given

        // when
        //objUnderTest.

        // then
        //assertEquals(true, false);
    }

    @Test
    public void addAll() {
        // given

        // when
        //objUnderTest.

        // then
        //assertEquals(true, false);
    }


}