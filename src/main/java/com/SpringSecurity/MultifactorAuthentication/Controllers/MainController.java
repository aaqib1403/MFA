package com.SpringSecurity.MultifactorAuthentication.Controllers;

import com.SpringSecurity.MultifactorAuthentication.Dto.LoginDto;
import com.SpringSecurity.MultifactorAuthentication.Entities.UserEntity;
import com.SpringSecurity.MultifactorAuthentication.Repositories.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/MFA")
public class MainController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @ApiOperation(value = "Hello Descrip")
    @GetMapping("/hello")
    public String getHello() {
        return "Hello!!!";
    }

    @ApiOperation(value = "To login to use the api")
    @PostMapping("/login")
    private String login(LoginDto loginDto) {
        return "success";
    }


    @ApiOperation(value = "To Register the User")
    @PostMapping("/register")
    private String register(@RequestBody LoginDto loginDto) {
        UserEntity userEntity = new UserEntity();
        String encodedPassword = bCryptPasswordEncoder.encode(loginDto.getPassword());
        loginDto.setPassword(encodedPassword);
        BeanUtils.copyProperties(loginDto, userEntity);
        userRepository.save(userEntity);
        return "Register Success";
    }

    @ApiOperation(value = "")
    @GetMapping("/authenticate")
    private String register() {

        return "Authenticate Success";
    }
}
