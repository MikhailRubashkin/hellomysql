package by.pvt.service;

import by.pvt.dto.SystemUsers;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.maven.model.Resource;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SystemUserServiceTest extends DBTestCase {

    @Override
    protected IDataSet getDataSet() throws Exception {
        return null;
    }

    public SystemUserServiceTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/hello_mysql_junit?createDatabaseIfNotExist=true&amp;useSSL=false");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "pristijpriborF1539249f");
    }



    @Before
    public void setUp() throws Exception {
        objUderTest = new SystemUserService();
        objUderTest.setSqlSessionFactory(
                new SqlSessionFactoryBuilder().build(
                        Resource.getResourceAsStream("by/pvt/service/mibatis-congig.xml")
                )
        );

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getSystemUsers() {
        objUnderTest.getSystemUser();
        List<SystemUsers> list = objUnderTest.getSystemUsers();
    }

    @Test
    public void add() {
    }

    @Test
    public void addAll() {
    }
}