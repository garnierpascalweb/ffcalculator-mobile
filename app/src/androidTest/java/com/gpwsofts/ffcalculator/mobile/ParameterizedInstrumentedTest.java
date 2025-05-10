package com.gpwsofts.ffcalculator.mobile;

import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.gpwsofts.ffcalculator.mobile.common.reader.AssetReaderProvider;
import com.gpwsofts.ffcalculator.mobile.model.grid.IGrid;
import com.gpwsofts.ffcalculator.mobile.services.grid.SimpleGridService;
import com.gpwsofts.ffcalculator.mobile.services.town.SimpleTownService;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(Parameterized.class)
public class ParameterizedInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    private final RandomResult testCase;

    public ParameterizedInstrumentedTest(RandomResult testCase) {
        this.testCase = testCase;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<RandomResult> data() throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();

        SimpleTownService townService = new SimpleTownService(new AssetReaderProvider(context));
        List<String> towns = townService.getTowns();

        SimpleGridService gridService = new SimpleGridService(new AssetReaderProvider(context));
        List<IGrid> grids = gridService.getGrids();

        List<RandomResult> results = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < grids.size(); i++) {
            IGrid grid = grids.get(i);
            String place = towns.get(random.nextInt(towns.size())) + " (" + i + ")";
            String classe = grid.getSpinnerItemValue();
            int pos = 1 + random.nextInt(grid.getMaxPos());
            int prts = pos + random.nextInt(100);

            results.add(new RandomResult(place, classe, pos, prts));
        }

        return results;
    }

    @Test
    public void testAjoutResultat() throws InterruptedException {
        Espresso.onView(withId(R.id.navigation_result)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.idTIETPlaceAutoComplete)).perform(replaceText(testCase.place));
        Espresso.onView(withId(R.id.idTVAutoClasses)).perform(replaceText(testCase.classe));
        Espresso.onView(withId(R.id.idTVAutoPos)).perform(replaceText(String.valueOf(testCase.pos)));
        Espresso.onView(withId(R.id.idTVAutoPrts)).perform(replaceText(String.valueOf(testCase.prts)));
        Espresso.onView(withId(R.id.idBTAjouter)).perform(ViewActions.click());

        Thread.sleep(1000); // facultatif pour laisser l’UI traiter
    }

    public static class RandomResult {
        public final String place;
        public final String classe;
        public final int pos;
        public final int prts;

        public RandomResult(String place, String classe, int pos, int prts) {
            this.place = place;
            this.classe = classe;
            this.pos = pos;
            this.prts = prts;
        }

        @Override
        public String toString() {
            return place + " - classe=" + classe + " - pos=" + pos + " - prts=" + prts;
        }
    }
}
