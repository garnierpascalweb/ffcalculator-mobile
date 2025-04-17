package com.gpwsofts.ffcalculator.mobile.services.town;

import com.gpwsofts.ffcalculator.mobile.common.reader.ReaderProvider;

public abstract class AbstractTownService implements ITownService {
    protected final ReaderProvider readerProvider;
    public AbstractTownService(ReaderProvider readerProvider) {
        this.readerProvider = readerProvider;
    }
}
