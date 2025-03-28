package com.library.app.service.impl;

import com.library.app.entity.User;
import com.library.app.dto.ResponseDTO;
import com.library.app.dto.UserDTO;
import com.library.app.exception.DuplicateEntityException;
import com.library.app.mapper.UserMapper;
import com.library.app.repository.UserRepository;
import com.library.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseDTO<UserDTO> save(UserDTO userDTO) {
        if(userRepository.findByUsername(userDTO.getUsername()).isEmpty())
            userRepository.save(userMapper.toEntity(userDTO));
        else
            throw new DuplicateEntityException("User already exists!");

        return new ResponseDTO<>(true, "User saved!");
    }

    @Override
    public ResponseDTO<UserDTO> findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User " + username + " doesn't exists!"));
        return new ResponseDTO<>(true, "", userMapper.toDTO(user));
    }
}
