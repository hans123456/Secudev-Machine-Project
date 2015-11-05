package utilities;

import java.util.HashMap;
import java.util.Map;

import com.paypal.base.Constants;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

public class PayPalAccess {

	private static String ClientID = "AVy6zrSTVCeJihOa0tL_8Eyvz6l1HDwANrShMtZ-GG2sSyBoUyurPdzy8MuIetVM-0dJwHiH47FXSV1k";
	private static String Secret = "EKR3ps6au38cDd0eRXlaOfz-Eomo7D1fS_0ZWhpAyyPyUQYJuADczOS__LoIxidpLZ9vHm-ss_to2xOx";

	private static OAuthTokenCredential tokenCredential;
	private static String accessToken;
	private static long timeUpdated;
	private static long expiresIn;

	private static Map<String, String> sdkConfig;

	static {
		sdkConfig = new HashMap<String, String>();
		sdkConfig.put(Constants.MODE, Constants.SANDBOX);
		timeUpdated = expiresIn = 0;
	}

	public static synchronized String getAccessToken() {
		// long currentTime = System.currentTimeMillis();
		// if (currentTime - timeUpdated > expiresIn) {
		boolean success = false;
		while (success == false) {
			try {
				tokenCredential = new OAuthTokenCredential(ClientID, Secret, sdkConfig);
				accessToken = tokenCredential.getAccessToken();
				// expiresIn = tokenCredential.expiresIn() * 900;
				success = true;
			} catch (PayPalRESTException e) {
				e.printStackTrace();
			}
		}
		// }
		return accessToken;
	}

	public static Map<String, String> getSdkConfig() {
		return sdkConfig;
	}

}
