package base.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import base.data.TaskRepository;
import base.data.entity.Task;

@RestController // The methods are implicitly annotated using @ResponseBody 
@RequestMapping("/rest/tasks")
public class TaskController {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskRepository.findById(id).get();
    }
	
	@GetMapping("")
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}
	
	@PostMapping("")
	public Task addTask(@RequestBody Task task) {
		return taskRepository.save(task);
	}
	
	@PutMapping("/{id}")
	public Task updateTask(@RequestBody Task newTask, @PathVariable Long id) {

		return taskRepository.findById(id)
			.map(task -> {
				task.setName(newTask.getName());
				task.setUser(newTask.getUser());
				return taskRepository.save(task);
			}).orElseGet(() -> {
				newTask.setId(id);
				return taskRepository.save(newTask);
			});
	}
	
	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable Long id) {
		taskRepository.deleteById(id);
	}

}