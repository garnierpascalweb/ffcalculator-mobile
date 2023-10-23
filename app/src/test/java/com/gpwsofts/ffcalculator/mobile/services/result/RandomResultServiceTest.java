package com.gpwsofts.ffcalculator.mobile.services.result;

import static com.gpwsofts.ffcalculator.mobile.services.result.MockResultService.MOCK_VALUE;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

public class RandomResultServiceTest {
    @Test
    public void testService() {
        RandomResultService service = new RandomResultService();
        double pts = service.getPts(0,0,"1.24.2");
        Assert.assertNotNull(pts);
    }
}
