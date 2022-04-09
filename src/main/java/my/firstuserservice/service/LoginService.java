package my.firstuserservice.service;

import my.firstuserservice.dto.LoginDto;
import my.firstuserservice.model.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LoginService {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private final JwtEncoder jwtEncoder;

    public LoginService(UserService userService, BCryptPasswordEncoder encoder, JwtEncoder jwtEncoder) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtEncoder = jwtEncoder;
    }

    public String login(LoginDto loginDto) {
        User user = userService.findByLogin(loginDto.getLogin());
        if (user == null
                || !encoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Ошибка аутентификации");
        }
        return generateToken(user);
    }

    private String generateToken(User user) {
        Instant now = Instant.now();
        long expiry = 3600;
        String scope = user.getRole().getName();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(user.getLogin())
                .claim("scope", scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
