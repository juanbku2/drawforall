package com.drawsforall.usermanagement.rest;

import com.drawsforall.usermanagement.business.UserService;
import com.drawsforall.usermanagement.rest.dto.PagedUsersDTO;
import com.drawsforall.usermanagement.rest.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
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

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(produces = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE})
    //@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public PagedUsersDTO getUsers(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "id,ascending") String sortBy,
            @RequestParam(name = "by", defaultValue = "all") String by,
            @RequestParam(name = "value", defaultValue = "") String value

    ) {
        return userService.getUsers(page, size, by, value, sortBy);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public UserDTO lookupUser(
            @PathVariable Long id) {
        return userService.lookupUser(id);
    }

    @PostMapping(value = "/create", produces = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE}, consumes = {"multipart/form-data"})
    public UserDTO createUser(@ModelAttribute("userDTO") UserDTO userDTO, @RequestParam(value = "image") MultipartFile image) throws IOException {
        UserDTO createdUser = userService.createUser(userDTO, image);
        return createdUser;
    }


    @GetMapping(value = "/current", produces = APPLICATION_JSON_VALUE)
    public Authentication currentUser() {
        return userService.currentUser();
    }


}
