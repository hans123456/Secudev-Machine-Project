package models;

import java.util.HashMap;

import models.exceptions.SecurityBreachException;

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
	}

	public int getId() {
		return this.id;
	}

	public void setPost(String post) throws SecurityBreachException {
		if (post == null || post.length() <= 0 || post.length() > 1000) throw new SecurityBreachException();
		this.info.put("post", post);
	}

	public void setUsername(String username) throws SecurityBreachException {
		if (username == null) throw new SecurityBreachException();
		this.info.put("username", username);
	}

}
