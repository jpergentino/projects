package base.web.dto;

public class RequestDTO {

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "RequestDTO [text=" + text + "]";
	}

}
