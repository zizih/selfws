package models;

import javax.persistence.Entity;
import play.db.jpa.Model;

@Entity(name = "friends")
public class Friends extends Model {

	public long userid;

	public long friendid;

	public Friends(long userid, long friendid) {
		this.userid = userid;
		this.friendid = friendid;
	}
}
