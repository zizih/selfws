package models;

import java.io.File;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.ivy.core.publish.PublishEngine;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity(name = "user")
public class User extends Model {

	@Required(message = "名字不能为空")
	public String name;

	@Required
	public String passwd;

	public Integer gender;

	public String email;

	public String phone;

	public String description;

	@Column(name = "userimg")
	public String userImgPath;

}
