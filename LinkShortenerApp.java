import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LinkShortenerApp {
    private Map<String, String> shortToLongMap = new HashMap<>();
    private Map<String, String> longToShortMap = new HashMap<>();
    private MessageDigest md;

    public LinkShortenerApp() {
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String shorten(String longUrl) {
        byte[] shaBytes = md.digest(longUrl.getBytes());
        String shortUrl = base62Encode(shaBytes);
        shortToLongMap.put(shortUrl, longUrl);
        longToShortMap.put(longUrl, shortUrl);
        return shortUrl;
    }

    public String expand(String shortUrl) {
        return shortToLongMap.get(shortUrl);
    }

    private String base62Encode(byte[] bytes) {
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder encoded = new StringBuilder();
        long number = 0;

        for (int i = 0; i < bytes.length; i++) {
            number = (number << 8) | (bytes[i] & 0xFF);
        }

        while (number > 0) {
            encoded.insert(0, characters.charAt((int) (number % 62)));
            number /= 62;
        }

        return encoded.toString();
    }

    public static void main(String[] args) {
        LinkShortenerApp urlShortener = new LinkShortenerApp();
        String longUrl = "https://www.example.com";
        String shortUrl = urlShortener.shorten(longUrl);
        System.out.println("Shortened URL: " + shortUrl);
        System.out.println("Original URL: " + urlShortener.expand(shortUrl));
    }
}
