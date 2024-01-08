package com.example.board.service;

import com.example.board.domain.User;
import com.example.board.jwt.JwtToken;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    public User insertUser(User user) throws SQLException;
    public List<User> getAllUsers() throws SQLException;
    public User getUserByUserId(String userId) throws SQLException;
    public void updateUserPw(String userId, User user) throws SQLException;
    public void deleteUser(String userId) throws SQLException;
    public JwtToken login(String id, String password);
}
