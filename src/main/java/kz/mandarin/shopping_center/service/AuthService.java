package kz.mandarin.shopping_center.service;

import kz.mandarin.shopping_center.dto.JwtAuthenticationResponse;
import kz.mandarin.shopping_center.dto.SignInRequest;
import kz.mandarin.shopping_center.dto.SignUpRequest;
import kz.mandarin.shopping_center.entity.User;
import kz.mandarin.shopping_center.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest signUpRequest) {
        var userCreated = User.builder().username(
                        signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .role(Role.ROLE_USER)
                .build();
        userService.createUser(userCreated);
        String token = jwtService.generateToken(userCreated);
        return new JwtAuthenticationResponse(token);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
