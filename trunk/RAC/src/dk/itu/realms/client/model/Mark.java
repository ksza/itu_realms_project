package dk.itu.realms.client.model;

import java.util.ArrayList;
import java.util.List;

public class Mark {

	private Long id;
	
	private String markTitle;
	private String markDescription;
	
	private boolean isQuestion;
	private String textBlob;
	
	private List<Option> options;
	
	public Mark(Long id, String markTitle, String markDescription, boolean isQuestion, String textBlob, List<Option> options) {
		this.id = id;
		this.markTitle = markTitle;
		this.markDescription = markDescription;
		this.isQuestion = isQuestion;
		this.textBlob = textBlob;
		this.options = options;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMarkTitle() {
		return markTitle;
	}
	public void setMarkTitle(String markTitle) {
		this.markTitle = markTitle;
	}
	
	public String getMarkDescription() {
		return markDescription;
	}
	public void setMarkDescription(String markDescription) {
		this.markDescription = markDescription;
	}

	public boolean isQuestion() {
		return isQuestion;
	}
	public void setQuestion(boolean isQuestion) {
		this.isQuestion = isQuestion;
	}

	public String getTextBlob() {
		return textBlob;
	}
	public void setTextBlob(String textBlob) {
		this.textBlob = textBlob;
	}

	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	
	public String[] getOptionsNameArray() {
		final List<String> options = new ArrayList<String>();
		for(Option o: this.options) {
			options.add(o.getDescription());
		}
		
		return options.toArray(new String[options.size() - 1]);
	}
}
