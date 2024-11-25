package ua.dragunovskiy.web_security_2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dragunovskiy.web_security_2.dto.JwtRequestDto;
import ua.dragunovskiy.web_security_2.dto.JwtResponseDto;
import ua.dragunovskiy.web_security_2.dto.RegistrationUserDto;
import ua.dragunovskiy.web_security_2.dto.UserDto;
import ua.dragunovskiy.web_security_2.entity.User;
import ua.dragunovskiy.web_security_2.exception.AppError;
import ua.dragunovskiy.web_security_2.service.UserService;
import ua.dragunovskiy.web_security_2.util.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequest) {
        // TODO: hide code in service
        //  and to set exception handling with global exceptions? (to think)
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            System.out.println("ok");
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Wrong login or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDto(token));
    }

//    @PostMapping("/registration")
//    public ResponseEntity<?> createNewUser (@RequestBody RegistrationUserDto registrationUserDto) {
//        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
//            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Passwords don't match"), HttpStatus.BAD_REQUEST);
//        }
//        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
//            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "User with notice username is already exist"), HttpStatus.UNAUTHORIZED);
//        }
//        User user = new User();
//        user.setUsername(registrationUserDto.getUsername());
//        user.setEmail(registrationUserDto.getEmail());
//        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
//        userService.createNewUser(user);
//        return null;
//    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser (@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Passwords don't match"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "User with notice username is already exist"), HttpStatus.UNAUTHORIZED);
        }
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }
}
