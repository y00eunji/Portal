package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StrategyStatement {
    PreparedStatement makeStatement(Connection connection) throws SQLException;
}
