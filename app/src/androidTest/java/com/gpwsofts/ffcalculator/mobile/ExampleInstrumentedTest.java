package com.gpwsofts.ffcalculator.mobile;

import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.gpwsofts.ffcalculator.mobile.common.reader.AssetReaderProvider;
import com.gpwsofts.ffcalculator.mobile.model.grid.IGrid;
import com.gpwsofts.ffcalculator.mobile.services.grid.IGridService;
import com.gpwsofts.ffcalculator.mobile.services.grid.SimpleGridService;
import com.gpwsofts.ffcalculator.mobile.services.town.ITownService;
import com.gpwsofts.ffcalculator.mobile.services.town.SimpleTownService;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Tests instrumentalisés a base d'Espresso
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG_NAME = "ExampleInstrumentedTest";
    //TODO 1.0.0 erreur on a vu des junior classe 1 avec le logo u17
    /**
     * le scenario rule
     */
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    private ITownService townsService;
    private IGridService gridService;
    private List<String> towns;/**
     *
     */
    private List<IGrid> grids;

    /**
     * Configuration du test
     * Chargement de la liste des grilles depuis assets
     * Chargement de la liste des communes
     * Boucle sur addResult
     *
     * @throws IOException
     */
    @Test
    public void setup() throws IOException {
        final Context context = InstrumentationRegistry.getInstrumentation().getContext();
        townsService = new SimpleTownService(new AssetReaderProvider(context));
        towns = townsService.getTowns();
        gridService = new SimpleGridService(new AssetReaderProvider(context));
        grids = gridService.getGrids();

        Espresso.onView(withId(R.id.navigation_result)).perform(ViewActions.click());
        StringBuilder sb = new StringBuilder();
        int nbTests = 84;
        int ok = 0;
        int ko = 0;
        List<RandomResult> errors = new ArrayList<RandomResult>();
        //TODO 1.0.0 test repeat comme avec testNg ?
        for (int index = 0; index < nbTests; index++) {
            RandomResult result = new RandomResult();
            try {
                result = getRandomResult(index);
                Espresso.onView(withId(R.id.navigation_result)).perform(ViewActions.click());
                Espresso.onView(withId(R.id.idTIETPlaceAutoComplete)).perform(replaceText(result.place));
                Espresso.onView(withId(R.id.idTVAutoClasses)).perform(replaceText(result.classe));
                Espresso.onView(withId(R.id.idTVAutoPos)).perform(replaceText(String.valueOf(result.pos)));
                Espresso.onView(withId(R.id.idTVAutoPrts)).perform(replaceText(String.valueOf(result.prts)));
                Espresso.onView(withId(R.id.idBTAjouter)).perform(ViewActions.click());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e){
                result.e = e;
                errors.add(result);
            } finally {

            }
        }
        assertTrue("il y a des erreurs pour " + errors.size() + " resultats : " + errors, errors.isEmpty());
    }


    /**
     * la commune est piochée aléatoirement parmi les communes francaises
     * la grille correspod  l'index demandé
     * le resultat garantit une cohérence de la position et du nombre de prts
     *
     * @param index
     * @return une instance de RandomResult, résultat aléatoire
     */
    private RandomResult getRandomResult(int index) {
        int communesSize = towns.size();
        Random rand = new Random();
        IGrid grid = grids.get(index);
        int maxPos = grid.getMaxPos();
        String place = towns.get(rand.nextInt(communesSize - 1)) + " (" + index + ")";
        String spinnerItemValue = grid.getSpinnerItemValue();
        int pos = 1 + rand.nextInt(maxPos);
        int prts = pos + rand.nextInt(100);
        RandomResult randomResult = new RandomResult();
        randomResult.classe = spinnerItemValue;
        randomResult.place = place;
        randomResult.pos = pos;
        randomResult.prts = prts;
        return randomResult;
    }

    /**
     * inner class pour encapsuler les données d'un resultat
     */
    private static class RandomResult {
        String place;
        String classe;
        int pos;
        int prts;
        Exception e;

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            //sb.append(place + " - classe = <"  + classe + "> - position = <" + pos + "> - prts = <" + prts + ">");
            sb.append(place).append(" - classe = <").append(classe).append("> - position = <").append(pos).append("> - prts = <").append(prts);// " - classe = <"  + classe + "> - position = <" + pos + "> - prts = <" + prts + ">");
            if (e != null){
                // sb.append(" - exception de type <" + e.getClass().getSimpleName() + "> - message = <" + e.getMessage()+ ">");
                sb.append(" - exception de type <").append(e.getClass().getSimpleName()).append("> - message = <").append(e.getMessage());
            } else sb.append("exception null");
            return sb.toString();
        }
    }
}