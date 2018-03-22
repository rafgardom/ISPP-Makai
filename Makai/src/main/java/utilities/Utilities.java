
package utilities;

import org.springframework.security.crypto.codec.Base64;

public class Utilities {

	public static String showImage(final byte[] picture) {

		String result;
		byte[] base64;
		StringBuilder imageString;

		base64 = Base64.encode(picture);
		imageString = new StringBuilder();
		imageString.append("data:image/png;base64,");
		imageString.append(new String(base64));
		result = imageString.toString();

		return result;
	}
}
