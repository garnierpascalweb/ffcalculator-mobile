package com.gpwsofts.ffcalculator.mobile.services.town;

import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;

import java.io.IOException;
import java.util.List;

public interface ITownService extends ICleanableService {
    List<String> getTowns() throws IOException;
}
