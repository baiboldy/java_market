package kz.mandarin.shopping_center.controller;

import jakarta.validation.Valid;
import kz.mandarin.shopping_center.dto.JwtAuthenticationResponse;
import kz.mandarin.shopping_center.dto.SignInRequest;
import kz.mandarin.shopping_center.dto.SignUpRequest;
import kz.mandarin.shopping_center.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authService.signIn(request);
    }
}
