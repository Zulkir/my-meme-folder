package com.mymemefolder.mmfgateway.utils;

import java.io.InputStream;

public class InputStreamWithLength {
    private final InputStream stream;
    private final long length;

    public InputStreamWithLength(InputStream stream, long length) {
        this.stream = stream;
        this.length = length;
    }

    public InputStream getStream() {
        return stream;
    }

    public long getLength() {
        return length;
    }
}
