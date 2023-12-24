package com.example.board.service;

import com.example.board.dto.UserDTO;
import com.example.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO insertUser(UserDTO user) throws SQLException { // 등록
        return userRepository.insertUser(user);
    }

    @Override
    public List<UserDTO> getAllUsers() throws SQLException { // 전체조회
        return userRepository.getAllUsers();
    }

    @Override
    public UserDTO getUserByUserId(String userId) throws SQLException { // 특정조회
        return userRepository.getUserByUserId(userId);
    }

    @Override
    public void updateUserPw(String userId, UserDTO user) throws SQLException { // 수정
        userRepository.updateUserPw(userId, user);
    }

    @Override
    public void deleteUser(String userId) throws SQLException { // 삭제
        userRepository.deleteUser(userId);
    }
}
