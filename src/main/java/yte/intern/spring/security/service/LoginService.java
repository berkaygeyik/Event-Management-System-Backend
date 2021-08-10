package yte.intern.spring.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import yte.intern.spring.security.dto.LoginRequest;
import yte.intern.spring.security.dto.LoginResponse;
import yte.intern.spring.security.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Value("${security.jwt.secretKey}")
    private String secretKey;

    private final DaoAuthenticationProvider authenticationProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword());
        try {
            Authentication user = authenticationProvider.authenticate(authentication);
            String token = JwtUtil.generateToken(user, secretKey, 7);
            return new LoginResponse(token);


        } catch (AuthenticationException ex) {
            System.out.println("There is a problem in Login Service!");
        }
        return null;
    }
}
