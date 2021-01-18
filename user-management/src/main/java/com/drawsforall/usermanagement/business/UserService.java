package com.drawsforall.usermanagement.business;


import com.drawsforall.usermanagement.configsecurity.AuthenticationService;
import com.drawsforall.usermanagement.persistance.RoleRepository;
import com.drawsforall.usermanagement.persistance.UserRepository;
import com.drawsforall.usermanagement.persistance.entity.Role;
import com.drawsforall.usermanagement.persistance.entity.RoleType;
import com.drawsforall.usermanagement.persistance.entity.User;
import com.drawsforall.usermanagement.rest.dto.PagedUsersDTO;
import com.drawsforall.usermanagement.rest.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;


@Transactional
@Service
public class UserService {

    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final AuthenticationService authenticationService;


    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.authenticationService = authenticationService;
    }

    public PagedUsersDTO getUsers(int pageNo, int pageSize) {
        Page<User> pagedResult = userRepository.findAll(PageRequest.of(pageNo, pageSize));


        return userMapper.toPagedUsersDTO(pagedResult);
    }

    public UserDTO lookupUser(Long value) {
        User user;
        UserDTO userDTO;

        user = userRepository.findById(value).orElseThrow(()
                -> new IllegalArgumentException("the next id doest not exist"));
        userDTO = userMapper.toUserDTO(user);

        return userDTO;
    }

    public UserDTO createUser(UserDTO userDTO, MultipartFile file) throws IOException {

        userRepository.findByUsername(userDTO.getUsername()).ifPresent(user -> {
            throw new IllegalArgumentException("the next user already not exist");
        });
        userDTO.setPicByte(file.getBytes());


        User user = userMapper.fromUserDTO(userDTO);
        boolean isAnonymousAuthentication = authenticationService.isAnonymousAuthentication();
        if (userDTO.getRoles() == null) {
            userDTO.setRoles(Collections.singletonList("ROLE_USER"));
        }
        Set<Role> roles = isAnonymousAuthentication
                ? roleRepository.findAllByNameIn(Collections.singletonList(RoleType.ROLE_USER.name()))
                : roleRepository.findAllByNameIn(userDTO.getRoles());

        user.setRoles(roles);


        userRepository.save(user);

        return userMapper.toUserDTO(user);
    }

    public Authentication currentUser() {

        return authenticationService.getAuthentication();
    }


}