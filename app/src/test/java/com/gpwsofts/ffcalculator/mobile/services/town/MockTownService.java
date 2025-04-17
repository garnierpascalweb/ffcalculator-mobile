package com.gpwsofts.ffcalculator.mobile.services.town;

import com.gpwsofts.ffcalculator.mobile.common.reader.ReaderProvider;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class MockTownService extends AbstractTownService {

    public MockTownService(ReaderProvider readerProvider) {
        super(readerProvider);
    }

    @Override
    public List<String> getTowns() throws IOException {
        return Collections.emptyList();
    }

    @Override
    public void clean() {

    }
}
