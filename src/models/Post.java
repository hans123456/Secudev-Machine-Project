package models;

import java.util.HashMap;
import java.util.regex.Pattern;

import models.exceptions.NotAnImageException;
import models.exceptions.SecurityBreachException;
import utilities.ValidImageChecker;

public class Post {

	private int id;
	private HashMap<String, String> info;

	public Post() {
		this.info = new HashMap<String, String>();
	}

	public String getInfo(String key) {
		return this.info.get(key);
	}

	public void setInfo(String key, String value) {
		this.info.put(key, value);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIdString(String id) throws SecurityBreachException {
		try {
			this.id = Integer.parseInt(id);
		} catch (Exception e) {
			throw new SecurityBreachException();
		}
		this.info.put("id", id);
	}

	public int getId() {
		return this.id;
	}

	public final static Pattern ImagePattern = Pattern.compile("\\[img](.*?)\\[/img]");

	public void setPost(String post) throws SecurityBreachException, NotAnImageException {
		if (post == null || post.length() <= 0 || post.length() > 5000) throw new SecurityBreachException();
		if (!ValidImageChecker.checkUsingRegex(ImagePattern, post)) throw new NotAnImageException();
		this.info.put("post", post);
	}

	public void setUsername(String username) throws SecurityBreachException {
		if (username == null) throw new SecurityBreachException();
		this.info.put("username", username);
	}

}
