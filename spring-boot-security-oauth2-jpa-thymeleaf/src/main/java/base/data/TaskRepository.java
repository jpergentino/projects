package base.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import base.data.entity.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
	
	public List<Task> findAll();

}