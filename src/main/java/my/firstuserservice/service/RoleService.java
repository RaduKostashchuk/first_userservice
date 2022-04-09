package my.firstuserservice.service;

import my.firstuserservice.model.Role;
import my.firstuserservice.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role) {
        if (roleRepository.existsByName(role.getName())) {
            throw new IllegalArgumentException("Такая роль уже существует");
        }
        return roleRepository.save(role);
    }

    public void delete(int id) {
        if (!roleRepository.existsById(id)) {
            throw new NoSuchElementException("Такой роли не существует");
        }
        roleRepository.deleteById(id);
    }

    public Role findById(int id) {
        Role result = roleRepository.findById(id).orElse(null);
        if (result == null) {
            throw new NoSuchElementException("Такой роли не существует");
        }
        return result;
    }

    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    public void update(Role role) {
        Role persisted = roleRepository.findById(role.getId()).orElse(null);
        String name = role.getName();
        if (persisted == null) {
            throw new NoSuchElementException("Такой роли не существует");
        }
        if (!name.isEmpty()) {
            persisted.setName(name);
            roleRepository.save(persisted);
        }
    }
}
