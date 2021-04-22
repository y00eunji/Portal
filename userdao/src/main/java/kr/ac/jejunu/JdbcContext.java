package kr.ac.jejunu;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcContext {
    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    User JdbcContextForFindById(StrategyStatement strategyStatement) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = strategyStatement.makeStatement(connection);

            //쿼리 실행하기
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }

        } finally {
            try {
                resultSet.close();
            } catch (Exception throwables) { //null point까지 받아야대서 일반 exception
                throwables.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }

        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
        return user;
    }

    void jdbcContextForInsert(User user, StrategyStatement strategyStatement) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = strategyStatement.makeStatement(connection);


            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            user.setId(resultSet.getInt(1));
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    void jdbcContextForUpdate(StrategyStatement strategyStatement) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = strategyStatement.makeStatement(connection);


            preparedStatement.executeUpdate();
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
    }

    void jdbcContextForDelete(StrategyStatement strategyStatement) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = strategyStatement.makeStatement(connection);


            preparedStatement.executeUpdate();
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
    }

    User findbyid(String sql, Object[] param) throws SQLException {
        return JdbcContextForFindById(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql
            );
            for (int i = 0; i < param.length; i++) {
                preparedStatement.setObject(i + 1, param[i]);
            }
            return preparedStatement;
        });
    }

    void insert(User user, String sql, Object[] param, UserDao userDao) throws SQLException {
        jdbcContextForInsert(user, connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql
                    , Statement.RETURN_GENERATED_KEYS

            );
            for (int i = 0; i < param.length; i++) {
                preparedStatement.setObject(i + 1, param[i]);
            }
            return preparedStatement;

        });
    }

    void update(String sql, Object[] param) throws SQLException {
        jdbcContextForUpdate(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql
            );
            for (int i = 0; i < param.length; i++) {
                preparedStatement.setObject(i + 1, param[i]);
            }
            return preparedStatement;
        });
    }
}