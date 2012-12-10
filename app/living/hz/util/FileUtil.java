package living.hz.util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.internal.core.util.PublicScanner;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import play.Play;

public class FileUtil {

	public static String USERFILEPATH = "UserImg";
	public static String AVATARFILEPATH = "AvatarImg";
	public static String ALBUMFILEPATH = "AlbumImg";

	public static String saveAvatar(File avatar, String username) {
		if (avatar == null)
			return null;
		// path为相对路径
		File target = new File(getProjectPath()
				+ getAvatarRelativePath(username));
		if (!target.exists()) {
			target.mkdirs();
		}
		try {

			BufferedImage image = ImageIO.read(avatar);
			ImageIO.write(
					image,
					"JPEG",
					new File(target
							+ File.separator
							+ username
							+ avatar.getName().substring(
									avatar.getName().lastIndexOf("."))));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// copy(img, target,userName);
		return getAvatarRelativePath(username) + File.separator + username
				+ avatar.getName().substring(avatar.getName().lastIndexOf("."));// 路径问题
	}

	public static String saveAlbum(File album) {
		if (album == null)
			return null;
		// path为相对路径
		File target = new File(getProjectPath() + getAlbumRelativePath());
		if (!target.exists()) {
			target.mkdirs();
		}
		try {

			BufferedImage image = ImageIO.read(album);
			ImageIO.write(image, "JPEG", new File(target + File.separator
					+ album.getName()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// copy(img, target,userName);
		return getAlbumRelativePath() + File.separator + album.getName();// 路径问题
	}

	public static String getBasePath() {
		return (String) Play.configuration.get("userImgPath");
	}

	public static String getRelativePath() {
		return File.separator + "public" + File.separator + "images";
	}

	public static String getUserRelativePath() {
		return getRelativePath() + File.separator + USERFILEPATH;
	}

	public static String getAvatarRelativePath(String username) {
		return getUserRelativePath() + File.separator + AVATARFILEPATH
				+ File.separator + username;
	}

	public static String getAlbumRelativePath() {
		return getUserRelativePath() + File.separator + ALBUMFILEPATH;
	}

	public static String getProjectPath() {
		File currentFile = new File(FileUtil.class.getClassLoader()
				.getResource("").getPath());
		return currentFile.getParent();
	}

	public static String renameAvatarPath(String oldUserName,
			String newUserName, String oldAvatarPath) {
		File oldFile = new File(getProjectPath()
				+ getAvatarRelativePath(oldUserName) + File.separator
				+ oldUserName);
		File newFile = new File(getProjectPath()
				+ getAvatarRelativePath(oldUserName) + File.separator
				+ newUserName);
		oldFile.renameTo(newFile);
		String newAvatarPath = getAvatarRelativePath(oldUserName)
				+ File.separator + newUserName + File.separator + newUserName
				+ oldAvatarPath.substring(oldAvatarPath.lastIndexOf("."));
		String tmpPath = getAvatarRelativePath(oldUserName) + File.separator
				+ newUserName + File.separator + oldUserName
				+ oldAvatarPath.substring(oldAvatarPath.lastIndexOf("."));
		File oldAvatar = new File(getProjectPath() + tmpPath);
		File newAvatar = new File(getProjectPath() + newAvatarPath);
		oldAvatar.renameTo(newAvatar);
		return newAvatarPath;
	}

}
