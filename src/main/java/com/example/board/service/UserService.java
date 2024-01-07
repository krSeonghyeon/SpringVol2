package com.example.board.service;

import com.example.board.domain.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserService {
    public User insertUser(User user) throws SQLException;
    public List<User> getAllUsers() throws SQLException;
    public User getUserByUserId(String userId) throws SQLException;
    public void updateUserPw(String userId, User user) throws SQLException;
    public void deleteUser(String userId) throws SQLException;
    public String signIn(User request) throws Exception;
}
