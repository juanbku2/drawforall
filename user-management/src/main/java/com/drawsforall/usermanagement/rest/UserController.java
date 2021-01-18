package com.drawsforall.usermanagement.rest;

import com.drawsforall.usermanagement.business.UserService;
import com.drawsforall.usermanagement.rest.dto.PagedUsersDTO;
import com.drawsforall.usermanagement.rest.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


@EnableSpringDataWebSupport
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(produces = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public PagedUsersDTO getUsers(@RequestParam(defaultValue = "0") Integer pageNo,
                                  @RequestParam(defaultValue = "5") Integer pageSize) {
        return userService.getUsers(pageNo, pageSize);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public UserDTO lookupUser(
            @PathVariable Long id) {
        return userService.lookupUser(id);
    }

    @PostMapping(produces = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE}, consumes = {"multipart/form-data"})
    public UserDTO createUser(@ModelAttribute("userDTO") UserDTO userDTO, @RequestParam(value = "image") MultipartFile image) throws IOException {
        UserDTO createdUser = userService.createUser(userDTO, image);
        return createdUser;
    }


    @GetMapping(value = "/current", produces = APPLICATION_JSON_VALUE)
    public Authentication currentUser() {
        return userService.currentUser();
    }


}
