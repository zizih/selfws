package controllers;

import play.*;
import play.data.validation.Valid;
import play.mvc.*;
import play.mvc.Scope.RenderArgs;
import sun.rmi.log.LogOutputStream;
import sun.security.jgss.LoginConfigImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import org.bouncycastle.jce.provider.JDKGOST3410Signer.gost3410;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.h2.constant.SysProperties;

import com.mysql.jdbc.log.Log;
import com.sun.org.apache.bcel.internal.generic.Select;

import living.hz.util.FileUtil;
import living.hz.util.TimeUtil;
import models.*;

public class Application extends Controller {

	static boolean iflogin = false;
	static String loginstatu = null;
	static String statuString = null;
	static User currentUser;
	static int i = 0;
	static String USERIMG = "UserImg";
	static String ALBUMIMG = "Album";

	@Before()
	public static void init() {
		statuString = "true";
		renderArgs.put("iflogin", iflogin);
	}

	@Before()
	public static void after() {
		renderArgs.put("user", currentUser);
		renderArgs.put("currentUser", currentUser);
	}

	public static void index() {
		List userList = User.findAll();
		render(userList);
	}

	public static void goLogin() {
		render("@login"); // loginstatu 不能传递给标签页面
	}

	public static void login(String name, String passwd) {
		User user = User.find("byName", name).first();
		if (user == null) {
			System.out.println("namefalse");
			loginstatu = "namefalse";
			flash.error(loginstatu);
			goLogin();
		} else if (!user.passwd.equals(passwd)) {
			System.out.println("passwdfalse");
			loginstatu = "passwdfalse";
			flash.error(loginstatu);
			goLogin();
		} else {
			System.out.println("you have login...");
			currentUser = user;
			iflogin = true;
			index();
		}
	}

	public static void goRegister() {
		render("@register");
	}

	public static void register(@Valid User user, String passwd,
			String newpasswd, File avatar) {
		if (validation.hasErrors()) {
			validation.keep();
			goLogin();
		}
		if (user.id != null) {
			System.out.println("!null");
			User oldUser = User.findById(user.id);
			if (user.name != oldUser.name) {
				user.userImgPath = FileUtil.renameAvatarPath(oldUser.name,
						user.name, oldUser.userImgPath);
			}
			if (newpasswd != null && !newpasswd.equals("")) {
				System.out.println("new passwd != null");
				if (passwd.equals(user.passwd)) {
					System.out.println("right passwd");
					user.passwd = newpasswd;
				} else {
					System.out.println("error...");
					statuString = "passwdfalse";
					flash.error(statuString);
					showUserInfo();
				}
			}
			if (avatar != null) {
				new File(oldUser.userImgPath).delete();
				user.userImgPath = FileUtil.saveAvatar(avatar, user.name);
			}
			user.userImgPath = oldUser.userImgPath;
		} else {
			user.userImgPath = FileUtil.saveAvatar(avatar, user.name);
		}
		currentUser = user;
		user.save();
		showUserInfo();
	}

	public static void changeAvatar(File avatar) {
		User user = User.findById(currentUser.id);
		user.userImgPath = FileUtil.saveAvatar(avatar, user.name);
		user.save();
		showUserInfo();
	}

	public static void saveAvatar(File img) {
	}

	public static void download() {
		String fileName = "yourFileName";
		File ff = new File("/tmp/selfws/UserImg/aa.jpg");
		FileInputStream input;
		try {
			input = new FileInputStream(ff);
			renderBinary(input, fileName, "application/download", true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void logout() {
		iflogin = false;
		currentUser = null;
		index();
	}

	public static void showUserInfo() {
		User user = User.findById(currentUser.id);
		List firendsList = Friends.find("byUserid", currentUser.id).fetch();
		renderArgs.put("statuString", statuString);
		render(user, firendsList);
	}

	public static void deleteExperience(long experienceid) {
		Experience experience = Experience.findById(experienceid);
		experience.delete();
		showExperience();
	}

	public static void goAddExperience(long id) {
		System.out.println("id====>" + id);
		Experience experience = Experience.findById(id);
		render("@addExperience", experience);
	}

	public static void addExperience(@Valid Experience experience,
			int startyear, int startmonth, int startday, int endyear,
			int endmonth, int endday) {
		System.out.println("yyyy-mm-dd========>" + startyear + startmonth
				+ startday + "      " + endyear + endmonth + endday);
		if (validation.hasErrors()) {
			validation.keep();
			System.out.println("day error..");
		}
		Date startDate = new Date(startyear, startmonth, startday);
		Date endDate = new Date(endyear, endmonth, endday);
		System.out.println("date start==>" + startDate);
		System.out.println("date end==>" + endDate);
		experience.starttime = startDate.getTime() - TimeUtil.getPointTime();
		experience.endtime = endDate.getTime() - TimeUtil.getPointTime();
		experience.userid = currentUser.id;
		experience.save();
		showExperience();
	}

	public static void showExperience() {
		List experienceList = Experience.find("byUserid", currentUser.id)
				.fetch();
		render(experienceList);
	}

	public static void addFriend(long friendid) {
		Friends friend = new Friends(currentUser.id, friendid);
		friend.save();
		render("@index");
	}

	public static void showAlbum() {
		List albumList = Album.find("byUserid", currentUser.id).fetch();
		render(albumList);
	}

	public static void addAlbum(File img) {
		Album album = new Album();
		album.imgpath = FileUtil.saveAlbum(img);
		album.userid = currentUser.id;
		album.save();
		showAlbum();
	}

	public static void deleteAlbum(long albumid) {
		Album album = Album.findById(albumid);
		album.delete();
		showAlbum();
	}

	public static void goChat(long touserid, String tousername) {
		System.out.println("go chat====>" + touserid + tousername);
		render("@addChat", touserid, tousername);
	}

	public static void addchat(@Valid Chat chat) {
		if (validation.hasErrors()) {
			System.out.println("add chat error...");
			validation.keep();
		}
		chat.sendtime = new Date().getTime();
		chat.fromuserid = currentUser.id;
		System.out.println("chat====>>" + chat.touserid + chat.message
				+ chat.sendtime + chat.fromuserid);
		chat.save();
		showChat();
	}

	public static void showChat() {
		List chatSendList = Chat.find("byTouserid", currentUser.id).fetch();
		List chatRecieveList = Chat.find("byFromuserid", currentUser.id)
				.fetch();
		render(chatSendList, chatRecieveList);
	}

	public static void deleteChat(long chatid) {
		Chat chat = Chat.findById(chatid);
		chat.delete();
		showChat();
	}
}
