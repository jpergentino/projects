package me.monitorex.monitor;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ServicesConfiguration {

	private String name;
	private List<Service> services;

	public ServicesConfiguration(String name, List<Service> services) {
		super();
		this.name = name;
		this.services = services;
	}
	
	public ServicesConfiguration() {
	}
	
	public void addService(Service service) {
		if (Objects.isNull(services)) {
			services = new LinkedList<Service>();
		}
		services.add(service);
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}