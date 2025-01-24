package com.gpwsofts.ffcalculator.mobile;

import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpwsofts.ffcalculator.mobile.model.Grid;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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

    /**
     * La liste des communes de France (utilisees dans le cadre de la construction d'un resultat)
     */
    private List<String> communes;
    /**
     *
     */
    private List<Grid> grids;

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
        loadGridsFromFile();
        loadCommunesFromFile();
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
                Espresso.onView(withId(R.id.idTIETPlace)).perform(replaceText(result.place));
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
        assertEquals("il y a des erreurs pour " + errors.size() + " resultats : " + errors, true, errors.isEmpty());
    }


    /**
     * la commune est piochée aléatoirement parmi les communes francaises
     * la grille correspod  l'index demandé
     * le resultat garantit une cohérence de la position et du nombre de prts
     *
     * @param index
     * @return une instance de RandomResult, résultat aléatoire
     */
    protected RandomResult getRandomResult(int index) {
        int gridsSize = grids.size();
        int communesSize = communes.size();
        Random rand = new Random();
        Grid grid = grids.get(index);
        int maxPos = grid.getMaxPos();
        String place = new StringBuilder().append(communes.get(rand.nextInt(communesSize - 1))).append(" (").append(index).append(")").toString();
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
     * chargement de la grille de points
     *
     * @throws IOException
     */
    protected void loadGridsFromFile() throws IOException {
        InputStream is = null;
        Type listGridType = null;
        String jsonDatas = null;
        Gson gson = null;
        try {
            is = InstrumentationRegistry.getInstrumentation().getContext().getResources().getAssets().open("grids/grilles.json");
            //"grids/grilles.json"
            int size = is.available();
            Log.v(TAG_NAME, "<" + size + "> octets lus - creation dun buffer de cette taille");
            byte[] buffer = new byte[size];
            is.read(buffer);
            listGridType = new TypeToken<List<Grid>>() {
            }.getType();
            jsonDatas = new String(buffer, StandardCharsets.UTF_8);
            Log.v(TAG_NAME, "construction d'une instance de liste depuis Gson");
            gson = new Gson();
            grids = gson.fromJson(jsonDatas, listGridType);
            Log.v(TAG_NAME, "tri de la liste des grilles");
            Collections.sort(grids);
        } finally {
            if (is != null)
                is.close();
        }
    }

    /**
     * changement des communes francaises
     *
     * @throws IOException
     */
    protected void loadCommunesFromFile() throws IOException {
        InputStream is = null;
        Scanner scanner = null;
        try {
            communes = new ArrayList<String>();
            is = InstrumentationRegistry.getInstrumentation().getContext().getResources().getAssets().open("grids/communes.txt");
            scanner = new Scanner(is);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                communes.add(line.trim());
            }
        } finally {
            if (is != null)
                is.close();
            if (scanner != null)
                scanner.close();
        }
    }

    /**
     * inner class pour encapsuler les données d'un resultat
     */
    private class RandomResult {
        String place;
        String classe;
        int pos;
        int prts;
        Exception e;
        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(place + " - classe = <"  + classe + "> - position = <" + pos + "> - prts = <" + prts + ">");
            if (e != null){
                sb.append(" - exception de type <" + e.getClass().getSimpleName() + "> - message = <" + e.getMessage()+ ">");
            } else sb.append("exception null");
            return sb.toString();
        }
    }
}