package base.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import base.data.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	public List<User> findAll();

}