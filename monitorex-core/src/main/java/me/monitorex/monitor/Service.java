package me.monitorex.monitor;

import java.util.Objects;

public class Service {

	private String name;
	private String url;
	private String method;
	private String requestBody;
	
	private Integer expectedResultCode;
	private Integer timeout;
	
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

	@Override
	public int hashCode() {
		return Objects.hash(interval, name, port, url);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Service other = (Service) obj;
		return interval == other.interval && Objects.equals(name, other.name) && port == other.port
				&& Objects.equals(url, other.url);
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public Integer getExpectedResultCode() {
		return expectedResultCode;
	}

	public void setExpectedResultCode(Integer expectedResultCode) {
		this.expectedResultCode = expectedResultCode;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
}