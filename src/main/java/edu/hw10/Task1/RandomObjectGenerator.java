package edu.hw10.Task1;

import edu.hw10.Task1.annotations.Max;
import edu.hw10.Task1.annotations.Min;
import edu.hw10.Task1.annotations.NotNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Random;

public class RandomObjectGenerator {
    private final static int CHAR_ALPHABET_SIZE = 26;
    private final static int MAX_RANDOM_STRING_LENGTH = 12;
    private final Random random;

    public RandomObjectGenerator() {
        this.random = new Random();
    }

    @SuppressWarnings("unchecked")
    public <T> T nextObject(Class<T> tClass, String factoryMethod) throws NoSuchMethodException {
        try {
            if (factoryMethod != null) {
                Method[] methods = tClass.getDeclaredMethods();
                Method factory = Arrays.stream(methods)
                    .filter(method -> method.getName().equals(factoryMethod))
                    .filter(method -> method.getGenericReturnType().getTypeName().equals(tClass.getName()))
                    .findFirst()
                    .orElseThrow(NoSuchMethodException::new);
                factory.setAccessible(true);
                Object[] parameters = Arrays.stream(factory.getParameters())
                    .map(this::generateParameterWithAnnotations)
                    .toArray();
                return (T) factory.invoke(null, parameters);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public <T> T nextObject(Class<T> tClass) {
        try {
            Constructor<T>[] constructors = (Constructor<T>[]) tClass.getDeclaredConstructors();
            Constructor<T> randomConstructor = constructors[random.nextInt(constructors.length)];
            randomConstructor.setAccessible(true);

            Object[] parameters = Arrays.stream(randomConstructor.getParameters())
                .map(this::generateParameterWithAnnotations)
                .toArray();

            return randomConstructor.newInstance(parameters);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Object generateParameterWithAnnotations(Parameter parameter) {
        Class<?> type = parameter.getType();

        if (parameter.isAnnotationPresent(NotNull.class)) {
            return generateNotNullValue(type);
        } else if (parameter.isAnnotationPresent(Min.class) || parameter.isAnnotationPresent(Max.class)) {
            return generateBoundedValue(type, parameter.getAnnotation(Min.class), parameter.getAnnotation(Max.class));
        } else {
            return generateRandomValue(type);
        }
    }

    private Object generateRandomValue(Class<?> type) {
        Object result = null;
        if (type == int.class || type == Integer.class) {
            result = random.nextInt();
        } else if (type == byte.class || type == Byte.class) {
            result = (byte) random.nextInt();
        } else if (type == float.class || type == Float.class) {
            result = random.nextFloat();
        } else if (type == double.class || type == Double.class) {
            result = random.nextDouble();
        } else if (type == boolean.class || type == Boolean.class) {
            result = random.nextBoolean();
        } else if (type == char.class || type == Character.class) {
            result = (char) (random.nextInt(CHAR_ALPHABET_SIZE) + 'a');
        } else if (type == String.class) {
            result = generateRandomString();
        }

        return result;
    }

    private Object generateNotNullValue(Class<?> type) {
        Object value = generateRandomValue(type);
        if (value == null) {
            throw new RuntimeException("Annotation mismatch");
        }
        return value;
    }

    private Object generateBoundedValue(Class<?> type, Min minAnnotation, Max maxAnnotation) {
        double minValue = (minAnnotation != null) ? minAnnotation.value() : Double.MIN_VALUE;
        double maxValue = (maxAnnotation != null) ? maxAnnotation.value() : Double.MAX_VALUE;
        Object result;

        if (type == int.class || type == Integer.class) {
            result = random.nextInt(
                (int) Math.max(minValue, Integer.MIN_VALUE),
                (int) Math.min(maxValue, Integer.MAX_VALUE)
            );
        } else if (type == byte.class || type == Byte.class) {
            result = (byte) random.nextInt(
                (int) Math.max(minValue, Byte.MIN_VALUE),
                (int) Math.min(maxValue, Byte.MAX_VALUE)
            );
        } else if (type == float.class || type == Float.class) {
            result = random.nextFloat(
                (float) Math.max(minValue, Float.MIN_VALUE),
                (float) Math.min(maxValue, Float.MAX_VALUE)
            );
        } else if (type == double.class || type == Double.class) {
            result = random.nextDouble(
                Math.max(minValue, Double.MIN_VALUE),
                Math.min(maxValue, Double.MAX_VALUE)
            );
        } else {
            result = generateRandomValue(type);
        }

        return result;
    }

    private String generateRandomString() {
        int length = random.nextInt(MAX_RANDOM_STRING_LENGTH);
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(CHAR_ALPHABET_SIZE) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }
}
