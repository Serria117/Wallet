package com.example.wallet.domain.user;

import com.example.wallet.domain.user.dto.CreateUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service @Slf4j
public class UserService implements UserDetailsService
{
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        var foundUser = userRepository.findByUserName(username);
        if ( foundUser == null ) throw new UsernameNotFoundException("Username does not exist");
        return new AppUserDetail(foundUser);
    }

    public void signUp(CreateUserDto newUser)
    {
        var userRoles = new HashSet<RoleEntity>();
        //default role will be 'ROLE_USER'
        userRoles.add(new RoleEntity().roleName("ROLE_USER"));
        var user = new UserEntity()
                           .userName(newUser.getUsername())
                           .password(passwordEncoder.encode(newUser.getPassword()))
                           .email(newUser.getEmail())
                           .createdTime(LocalDateTime.now())
                           .userRoles(userRoles);
        userRepository.save(user);
    }
}
