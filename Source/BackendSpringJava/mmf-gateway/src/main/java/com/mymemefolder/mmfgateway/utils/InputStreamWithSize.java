package com.mymemefolder.mmfgateway.utils;

import java.io.InputStream;

public class InputStreamWithSize {
    private final InputStream stream;
    private final long size;

    public InputStreamWithSize(InputStream stream, long size) {
        this.stream = stream;
        this.size = size;
    }

    public InputStream getStream() {
        return stream;
    }

    public long getSize() {
        return size;
    }
}
