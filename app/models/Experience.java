package models;

import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.eclipse.jdt.core.dom.ThisExpression;

import play.db.jpa.Model;

@Entity(name = "experience")
public class Experience extends Model {

	public long userid;

	public long starttime;

	public long endtime;

	public String summary;

	public String content;

	public String getStartTime() {
		return getFormatTime(starttime);
	}

	public String getEndTime() {
		return getFormatTime(endtime);
	}

	public String getFormatTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		return sdf.format(time);
	}

}
