package com.example.wallet.configuration.security;

import com.example.wallet.domain.user.UserService;
import com.example.wallet.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    @Autowired JwtUtil jwtUtil;
    @Autowired UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        try {
            var token = getToken(request);
            if ( token != null && jwtUtil.validateToken(token) ) {
                var userName = jwtUtil.getUserNameFromToken(token);
                UserDetails loggedInUser = userService.loadUserByUsername(userName);
                if(loggedInUser != null) {
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            loggedInUser,
                            null,
                            loggedInUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        catch ( Exception e ) {
            throw new RuntimeException("Authentication failed");
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request)
    {
        var token = request.getHeader("Authorization");
        if ( StringUtils.hasText(token) && token.startsWith("Bearer ") ) {
            return token.substring(7);
        }
        return null;
    }
}
