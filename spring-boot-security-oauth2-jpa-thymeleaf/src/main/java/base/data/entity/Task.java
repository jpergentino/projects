package base.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tasks") // Optional. The default name is the class name lower case
public class Task {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gen_task_id")
    private Long id;
	
	@Column(length = 128, nullable = false)
	private String name;
	
	@ManyToOne
    @JoinColumn(name="user_id_fk", nullable=false)
	//@JsonBackReference // Approach to avoid JSON Infinite recursion
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}