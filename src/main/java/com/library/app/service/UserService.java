package com.library.app.service;

import com.library.app.dto.BookDTO;
import com.library.app.dto.ResponseDTO;
import com.library.app.dto.UserDTO;

public interface UserService {
    ResponseDTO<UserDTO> save(UserDTO userDTO);
    ResponseDTO<UserDTO> findByUsername(String username);


}
