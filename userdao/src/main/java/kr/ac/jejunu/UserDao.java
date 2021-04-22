package kr.ac.jejunu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;

//나는 주석이다
@Component
public class UserDao {
//    private final JdbcTemplate jdbcTemplate;
//
//    public UserDao(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {

    }

    public User findById(Integer id) throws ClassNotFoundException, SQLException {
        String sql = "select * from userinfo where id = ?";
        Object[] param = new Object[]{id};
        return jdbcTemplate.query(sql, resultSet -> {
            User user = null;
            if (resultSet.next()) {

                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
            return user;
        }, id);
    }

    public void insert(User user) throws ClassNotFoundException, SQLException {
        String sql = "insert into userinfo (name, password) values ( ?, ? )";
        Object[] param = new Object[]{user.getName(), user.getPassword()};
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    sql
                    , Statement.RETURN_GENERATED_KEYS
            );
            for (int i = 0; i < param.length; i++) {
                preparedStatement.setObject(i + 1, param[i]);
            }
            return preparedStatement;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
    }

    public void update(User user) throws SQLException {
        String sql = "update userinfo set name = ?, password = ? where id = ?";
        Object[] param = new Object[]{user.getName(), user.getPassword(), user.getId()};
        jdbcTemplate.update(sql, param);

    }

    public void delete(Integer id) throws SQLException {
        String sql = "delete from userinfo where id = ?";
        Object[] param = new Object[]{id};
        jdbcTemplate.update(sql, param);

    }

}


