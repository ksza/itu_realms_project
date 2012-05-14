package dk.itu.realms.model.entity.reduced;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import dk.itu.realms.model.entity.Mark;
import dk.itu.realms.model.entity.Option;

public class MarkReduced {
	
	private static final String[] TYPES = new String[] { "INFORMATION", "QUESTION" };
	
	private Long id;
	
	private String markTitle;
	private String markDescription;
	
	/* possible types: INFORMATION, QUESTION */
	private String type;

	/* can represent a Question or INFORMATION -> the data stored here will be formatted! */
	private String textBlob;
	
	private List<Option> options;

	public static String[] getSupportedTypes() {
		return TYPES;
	}
	
	public MarkReduced() { }
	
	public MarkReduced(Mark mark) {
		this.id = mark.getId();
		this.markTitle = mark.getMarkTitle();
		this.markDescription = mark.getMarkDescription();
		this.textBlob = mark.getTextBlob();
		this.type = mark.getType();
		options = mark.getOptions();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTextBlob() {
		return textBlob;
	}

	public void setTextBlob(String textBlob) {
		this.textBlob = textBlob;
	}

	@XmlElement(name="option")
	public List<Option> getOptions() {
		return options;
	}

	
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	
	
}
