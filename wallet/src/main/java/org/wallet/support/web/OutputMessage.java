package org.wallet.support.web;

public class OutputMessage {

	private String from;
    private String text;
    private String time;
    
	public OutputMessage(String from2, String text2, String time) {
		// TODO Auto-generated constructor stub
		this.from = from2;
		this.text = text2;
		this.time = time;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
