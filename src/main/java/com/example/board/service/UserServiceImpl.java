package com.example.board.service;

import com.example.board.domain.User;
import com.example.board.jwt.JwtToken;
import com.example.board.jwt.JwtTokenProvider;
import com.example.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder encoder, UserRepository repository, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
        this.encoder = encoder;
        this.userRepository = repository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public User insertUser(User user) throws SQLException { // 등록
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() throws SQLException { // 전체조회
        return userRepository.findAll();
    }

    @Override
    public User getUserByUserId(String userId) throws SQLException { // 특정조회
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void updateUserPw(String userId, User user) throws SQLException { // 수정
        User userInf = userRepository.findById(userId).orElse(null);
        if(userInf != null) {
            userInf.setUserpw(user.getUserpw());
            userRepository.save(userInf);
        }
    }

    @Override
    public void deleteUser(String userId) throws SQLException { // 삭제
        userRepository.deleteById(userId);
    }

    @Override
    public JwtToken login(String id, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtToken token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
}
