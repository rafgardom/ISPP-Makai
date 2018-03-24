
package utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNamePasswordValidator {

	private final Pattern		passwordPattern;
	private final Pattern		userNamePattern;
	private Matcher				passwordMatcher;
	private Matcher				userNameMatcher;

	private static final String	PASSWORD_PATTERN	= "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,30})";
	private static final String	USERNAME_PATTERN	= "[^A-Za-z0-9]";


	public UserNamePasswordValidator() {
		this.passwordPattern = Pattern.compile(UserNamePasswordValidator.PASSWORD_PATTERN);
		this.userNamePattern = Pattern.compile(UserNamePasswordValidator.USERNAME_PATTERN);
	}

	public boolean passwordValidate(final String password) {

		this.passwordMatcher = this.passwordPattern.matcher(password);
		return this.passwordMatcher.matches();

	}

	public boolean userNameValidate(final String userName) {
		boolean result = false;
		for (final char i : userName.toCharArray()) {
			final String check = Character.toString(i);
			if (this.userNamePattern.matcher(check).matches()) {
				result = true;
				break;
			}
		}

		return result;

	}
}
