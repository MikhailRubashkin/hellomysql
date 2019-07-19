package by.pvt.service;

import java.io.IOException;
import java.util.Date;
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


 public class SystemUserService {

    private static Logger log = Logger.getLogger(SystemUserService.class.getName());

    private SqlSessionFactory sqlSessionFactory;

    public SystemUserService() {
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

     public static void main(String[] args) {
        SystemUsers systemUsers = new SystemUsers();
        systemUsers.setId(2);
        systemUsers.setUsername("User2");
        systemUsers.setActive(false);
        systemUsers.setDateofbirth(new Date());

        new SystemUserService().add(systemUsers);

        new SystemUserService()
                .getSystemUsers()
                .forEach(user ->
                        log.info(user.getId() + " "
                                + user.getUsername() + " "
                                + user.getActive()));
    }

    public List<SystemUsers> getSystemUsers() {
        return sqlSessionFactory
                .openSession()
                .getMapper(SystemUsersMapper.class)
                .selectByExample(null);
    }

    public void add(SystemUsers systemUser) {
        SqlSession session = sqlSessionFactory.openSession();
        SystemUsersMapper dao = session.getMapper(SystemUsersMapper.class);
         int result = sqlSessionFactory
                 .openSession()
                 .getMapper(SystemUsersMapper.class)
                 .insert(systemUser);

         log.info("Added new systemUser with result=" + result);

        session.commit();
        session.close();
     }

     public void  addAll(List<SystemUserService> systemUsers) {

         if (systemUsers == null) {
             log.info("The input ...");
             return;
         }

         SqlSession session = sqlSessionFactory.openSession();
         SystemUsersMapper dao = session.getMapper(SystemUsersMapper.class);
         try {
             systemUsers.stream()
                     .filter(Objects::nonNull);
                     .forEach(dao::insert);
             session.commit();
         } catch (Exception e) {
             e.printStackTrace();
             session.rollback();
         } finally {
             session.close();
         }


            systemUsers.forEach(dao::insert);
            session.commit();
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }
     }


