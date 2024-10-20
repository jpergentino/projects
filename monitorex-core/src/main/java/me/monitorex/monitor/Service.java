package me.monitorex.monitor;

public class Service {


	private String name;
	private String url;
	private long port;
	private long interval;
	
	public Service() {
	}
	
	public Service(String name, String url, long port, long interval) {
		super();
		this.name = name;
		this.url = url;
		this.port = port;
		this.interval = interval;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getPort() {
		return port;
	}

	public void setPort(long port) {
		this.port = port;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}
}