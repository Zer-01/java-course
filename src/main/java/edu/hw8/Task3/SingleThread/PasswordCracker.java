package edu.hw8.Task3.SingleThread;

import edu.hw8.Task3.BaseReader;
import edu.hw8.Task3.PasswordGenerator;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PasswordCracker {
    private final static Logger LOGGER = LogManager.getLogger();
    private final Map<String, String> foundPasswords;
    private final Map<String, String> baseMap;
    private final PasswordGenerator generator;

    public PasswordCracker(int passSize, Path path) {
        generator = new PasswordGenerator(passSize);
        baseMap = BaseReader.read(path);
        foundPasswords = new HashMap<>();
    }

    public void print() {
        for (var entry : foundPasswords.entrySet()) {
            LOGGER.info(entry.getKey() + "   " + entry.getValue());
        }
    }

    public Map<String, String> getResult() {
        return foundPasswords;
    }

    public void crack() {
        while (!baseMap.isEmpty() && generator.hasNext()) {
            String pass = generator.nextPassword();
            String hash = getHash(pass);
            if (baseMap.containsKey(hash)) {
                foundPasswords.put(baseMap.get(hash), pass);
                baseMap.remove(hash);
            }
        }
    }

    @SuppressWarnings("MagicNumber")
    private String getHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            byte[] md5Hash = md.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte b : md5Hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
