package com.drawsforall.usermanagement.rest.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO extends RepresentationModel<UserDTO> {

    private Long userId;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private byte[] picByte;
    private List<String> roles;


}
