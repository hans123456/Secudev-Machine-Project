package utilities;

import java.util.HashMap;
import java.util.Map;

import com.paypal.base.Constants;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

public class PayPalAccess {

	private static String ClientID = "AVy6zrSTVCeJihOa0tL_8Eyvz6l1HDwANrShMtZ-GG2sSyBoUyurPdzy8MuIetVM-0dJwHiH47FXSV1k";
	private static String Secret = "EKR3ps6au38cDd0eRXlaOfz-Eomo7D1fS_0ZWhpAyyPyUQYJuADczOS__LoIxidpLZ9vHm-ss_to2xOx";

	private static String accessToken;

	private static Map<String, String> sdkConfig;

	static {
		sdkConfig = new HashMap<String, String>();
		sdkConfig.put(Constants.MODE, Constants.SANDBOX);
		try {
			accessToken = new OAuthTokenCredential(ClientID, Secret, sdkConfig).getAccessToken();
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
	}

	public static String getAccessToken() {
		return accessToken;
	}

	public static Map<String, String> getSdkConfig() {
		return sdkConfig;
	}

}
