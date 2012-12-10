package models;

import javax.persistence.Entity;

import org.apache.commons.collections.map.StaticBucketMap;

import play.db.jpa.Model;

@Entity(name = "chat")
public class Chat extends Model {

	public long fromuserid;

	public long touserid;

	public String message;

	public long sendtime;
	
	public String getFromUserName(){
		User user= User.findById(fromuserid);
		return user.name;
	}
	
	public String getToUserName(){
		System.out.println("to user name="+touserid);
		User user= User.findById(touserid);
		return user.name;
	}
}
