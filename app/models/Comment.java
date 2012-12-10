package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity(name="comment")
public class Comment extends Model {

	public long userid;

	public long blogid;

	public String comment;
}
