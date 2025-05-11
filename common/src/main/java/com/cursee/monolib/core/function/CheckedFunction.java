package com.cursee.monolib.core.function;

/**
 * Example Usage:
 *
 * <pre>{@code
 * CheckedFunction<String, String> readFirstLine = path -> {
 *     try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
 *         return reader.readLine();
 *     }
 * };
 *
 * try {
 *     String firstLine = readFirstLine.apply("example.txt");
 *     System.out.println(firstLine);
 * }
 * catch (Exception e) {
 *     e.printStackTrace();
 * }
 * }</pre>
 */
@FunctionalInterface
public interface CheckedFunction<T, R> {
    R apply(T t) throws Exception;
}
