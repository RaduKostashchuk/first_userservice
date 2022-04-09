package my.firstuserservice.service;

import my.firstuserservice.model.Role;
import my.firstuserservice.model.User;
import my.firstuserservice.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleService = roleService;
    }

    public User save(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new IllegalArgumentException("Такой пользователь уже существует");
        }
        if (!roleService.existsByName(user.getRole().getName())) {
            throw new NoSuchElementException("Такой роли не существует");
        }
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
        return userRepository.save(user);
    }

    public void delete(int id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("Такого пользователя не существует");
        }
        userRepository.deleteById(id);
    }

    public User findById(int id) {
        User result = userRepository.findById(id).orElse(null);
        if (result == null) {
            throw new NoSuchElementException("Такого пользователя не существует");
        }
        return result;
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public void update(User user) {
        User persisted = userRepository.findById(user.getId()).orElse(null);
        String password = user.getPassword();
        String name = user.getName();
        String surname = user.getSurname();
        Role role = user.getRole();
        if (persisted == null) {
            throw new NoSuchElementException("Такого пользователя не существует");
        }
        if (password != null) {
            persisted.setPassword(password);
        }
        if (name != null) {
            persisted.setName(name);
        }
        if (surname != null) {
            persisted.setSurname(surname);
        }
        if (role != null) {
            persisted.setRole(role);
        }
        userRepository.save(persisted);
    }
}
