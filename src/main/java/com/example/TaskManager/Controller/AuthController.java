package com.example.TaskManager.Controller;

import com.example.TaskManager.Model.DTO.AuthResponseDTO;
import com.example.TaskManager.Model.DTO.UserDTO;
import com.example.TaskManager.Model.Entities.Role;
import com.example.TaskManager.Model.Entities.UserEntity;
import com.example.TaskManager.Security.JwtGenerator;
import com.example.TaskManager.Service.RoleService;
import com.example.TaskManager.Service.UserService;
import com.example.TaskManager.mappers.implementation.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Optional;

/**
 * Implements user authorization through the use of login and registration endpoints with
 * JWT tokens
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    private JwtGenerator tokenGenerator;

    /**
     *
     * @param authenticationManager
     * @param userService
     * @param roleService
     * @param passwordEncoder
     * @param userMapper
     * @param tokenGenerator
     *
     * informs spring that these dependencies need to be injected
     * (constructor injection)
     */
    public AuthController(
       AuthenticationManager authenticationManager,
       UserService userService,
       RoleService roleService,
       PasswordEncoder passwordEncoder,
       UserMapper userMapper,
       JwtGenerator tokenGenerator
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.tokenGenerator = tokenGenerator;
    }

    /**
     * takes in a UserDTO containing credentials to be used for registration
     * password credential is encoded, role is set, username is verified for uniqueness,
     * and then user is registered
     * @param userDTO
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        if(userService.existsByUsername(userDTO.getUsername())){
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Optional<Role> wrappedRoles = roleService.findByName("USER");
        Role roles;
        if(wrappedRoles.isPresent()){
            roles = wrappedRoles.get();
        }
        else{
            return new ResponseEntity<>("Role not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        userEntity.setRoles(Collections.singletonList(roles));
        userService.save(userEntity);

        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }

    /**
     * Verifies user credentials, authenticates user, and generates token
     * @param userDTO takes in user credentials in form of userDTO
     * @return a response body containing token and HttpStatus.ok
     */
    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
}
