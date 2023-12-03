package com.projekt.forum.services;

import com.projekt.forum.dataTypes.forms.RegisterForm;
import com.projekt.forum.entity.GrantedAuthorityEntity;
import com.projekt.forum.entity.UserEntity;
import com.projekt.forum.repositories.AuthorityRepository;
import com.projekt.forum.repositories.UserRepository;
import com.projekt.forum.utility.DateUtility;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final GrantedAuthorityEntity grantedAuthUser;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest httpServletRequest;
    private final JWTService jwtService;

    public RegisterService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, HttpServletRequest httpServletRequest, JWTService jwtService) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        //TODO może customowy wyjątek ?
        this.grantedAuthUser = authorityRepository.findById(2).orElseThrow(()->new RuntimeException("Fatal Error user auth not found"));


        this.passwordEncoder = passwordEncoder;
        this.httpServletRequest = httpServletRequest;
        this.jwtService = jwtService;
    }

    @Transactional
    public boolean registerNewUser(RegisterForm registerForm, HttpServletResponse httpServletResponse) {
        UserEntity newUser= new UserEntity(grantedAuthUser,passwordEncoder.encode(registerForm.getPassword()),registerForm.getLogin(), registerForm.getEmail(),DateUtility.getCurrentDate());
        userRepository.save(newUser);
        String jwtToken = jwtService.generateJWT(newUser);
        jwtService.addTokenToResponse(httpServletResponse,jwtToken);

        try{
            httpServletRequest.login(registerForm.getLogin(),registerForm.getPassword());
        }
        catch ( ServletException e){
            return false;
        }

        return true;
    }

}
