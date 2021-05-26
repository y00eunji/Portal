package kr.ac.jejunu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

public class UserDaoTest {

    static UserDao userDao;

    @BeforeAll
    public static void setup()  {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext("kr.ac.jejunu");
        userDao = applicationContext.getBean("userDao", UserDao.class);
    }

    @Test
    public void get() throws SQLException, ClassNotFoundException {
        Integer id = 3;
        String name = "hulk";
        String password = "1234";

//        DaoFactory daoFactory = new DaoFactory();
//        UserDao userDao = daoFactory.getUserDao();

        User user = userDao.findById(id);

        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }

    @Test
    public void insert() throws SQLException, ClassNotFoundException {
        String name = "eunji3";
        String password = "12249";

        //builer사용
        User user= User.builder().name(name).password(password).build();

//        User user = new User();
//        user.setName(name);
//        user.setPassword(password);
//        DaoFactory daoFactory = new DaoFactory();
//        UserDao userDao = daoFactory.getUserDao();
        userDao.insert(user);


        User insertedUser = userDao.findById(user.getId());

        assertThat(user.getId(), greaterThan(0));
        assertThat(insertedUser.getId(), is(user.getId()));
        assertThat(insertedUser.getName(), is(user.getName()));
        assertThat(insertedUser.getPassword(), is(user.getPassword()));
    }

    @Test
    public void update() throws SQLException, ClassNotFoundException {
        String name = "eunji11";
        String password = "1234";

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDao.insert(user);

        user.setName("hulk");
        user.setPassword("1234");

        userDao.update(user);

        User updatedUser = userDao.findById(user.getId());

        assertThat(updatedUser.getId(), is(user.getId()));
        assertThat(updatedUser.getName(), is(user.getName()));
        assertThat(updatedUser.getPassword(), is(user.getPassword()));
    }

    @Test
    public void delete() throws SQLException, ClassNotFoundException {
        String name = "eunji11";
        String password = "1234";

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDao.insert(user);

        userDao.delete(user.getId());

        User deletedUser = userDao.findById(user.getId());

        assertThat(deletedUser, nullValue());
    }
//    @Test
//    public void getHalla() throws SQLException, ClassNotFoundException {
//        Integer id = 1;
//        String name = "eunji";
//        String password = "1234";
//
//        UserDao userDao = new UserDao(new HallaconnectionMaker());
//        User user = userDao.findById(id);
//
//        assertThat(user.getId(), is(id));
//        assertThat(user.getName(), is(name));
//        assertThat(user.getPassword(), is(password));
//    }
//    @Test
//    public void insertHalla() throws SQLException, ClassNotFoundException {
//        String name = "eunji";
//        String password = "1234";
//
//        User user = new User();
//        user.setName(name);
//        user.setPassword(password);
//        UserDao userDao = new UserDao(new HallaconnectionMaker());
//        userDao.insert(user);
//
//        User insertedUser = userDao.findById(user.getId());
//
//        assertThat(user.getId(), greaterThan(0));
//        assertThat(insertedUser.getId(), is(user.getId()));
//        assertThat(insertedUser.getName(), is(user.getName()));
//        assertThat(insertedUser.getPassword(), is(user.getPassword()));
//    }

}

