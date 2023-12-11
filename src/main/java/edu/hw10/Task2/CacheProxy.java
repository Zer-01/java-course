package edu.hw10.Task2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CacheProxy implements InvocationHandler {
    private final Object target;
    private final Map<String, Object> cache;
    private final boolean persist;
    private final Path path;

    public CacheProxy(Object target, boolean persist, Path path) {
        if (target == null || persist && path == null) {
            throw new IllegalArgumentException();
        }

        this.target = target;
        this.persist = persist;
        this.cache = new HashMap<>();
        this.path = path;
        try {
            if (persist && Files.exists(path)) {
                loadCache();
            } else if (persist) {
                Files.createFile(path);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(T target, Class<?> interfaceClass, Path path1) {
        return (T) Proxy.newProxyInstance(
            interfaceClass.getClassLoader(),
            interfaceClass.getInterfaces(),
            new CacheProxy(target, true, path1)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Cache.class)) {
            String key = generateKey(method, args);
            if (cache.containsKey(key)) {
                return cache.get(key);
            }

            Object result = method.invoke(target, args);
            cache.put(key, result);

            if (persist) {
                saveCache();
            }

            return result;
        } else {
            return method.invoke(target, args);
        }
    }

    private String generateKey(Method method, Object[] args) {
        StringBuilder stringBuilder = new StringBuilder(method.getName());
        for (Object arg : args) {
            stringBuilder.append("-").append(arg.toString());
        }
        return stringBuilder.toString();
    }

    void saveCache() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toString()))) {
            oos.writeObject(cache);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCache() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toString()))) {
            if (ois.available() > 0) {
                Object loadedCache = ois.readObject();
                if (loadedCache instanceof Map) {
                    cache.putAll((Map<String, Object>) loadedCache);
                }
            }
        }
    }
}
