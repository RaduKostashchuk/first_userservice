package my.firstuserservice.service;

import my.firstuserservice.model.Role;
import my.firstuserservice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository mockedRoleRepository;

    @Test
    public void whenSaveExistingThenThrowException() {
        Role role = Role.of("ROLE_ANY");
        RoleService roleService = new RoleService(mockedRoleRepository);
        when(mockedRoleRepository.existsByName(role.getName())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> roleService.save(role));
    }

    @Test
    public void whenSaveNewThenRunWithoutException() {
        Role role = Role.of("ROLE_ANY");
        RoleService roleService = new RoleService(mockedRoleRepository);
        when(mockedRoleRepository.existsByName(role.getName())).thenReturn(false);
        roleService.save(role);
    }

    @Test
    public void whenDeleteNotExistingThenThrowException() {
        int id = 1;
        RoleService roleService = new RoleService(mockedRoleRepository);
        when(mockedRoleRepository.existsById(id)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> roleService.delete(id));
    }

    @Test
    public void whenFindNotExistingThenThrowException() {
        int id = 1;
        RoleService roleService = new RoleService(mockedRoleRepository);
        when(mockedRoleRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> roleService.findById(id));
    }

    @Test
    public void whenUpdateNotExistingThenThrowException() {
        Role role = Role.of("ROLE_ANY");
        int id = 1;
        role.setId(id);
        RoleService roleService = new RoleService(mockedRoleRepository);
        when(mockedRoleRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> roleService.update(role));
    }

    @Test
    public void whenUpdateExistingThenSuccess() {
        Role role = Role.of("ROLE_ANY");
        int id = 1;
        role.setId(id);
        RoleService roleService = new RoleService(mockedRoleRepository);
        when(mockedRoleRepository.findById(id)).thenReturn(Optional.of(role));
        roleService.update(role);
    }
}