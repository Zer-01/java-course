package edu.hw8.Task3.MultiThread;

import edu.hw8.Task3.BaseReader;
import edu.hw8.Task3.PasswordGenerator;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MTPasswordCracker {
    private final static Logger LOGGER = LogManager.getLogger();
    private final static int WAIT_TERMINATE_TIMEOUT = 100;
    private final Map<String, String> foundPasswords;
    private final Map<String, String> baseMap;
    private final int passwordSize;

    public MTPasswordCracker(int passSize, Path path) {
        baseMap = new ConcurrentHashMap<>(BaseReader.read(path));
        foundPasswords = new ConcurrentHashMap<>();
        this.passwordSize = passSize;
    }

    public void print() {
        for (var entry : foundPasswords.entrySet()) {
            LOGGER.info(entry.getKey() + "   " + entry.getValue());
        }
    }

    public Map<String, String> getResult() {
        return foundPasswords;
    }

    public void crack(int threadsCount) {
        if (threadsCount < 1) {
            throw new IllegalArgumentException();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);

        for (int i = 0; i < threadsCount; i++) {
            PasswordGenerator gen = new PasswordGenerator(passwordSize);
            gen.bound(i, threadsCount);
            executorService.submit(() -> hashCheckWorker3(gen));
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(WAIT_TERMINATE_TIMEOUT, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("MagicNumber")
    private static String getHash(String password) {
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

    private void hashCheckWorker3(PasswordGenerator generator) {
        while (!baseMap.isEmpty() && generator.hasNext()) {
            String pass = generator.nextPassword();
            if (pass != null) {
                String hash = getHash(pass);
                if (baseMap.containsKey(hash)) {
                    foundPasswords.put(baseMap.get(hash), pass);
                    baseMap.remove(hash);
                }
            }
        }
    }
}
