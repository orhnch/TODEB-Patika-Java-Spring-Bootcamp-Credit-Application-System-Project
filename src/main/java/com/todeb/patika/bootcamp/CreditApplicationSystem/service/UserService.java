package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.CustomJwtException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.exception.EntityNotFoundException;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.payload.UserLoginPayloadDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.response.GlobalResponse;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.response.JwtResponse;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.response.UserResponseDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.Role;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.User;
import com.todeb.patika.bootcamp.CreditApplicationSystem.repository.UserRepository;
import com.todeb.patika.bootcamp.CreditApplicationSystem.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    public GlobalResponse getAll() {
        List<User> userList = userRepository.findAll();
        GlobalResponse globalResponse = new GlobalResponse();
        globalResponse.setData(userList.stream()
                .map(x -> modelMapper.map(x, UserResponseDTO.class))
                .collect(Collectors.toList()));
        return globalResponse;
    }

    public JwtResponse signin(UserLoginPayloadDTO userLoginPayloadDTO) {
        try {
            JwtResponse jwtResponse = new JwtResponse();
            User user = userRepository.findByUsername(userLoginPayloadDTO.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginPayloadDTO.getUsername(), userLoginPayloadDTO.getPassword()));
            jwtResponse.setAccessToken(jwtTokenProvider.createToken(userLoginPayloadDTO.getUsername(), user.getRoles()));
            jwtResponse.setId(user.getId());
            jwtResponse.setRefreshToken("");
            return jwtResponse;

        } catch (AuthenticationException e) {
            throw new CustomJwtException("Invalid username/password supplied", HttpStatus.BAD_REQUEST);
        }
    }

    public JwtResponse signup(User user, boolean isAdmin) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            JwtResponse jwtResponse = new JwtResponse();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = isAdmin ? Role.ROLE_ADMIN : Role.ROLE_CUSTOMER;
            user.setRoles(Collections.singletonList(role));
            userRepository.save(user);
            jwtResponse.setAccessToken(jwtTokenProvider.createToken(user.getUsername(), user.getRoles()));
            jwtResponse.setId(user.getId());
            jwtResponse.setRefreshToken("");
            return jwtResponse;
        } else {
            throw new CustomJwtException("Username is already in use", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(String username) {
        User byUsername = userRepository.findByUsername(username);
        if (byUsername == null) {
            throw new EntityNotFoundException("User", "username : " + username);
        } else if (byUsername.getRoles().contains(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("No permission to delete user : " + username);
        }
        userRepository.deleteByUsername(username);
    }

    public UserResponseDTO search(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomJwtException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return modelMapper.map(user, UserResponseDTO.class);
    }
}
