package base.data.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users") // Optional. The default name is the class name lower case
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gen_user_id")
    private Long id;
	
	@Column(length = 128, unique = true)
	private String email;
	
	@Column(length = 128, nullable = false)
	private String name;
	
	@OneToMany(mappedBy="user")
	//@JsonManagedReference // Approach to avoid JSON Infinite recursion
	@JsonIgnore
    private Set<Task> tasks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

}