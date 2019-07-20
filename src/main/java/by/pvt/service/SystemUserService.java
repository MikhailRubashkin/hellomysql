package by.pvt.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import by.pvt.dao.SystemUsersMapper;
import by.pvt.dto.SystemUsers;


class SystemUsersService {

    private static Logger log = Logger.getLogger(SystemUsersService.class.getName());

    private SqlSessionFactory sqlSessionFactory;

    public SystemUsersService() {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(
                    Resources.getResourceAsStream("by/pvt/service/mybatis-config.xml")
            );
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    protected void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<SystemUsers> getSystemUsers() {
        SqlSession session = sqlSessionFactory.openSession();
        SystemUsersMapper dao =
                session.getMapper(SystemUsersMapper.class);

        List<SystemUsers> dtoUsers
                = dao.selectByExample(null);

        session.close();
        return dtoUsers;
    }

    public void add(SystemUsers systemUser) {
        SqlSession session = sqlSessionFactory.openSession();
        SystemUsersMapper dao = session.getMapper(SystemUsersMapper.class);

        int result = dao.insert(systemUser);
        log.info("Added new systemUser with result=" + result);

        session.commit();
        session.close();
    }

    public void addAll(List<SystemUsers> systemUsers) {
        if (systemUsers == null) {
            log.info("The input systemUsers is null");
            return;
        }
        SqlSession session = sqlSessionFactory.openSession();
        if (session == null) {
            log.info("Session is null");
            return;
        }
        SystemUsersMapper dao = session.getMapper(SystemUsersMapper.class);
        try {
            systemUsers.stream()
                    .filter(Objects::nonNull)
                    .forEach(dao::insert);
            session.commit();
        } catch (Exception e) {
            log.log(Level.WARNING, e.getMessage(), e);
            session.rollback();
        } finally {
            session.close();
        }
    }

}