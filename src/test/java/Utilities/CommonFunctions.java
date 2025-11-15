package Utilities;

public class CommonFunctions {
	public static String generateRandomEmail() {
		String email = java.util.UUID.randomUUID().toString().substring(0, 8) + "@gmail.com";
		return email;
	}
}
