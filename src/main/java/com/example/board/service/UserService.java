package com.example.board.service;

import com.example.board.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    public UserDTO insertUser(UserDTO user) throws SQLException;
    public List<UserDTO> getAllUsers() throws SQLException;
    public UserDTO getUserByUserId(String userId) throws SQLException;
    public void updateUserPw(String userId, UserDTO user) throws SQLException;
    public void deleteUser(String userId) throws SQLException;
}
