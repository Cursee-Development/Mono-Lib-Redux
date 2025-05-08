package com.cursee.monolib.core.serialization;

/** Adapted from Darkhax's <a href="https://github.com/Darkhax-Minecraft/Bookshelf">Bookshelf</a>. */
public class NBTParseException extends RuntimeException {

    public NBTParseException(String msg) {

        super(msg);
    }

    public NBTParseException(String msg, Throwable cause) {

        super(msg, cause);
    }

    public NBTParseException(Throwable cause) {

        super(cause);
    }
}
