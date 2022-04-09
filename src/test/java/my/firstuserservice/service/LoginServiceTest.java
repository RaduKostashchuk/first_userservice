package my.firstuserservice.service;

import my.firstuserservice.dto.LoginDto;
import my.firstuserservice.model.Role;
import my.firstuserservice.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    private UserService mockedUserService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private JwtEncoder jwtEncoder;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    public void whenUserNotFoundThenThrowException() {
        LoginDto loginDto = LoginDto.of("user", "123");
        when(mockedUserService.findByLogin(loginDto.getLogin())).thenReturn(null);
        LoginService loginService = new LoginService(mockedUserService, encoder, jwtEncoder);
        assertThrows(BadCredentialsException.class, () -> loginService.login(loginDto));
    }

    @Test
    public void whenWrongCredentialsThenThrowException() {
        String login = "user";
        String password = "password";
        Role role = Role.of("ROLE_ADMIN");
        LoginDto loginDto = LoginDto.of(login, "wrong_password");
        User user = User.of(login, encoder.encode(password), role);
        when(mockedUserService.findByLogin(loginDto.getLogin())).thenReturn(user);
        LoginService loginService = new LoginService(mockedUserService, encoder, jwtEncoder);
        assertThrows(BadCredentialsException.class, () -> loginService.login(loginDto));
    }

    @Test
    void whenValidCredentialsThenGenerateToken() {
        String login = "user";
        String password = "12345";
        Role role = Role.of("ROLE_ANY");
        LoginDto loginDto = LoginDto.of(login, password);
        User user = User.of(login, encoder.encode(password), role);
        when(mockedUserService.findByLogin(login)).thenReturn(user);
        when(jwtEncoder.encode(any(JwtEncoderParameters.class)).getTokenValue()).thenReturn("mocked token");
        LoginService loginService = new LoginService(mockedUserService, encoder, jwtEncoder);
        assertThat("mocked token", equalTo(loginService.login(loginDto)));
    }
}