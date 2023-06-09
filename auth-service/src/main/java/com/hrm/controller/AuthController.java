package com.hrm.controller;


import com.hrm.dto.request.NewRegisterRequestDto;
import com.hrm.dto.request.UserLoginDto;
import com.hrm.dto.response.RegisterResponseDto;
import com.hrm.exception.AuthServiceException;
import com.hrm.exception.ErrorType;
import com.hrm.repository.entity.Auth;
import com.hrm.service.AuthService;
import com.hrm.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.hrm.constants.ApiUrls.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenManager jwtTokenManager;
    private final AuthService authService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid NewRegisterRequestDto dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @GetMapping(LOGIN)
    public ResponseEntity<String> login(UserLoginDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PutMapping(FORGOTPASSWORD)
    public ResponseEntity<Boolean> forgotPassword(@RequestParam String email) {
        return ResponseEntity.ok(authService.forgotPassword(email));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(GETROLEFROMTOKEN)
    public ResponseEntity<String> getRoleFromToken(@RequestParam String token) {
        return ResponseEntity.ok(jwtTokenManager.getRoleFromToken(token).get());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(GETIDFROMTOKEN)
    public ResponseEntity<Long> getIdFromToken(@RequestParam String token) {
        return ResponseEntity.ok(jwtTokenManager.getIdFromToken(token).get());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(FINDALL)
    public ResponseEntity<List<Auth>> findAll() {
        return ResponseEntity.ok(authService.findAll());
    }

}
