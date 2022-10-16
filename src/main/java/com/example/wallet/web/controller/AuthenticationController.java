package com.example.wallet.web.controller;

import com.example.wallet.domain.user.AppUserDetail;
import com.example.wallet.domain.user.UserService;
import com.example.wallet.domain.user.dto.CreateUserDto;
import com.example.wallet.domain.user.dto.LoginDto;
import com.example.wallet.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController
{
    @Autowired UserService userService;
    @Autowired JwtUtil jwtUtil;
    @Autowired AuthenticationManager authenticationManager;

    @PostMapping("signup")
    public void signUp(@RequestBody CreateUserDto request)
    {
        userService.signUp(request);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto login)
    {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.getUsername(),
                            login.getPassword()));
            var userDetail = (AppUserDetail) userService.loadUserByUsername(login.getUsername());
            var token = jwtUtil.generateToken(userDetail);
            return ResponseEntity.status(200).body(token);
        }
        catch ( Exception e ) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
