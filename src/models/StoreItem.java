package models;

import java.util.HashMap;
import java.util.Map;

import models.exceptions.NotAnImageException;
import models.exceptions.SecurityBreachException;
import utilities.ValidImageChecker;

public class StoreItem {

	int id;
	Map<String, String> info;

	public StoreItem() {
		info = new HashMap<String, String>();
	}

	public int getId() {
		return id;
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

	public void setInfo(String key, String value) {
		info.put(key, value);
	}

	public String getInfo(String key) {
		String out = info.get(key);
		if (out == null) out = "";
		return out;
	}

	public void setName(String name) throws SecurityBreachException {
		if (null == name || name.length() > 100) throw new SecurityBreachException();
		info.put("name", name);
	}

	public void setDescription(String description) throws SecurityBreachException {
		if (null == description || description.equals("")) return;
		if (description.length() > 1000) throw new SecurityBreachException();
		info.put("description", description);
	}

	public void setImage(String image) throws SecurityBreachException, NotAnImageException {
		if (null == image || image.equals("")) return;
		if (image.length() > 100) throw new SecurityBreachException();
		if (ValidImageChecker.isImage(image) == false) throw new NotAnImageException();
		info.put("image", image);
	}

	public void setPrice(String price) throws SecurityBreachException {
		try {
			int p = Integer.parseInt(price);
			if (p <= 0) throw new SecurityBreachException();
			info.put("price", price);
		} catch (Exception e) {
			throw new SecurityBreachException();
		}
	}

	public static void main(String[] args) {
		Float.parseFloat("1");
	}

}
