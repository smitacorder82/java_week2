import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class URLShortener {
    public static String shortenURL(String longURL) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(longURL.getBytes());
            String base64 = Base64.getEncoder().encodeToString(digest);
            // Use the first 6 characters from the base64 string as the short URL
            return base64.substring(0, 6);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
