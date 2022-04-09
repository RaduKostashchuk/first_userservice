package my.firstuserservice.dto;

import my.firstuserservice.model.Role;
import my.firstuserservice.model.User;

public class UserDto {
    private int id;
    private String login;
    private String name;
    private String surname;
    private Role role;

    public static UserDto of(User user) {
        UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.login = user.getLogin();
        dto.name = user.getName();
        dto.surname = user.getSurname();
        dto.role = user.getRole();
        return dto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
