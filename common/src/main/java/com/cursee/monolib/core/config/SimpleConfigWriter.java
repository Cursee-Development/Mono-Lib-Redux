package com.cursee.monolib.core.config;

import com.cursee.monolib.util.toml.DatePolicy;
import com.cursee.monolib.util.toml.IndentationPolicy;
import com.cursee.monolib.util.toml.ValueWriter;
import com.cursee.monolib.util.toml.WriterContext;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TimeZone;

import static com.cursee.monolib.util.toml.MapValueWriter.MAP_VALUE_WRITER;
import static com.cursee.monolib.util.toml.ValueWriters.WRITERS;

@Deprecated(since = "2.1.0", forRemoval = true)
public class SimpleConfigWriter {

  public static void writeObjectToFile(Map<String, ?> from, File target) throws IOException {
      try (OutputStream outputStream = new FileOutputStream(target)) {
          writeObjectToOutputStream(from, outputStream);
      }
  }

  public static void writeObjectToOutputStream(Map<String, ?> from, OutputStream target) throws IOException {
    OutputStreamWriter writer = new OutputStreamWriter(target, StandardCharsets.UTF_8);
    writeObjectToOutputStreamWriter(from, writer);
    writer.flush();
  }

  public static void writeObjectToOutputStreamWriter(Map<String, ?> from, Writer target) throws IOException {

    ValueWriter valueWriter = WRITERS.findWriterFor(from);

    if (!(valueWriter == MAP_VALUE_WRITER)) {
      throw new IllegalArgumentException("An object of class " + from.getClass().getSimpleName() + " is invalid for SimpleConfig parsing.");
    }

    WriterContext context = new WriterContext(new IndentationPolicy(0, 0, 0), new DatePolicy(TimeZone.getTimeZone("UTC"), false), target);
    valueWriter.write(from, context);
  }

  public static SimpleConfig writeObjectToFileWithReturn(Map<String, ?> from, File target) throws IOException {
    try (OutputStream outputStream = new FileOutputStream(target)) {
      return writeObjectToOutputStreamWithReturn(from, target, outputStream);
    }
  }

  public static SimpleConfig writeObjectToOutputStreamWithReturn(Map<String, ?> from, File file, OutputStream target) throws IOException {
    OutputStreamWriter writer = new OutputStreamWriter(target, StandardCharsets.UTF_8);
    SimpleConfig toReturn = writeObjectToOutputStreamWriterWithReturn(from, file, writer);
    writer.flush();

    return toReturn;
  }

  public static SimpleConfig writeObjectToOutputStreamWriterWithReturn(Map<String, ?> from, File file, Writer target) throws IOException {

    ValueWriter valueWriter = WRITERS.findWriterFor(from);

    if (!(valueWriter == MAP_VALUE_WRITER)) {
      throw new IllegalArgumentException("An object of class " + from.getClass().getSimpleName() + " is invalid for SimpleConfig parsing.");
    }

    WriterContext context = new WriterContext(new IndentationPolicy(0, 0, 0), new DatePolicy(TimeZone.getTimeZone("UTC"), false), target);
    valueWriter.write(from, context);

    return SimpleConfig.read(file);
  }
}
