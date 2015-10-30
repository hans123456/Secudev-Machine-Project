package utilities;

import javax.servlet.http.HttpServletRequest;

public class Site {

	private static String url = null;

	public static String getSiteURL(HttpServletRequest request) {
		if (url == null)
			url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
		return url;
	}

}
