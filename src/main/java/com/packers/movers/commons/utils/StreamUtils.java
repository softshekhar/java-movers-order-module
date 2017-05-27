package com.packers.movers.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StreamUtils {

    public static void copy(InputStream source, OutputStream destination) throws IOException {
        byte[] buffer = new byte[4096];

        int bytesRead = source.read(buffer);
        while (bytesRead != -1) {
            destination.write(buffer, 0, bytesRead);
            bytesRead = source.read(buffer);
        }
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            copy(inputStream, output);

            return output.toByteArray();
        }
    }

    public static String toString(InputStream inputStream, Charset charset) throws IOException {
        return new String(getBytes(inputStream), charset);
    }

    public static String toString(InputStream inputStream) throws IOException {
        return toString(inputStream, StandardCharsets.UTF_8);
    }
}