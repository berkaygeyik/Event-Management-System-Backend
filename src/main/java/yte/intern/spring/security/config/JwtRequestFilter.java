package yte.intern.spring.security.config;


import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import yte.intern.spring.security.entity.Users;
import yte.intern.spring.security.service.CustomUserDetailsManager;
import yte.intern.spring.security.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter
{

    @Value("${security.jwt.secretKey}")
    private String secretKey;

    private final CustomUserDetailsManager customUserDetailsManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Bearer")){
            String jwtToken = authorization.substring(7);
            try {
                String username = JwtUtil.extractUsername(jwtToken, secretKey);
                Users userDetails = (Users) customUserDetailsManager.loadUserByUsername(username);
                var token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(token);

            } catch (ExpiredJwtException e) {
                System.out.println("Oturum zaman aşımına uğradı..");
            }



        }
        filterChain.doFilter(request,response);
    }

}
