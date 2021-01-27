package com.drawsforall.usermanagement.business;

import com.drawsforall.usermanagement.persistance.entity.User;
import com.drawsforall.usermanagement.rest.UserController;
import com.drawsforall.usermanagement.rest.dto.PagedUsersDTO;
import com.drawsforall.usermanagement.rest.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "password", qualifiedByName = "setEncodedPassword")
    })
    default UserDTO toUserDTO(User user) {
        UserDTO userDTO = UserDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .picByte(decompressBytes(user.getPicByte()))
                .roles(user.getRoles().stream()
                        .map(role -> role.getName().toString())
                        .collect(Collectors.toList()))
                .build();

        userDTO.add(buildLinksToUserDTO(user));


        return userDTO;
    }

    List<UserDTO> toUserDTO(List<User> users);


    @Mappings({
            @Mapping(target = "id", source = "userId"),
            @Mapping(target = "password", qualifiedByName = "setDecodedPassword"),
            @Mapping(target = "picByte", qualifiedByName = "compressBytes"),
            @Mapping(target = "roles", ignore = true)
    })
    User fromUserDTO(UserDTO userDTO);

    default PagedUsersDTO toPagedUsersDTO(Page<User> pagedUsers, String by, String value) {
        Page<UserDTO> page = pagedUsers.map(this::toUserDTO);
        PagedUsersDTO pagedUsersDTO = PagedUsersDTO.builder()
                .elements(page.getContent())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .numberOfElements(page.getNumberOfElements())
                .currentPage(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .sort(page.getSort().toString())
                .by(by)
                .value(value)
                .build();

        pagedUsersDTO.add(buildLinksToPagedUsersDTO(pagedUsers.getNumber(), pagedUsers.getSize(),
                pagedUsers.hasNext(), pagedUsers.hasPrevious(), pagedUsers.getSort().toString(), by, value));
        return pagedUsersDTO;
    }


    default List<Link> buildLinksToUserDTO(User user) {
        Link all = linkTo(methodOn(UserController.class).getUsers(null, null, null, null, null)).withRel("All");
        Link self = linkTo(methodOn(UserController.class).lookupUser(user.getId())).withRel("Self");
        return Arrays.asList(all, self);
    }

    default List<Link> buildLinksToPagedUsersDTO(int pageNo, int pageSize, boolean hasNext, boolean hasPrevious, String sort,String by, String value) {
        List<Link> links = new ArrayList<>();
        String fixedLink = sort.replaceAll(": DESC",",descending");

        if(sort.contains(": ASC")){
            fixedLink = sort.replaceAll(": ASC",",ascending");
        }

        Link selfRel = linkTo(methodOn(UserController.class).getUsers(pageNo, pageSize, fixedLink, by, value)).withSelfRel();
        links.add(selfRel);

        if (hasNext) {
            Link nextPage = linkTo(methodOn(UserController.class).getUsers(pageNo + 1, pageSize, fixedLink, by, value)).withRel("nextPage");
            links.add(nextPage);
        }

        if (hasPrevious) {
            Link previousPage = linkTo(methodOn(UserController.class).getUsers(pageNo - 1, pageSize, fixedLink, by, value)).withRel("previousPage");
            links.add(previousPage);
        }

        return links;
    }


    @Named("setEncodedPassword")
    default String setEncodedPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    @Named("setDecodedPassword")
    default String setDecodedPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    // compress the image bytes before storing it in the database
    @Named("compressBytes")
    default byte[] compressBytes(byte[] data) {
        if (data == null) {
            return null;
        }
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    default byte[] decompressBytes(byte[] data) {
        if (data == null) {
            return null;
        }
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }


}
