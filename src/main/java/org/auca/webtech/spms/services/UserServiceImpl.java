package com.elysee.springapp.dukachallenge.services;

import com.elysee.springapp.dukachallenge.domain.TaskOwner;
import com.elysee.springapp.dukachallenge.exceptions.UserWithProvidedEmailExistException;
import com.elysee.springapp.dukachallenge.payloads.JwtAccessTokenResponsePayload;
import com.elysee.springapp.dukachallenge.payloads.request.LoginPayload;
import com.elysee.springapp.dukachallenge.payloads.request.SignupPayload;
import com.elysee.springapp.dukachallenge.repository.TaskOwnerRepository;
import com.elysee.springapp.dukachallenge.security.ApplicationSecurityUser;
import com.elysee.springapp.dukachallenge.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TaskOwnerServiceImpl implements TaskOwnerService{

    private final TaskOwnerRepository ownerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils utils;
    private final PasswordEncoder encoder;

    public TaskOwnerServiceImpl(TaskOwnerRepository ownerRepository, AuthenticationManager authenticationManager, JwtUtils utils, PasswordEncoder encoder) {
        this.ownerRepository = ownerRepository;
        this.authenticationManager = authenticationManager;
        this.utils = utils;
        this.encoder = encoder;
    }

    @Override
    public TaskOwner register(SignupPayload signupPayload) {
        Optional<TaskOwner> existingUser = ownerRepository.findDistinctByEmail(signupPayload.getEmail());
        if(existingUser.isPresent()){
            log.error("Attempting to create duplicate user with email: {}", existingUser.get().getEmail());
            throw new UserWithProvidedEmailExistException("Provided email is already in use.Please provide a valid email");
        }
        TaskOwner toBeRegistered = null;
        try {
            toBeRegistered = TaskOwner.builder()
                    .id(UUID.randomUUID())
                    .firstName(signupPayload.getFirstName())
                    .lastName(signupPayload.getLastName())
                    .username(signupPayload.getUsername())
                    .email(signupPayload.getEmail())
                    .password(encoder.encode(signupPayload.getPassword()))
                    .isVerified(true)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ownerRepository.save(toBeRegistered);
    }

    @Override
    public JwtAccessTokenResponsePayload ownerLogin(LoginPayload loginPayload) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginPayload.getUsername(), loginPayload.getPassword())
        );
        ApplicationSecurityUser user = (ApplicationSecurityUser) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = utils.generateJwtToken(authentication);
        return JwtAccessTokenResponsePayload.builder()
                .status(HttpStatus.OK.toString())
                .message("Successfully Logged in.Welcome to your account")
                .authenticationToken(jwt)
                .username(user.getUsername())
                .userId(user.getId())
                .email(user.getEmail())
                .authorities(user.getAuthorities()).build();
    }
}
