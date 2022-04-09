package my.firstuserservice.repository;

import my.firstuserservice.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    boolean existsByName(String name);
}
