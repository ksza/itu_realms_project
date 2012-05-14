package dk.itu.realms.web.feedback;

import dk.itu.realms.model.entity.Mark;
import dk.itu.realms.model.entity.Option;

public class UserDataAdapter {

	private final String userId;
	private final String data;

	private final Mark mark;
	private final Option option;

	public UserDataAdapter(final String userId, final String data, final Mark mark, final Option option) {
		this.userId = userId;
		this.data = data;
		this.mark = mark;
		this.option = option;
	}

	public String getUserId() {
		return userId;
	}

	public String getData() {
		return data;
	}

	public String getMarkSummary() {
		String summary = "";

		if(mark != null) {
			if(mark.getType().equals("QUESTION")) {
				summary += "Question:<br/>";
				summary += mark.getTextBlob() + "<br/><br/>";
				summary += "Answers:<br/>";

				for(Option o: mark.getOptions()) {
					if(o.isCorrectAnswer()) {
						summary += "[x] " + o.getOptionName();
					} else {
						summary += "[ ] " + o.getOptionName();
					}
				}
			} else {
				summary += "Information:<br/>";
				summary += mark.getTextBlob() + "<br/><br/>";
			}
		}

		return summary;
	}

	public String getOptionSummary() {
		String summary = "";

		if(option != null) {
			if(option.isCorrectAnswer()) {
				summary += "Answer:<br/><b>" + "<font color=green>" + option.getOptionName() + " ( Correct )" + "</font></b>";
			} else {
				summary += "Answer:<br/><b>" + "<font color=red>" + option.getOptionName() + " ( Wrong )" + "</font></b>";
			}
		} else {
			summary += "Rating:<br/><b>" + data + "</b>";
		}

		return summary;
	}

}
