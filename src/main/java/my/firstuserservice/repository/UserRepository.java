package my.firstuserservice.repository;

import my.firstuserservice.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    boolean existsByLogin(String login);

    User findByLogin(String login);
}
