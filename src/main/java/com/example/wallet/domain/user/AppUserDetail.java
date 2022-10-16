package com.example.wallet.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AppUserDetail implements UserDetails
{
    UserEntity user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return user.userRoles()
                   .stream()
                   .map(r -> new SimpleGrantedAuthority(r.roleName()))
                   .collect(Collectors.toSet());
    }

    @Override
    public String getPassword()
    {
        return user.password();
    }

    @Override
    public String getUsername()
    {
        return user.userName();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return user.isActivated();
    }
}
