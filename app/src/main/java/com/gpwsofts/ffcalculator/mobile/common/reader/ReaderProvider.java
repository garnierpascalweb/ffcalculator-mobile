package com.gpwsofts.ffcalculator.mobile.common.reader;

import java.io.BufferedReader;
import java.io.IOException;

@FunctionalInterface
public interface ReaderProvider {
    BufferedReader openReader(String path) throws IOException;
}