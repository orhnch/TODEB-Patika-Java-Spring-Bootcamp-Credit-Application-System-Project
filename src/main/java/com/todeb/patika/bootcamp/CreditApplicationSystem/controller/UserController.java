package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.payload.UserSignUpPayloadDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.payload.UserLoginPayloadDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.response.GlobalResponse;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.response.JwtResponse;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.response.UserResponseDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.User;
import com.todeb.patika.bootcamp.CreditApplicationSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {


    private final UserService userService;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<GlobalResponse> getAllUsers() {

        return ResponseEntity.ok(userService.getAll());
    }


    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody UserLoginPayloadDTO userLoginPayloadDTO) {
        return ResponseEntity.ok(userService.signin(userLoginPayloadDTO));
    }


    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody @Valid UserSignUpPayloadDTO userSignUpPayloadDTO) {
        User user = modelMapper.map(userSignUpPayloadDTO,User.class);
        return ResponseEntity.ok(userService.signup(user, false));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{username}")
    public ResponseEntity<String> delete(@PathVariable String username) {
        userService.delete(username);
        return ResponseEntity.ok(username + " was deleted successfully");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/search/{username}")
    public ResponseEntity<UserResponseDTO> searchByUserName(@PathVariable String username) {
        return ResponseEntity.ok(userService.search(username));
    }

}
