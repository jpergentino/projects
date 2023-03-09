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

import base.data.UserRepository;
import base.data.entity.User;

@RestController // The methods are implicitly annotated using @ResponseBody 
@RequestMapping("/rest/users")
public class UserController {
	
	@Autowired
	private UserRepository repo;
	
	@GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return repo.findById(id).get();
    }
	
	@GetMapping("")
	public List<User> getAllUsers() {
		return repo.findAll();
	}
	
	@PostMapping("")
	public User addUser(@RequestBody User User) {
		return repo.save(User);
	}
	
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User newUser, @PathVariable Long id) {

		return repo.findById(id)
			.map(user -> {
				user.setName(newUser.getName());
				user.setEmail(newUser.getEmail());
				return repo.save(user);
			}).orElseGet(() -> {
				newUser.setId(id);
				return repo.save(newUser);
			});
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		repo.deleteById(id);
	}

}