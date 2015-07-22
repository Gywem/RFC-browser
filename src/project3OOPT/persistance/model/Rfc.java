package project3OOPT.persistance.model;

/*
 * Rfc
 * Represents the Rfc entity
 * */
public class Rfc {
	private String bodyRfc;
	private int number;
	private String title;
	
	public Rfc(int number, String body){
		this.bodyRfc = body;
		this.number = number;
	}

	public String getBodyRfc() {
		return bodyRfc;
	}

	public void setBodyRfc(String bodyRfc) {
		this.bodyRfc = bodyRfc;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
