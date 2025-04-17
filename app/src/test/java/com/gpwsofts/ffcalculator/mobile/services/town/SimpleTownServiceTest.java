package com.gpwsofts.ffcalculator.mobile.services.town;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.gpwsofts.ffcalculator.mobile.common.reader.ReaderProvider;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class SimpleTownServiceTest {
    private SimpleTownService service;

    @Before
    public void setup() {
        ReaderProvider mockReaderProvider = path -> {
            String fakeFileContent = "Paris\nLyon\nMarseille";
            return new BufferedReader(new StringReader(fakeFileContent));
        };
        service = new SimpleTownService(mockReaderProvider);
    }

    @Test
    public void testGetTowns_returnsCorrectList() throws IOException {
        List<String> towns = service.getTowns();

        assertNotNull(towns);
        assertEquals(3, towns.size());
        assertEquals("Paris", towns.get(0));
        assertEquals("Lyon", towns.get(1));
        assertEquals("Marseille", towns.get(2));
    }

    @Test
    public void testClean_clearsTowns() throws IOException {
        service.getTowns(); // charge la liste
        service.clean();    // nettoyage
        assertNotNull(service.getTowns());
        assertEquals(3, service.getTowns().size()); // rechargée automatiquement
    }
}
