package shiro;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.SimpleByteSource;

public class ShiroPasswordManager extends PasswordMatcher {

	private static DefaultHashService hashService;
	private static DefaultPasswordService passwordService;

	{
		hashService = new DefaultHashService();
		hashService.setHashIterations(500000);
		hashService.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
		hashService.setPrivateSalt(new SimpleByteSource("SeCuDeV"));
		hashService.setGeneratePublicSalt(true);
		passwordService = new DefaultPasswordService();
		passwordService.setHashService(hashService);
		this.setPasswordService(passwordService);
	}

	public static String encryptPassword(String pass) {
		return passwordService.encryptPassword(pass);
	}

	public static void main(String[] args) {
		System.out.println(new ShiroPasswordManager().encryptPassword("root"));
	}

}
