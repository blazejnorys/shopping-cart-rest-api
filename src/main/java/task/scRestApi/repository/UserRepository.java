package task.scRestApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.scRestApi.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


}
