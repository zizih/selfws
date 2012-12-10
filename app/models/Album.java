package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity(name="album")
public class Album extends Model {

	public long userid;

	public String imgpath;
	
	public Album(){
		
	}
}
