package my.firstuserservice.service;

import my.firstuserservice.model.Role;
import my.firstuserservice.model.User;
import my.firstuserservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository mockedUserRepository;

    @Mock
    private RoleService mockedRoleService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    public void whenSaveExistingUserThenThrowException() {
        User user = User.of("user", "password", Role.of("ROLE_ANY"));
        UserService userService = new UserService(mockedUserRepository, encoder, mockedRoleService);
        when(mockedUserRepository.existsByLogin(user.getLogin())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void whenSaveUserWIthNotExistingRoleThenThrowException() {
        User user = User.of("user", "password", Role.of("ROLE_ANY"));
        UserService userService = new UserService(mockedUserRepository, encoder, mockedRoleService);
        when(mockedUserRepository.existsByLogin(user.getLogin())).thenReturn(false);
        when(mockedRoleService.existsByName(user.getRole().getName())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> userService.save(user));
    }

    @Test
    public void whenDeleteNotExistingThenThrowException() {
        int id = 1;
        UserService userService = new UserService(mockedUserRepository, encoder, mockedRoleService);
        when(mockedUserRepository.existsById(id)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> userService.delete(id));
    }

    @Test
    public void whenFindNotExistingThenThrowException() {
        int id = 1;
        UserService userService = new UserService(mockedUserRepository, encoder, mockedRoleService);
        when(mockedUserRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.findById(id));
    }

    @Test
    public void whenUpdateNotExistingThenThrowException() {
        User user = User.of("user", "password", Role.of("ROLE_ANY"));
        int id = 1;
        user.setId(id);
        UserService userService = new UserService(mockedUserRepository, encoder, mockedRoleService);
        when(mockedUserRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.update(user));
    }

    @Test
    public void whenUpdateExistingThenSuccess() {
        User user = User.of("user", "password", Role.of("ROLE_ANY"));
        int id = 1;
        user.setId(id);
        UserService userService = new UserService(mockedUserRepository, encoder, mockedRoleService);
        when(mockedUserRepository.findById(id)).thenReturn(Optional.of(user));
        userService.update(user);
    }
}