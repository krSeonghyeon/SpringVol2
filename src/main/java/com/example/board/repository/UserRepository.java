package com.example.board.repository;

import com.example.board.DBConnection.DBConnectionManager;
import com.example.board.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class UserRepository {
    public UserDTO insertUser(UserDTO user) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into users(username, userid, userpw) values(?, ?, ?)";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getUserid());
            statement.setString(3, user.getUserpw());

            statement.executeUpdate();
            return user;

        } catch (SQLException e) {
            log.error("insertUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);
        }
    }

    public UserDTO getUserByUserId(String userid) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE userid = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, userid);

            rs = statement.executeQuery();
            UserDTO userDTO = new UserDTO();

            while(rs.next()) {
                userDTO.setUsername(rs.getString("username"));
                userDTO.setUserid(rs.getString("userid"));
                userDTO.setUserpw(rs.getString("userpw"));
            }
            return userDTO;
        }
        catch (SQLException e) {
            log.error("getUser error={}", e);
            throw e;
        }
        finally { // 항상 실행됨
            closeResource(connection, statement, rs);
        }
    }

    public List<UserDTO> getAllUsers() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            rs = statement.executeQuery();
            List<UserDTO> users = new ArrayList<>();

            while(rs.next()) {
                users.add(new UserDTO(rs.getString("username"),
                        rs.getString("userid"), rs.getString("userpw")));
            }
            return users;
        }
        catch (SQLException e) {
            log.error("getAllUser error={}", e);
            throw e;
        }
        finally { // 항상 실행됨
            closeResource(connection, statement, rs);
        }
    }
    public void updateUserPw(String userid, UserDTO user) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "UPDATE users SET userpw = ? where userid = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, user.getUserpw());
            statement.setString(2, userid);

            statement.executeUpdate();
        }
        catch (SQLException e) {
            log.error("updateUserPw error={}", e);
            throw e;
        }
        finally {
            closeResource(connection, statement, null);
        }
    }

    public void deleteUser(String userid) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from users where userid = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, userid);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);
        }

    }

    private void closeResource(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        //반환할 때는 반드시 역순으로 반환해야 함.
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
    }
}
