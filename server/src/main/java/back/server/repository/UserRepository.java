package back.server.repository;

import back.server.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    public User findByLogin(String login);
    public List<User> findAllByLogin(String login);
    public User findByNickname(String nickname);
}
