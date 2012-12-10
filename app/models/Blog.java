package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity(name="blog")
public class Blog extends Model{

	public long userid;
	
	public long posttime;
	
	public  long lastmodifytime;
	
	public String title;
	
	public String content;
	
	public String comment;
}
