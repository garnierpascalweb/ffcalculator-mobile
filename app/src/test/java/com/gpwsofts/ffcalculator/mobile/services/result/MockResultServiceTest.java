package com.gpwsofts.ffcalculator.mobile.services.result;

import static com.gpwsofts.ffcalculator.mobile.services.result.MockResultService.MOCK_VALUE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MockResultServiceTest {
    @Test
    public void testService() {
        MockResultService service = new MockResultService();
        double pts = service.getPts(0,0,"1.24.2");
        assertEquals(MOCK_VALUE, pts,0);
    }
}
