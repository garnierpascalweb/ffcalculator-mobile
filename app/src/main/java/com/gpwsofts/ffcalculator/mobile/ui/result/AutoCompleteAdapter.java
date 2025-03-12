package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter pour l'AutoCompleteTextView
 * L'AutoCompleteTextView nécessite un ArrayAdapter ou un adaptateur personnalisé. Dans ce cas, vous pouvez utiliser un ArrayAdapter standard, mais vous devez adapter les données du ListAdapter pour l'utiliser avec l'autocomplétion.
 * Voici un exemple d'adaptateur personnalisé pour connecter le AutoCompleteTextView à votre RecyclerView en utilisant les données :
 * @since 1.0.0
 */
public class AutoCompleteAdapter extends ArrayAdapter<String> {
    private static final String TAG_NAME = "AutoCompleteAdapter";
    private List<String> itemList;
    private List<String> initialItemList;

    public AutoCompleteAdapter(Context context, List<String> itemList) {
        super(context, R.layout.simple_spinner_item, itemList);
        this.itemList = itemList;
        this.initialItemList = new ArrayList<>(itemList);
        LogUtils.v(TAG_NAME, "instanciation de AutoCompleteAdapter avec une liste de <" + itemList + "> villes");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Retourne la vue associée à l'élément
        View view = super.getView(position, convertView, parent);
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    // Lorsque le texte est vide, on rétablit toute la liste
                    results.count = initialItemList.size();
                    results.values = initialItemList;
                    LogUtils.d(TAG_NAME, " texte tapé a vide <" + constraint + ">, retablissement de la liste initiale a <" + results.values + "> valeurs");
                } else {
                    List<String> filteredItems = new ArrayList<>();
                    for (String item : initialItemList) {
                        if (item.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredItems.add(item);
                        }
                    }
                    results.count = filteredItems.size();
                    results.values = filteredItems;
                }
                LogUtils.d(TAG_NAME, " texte tapé <" + constraint + ">, application du filtre qui nous rend <" + results.count + "> valeurs, la recherche ayant ete effectuée sur <" + itemList.size() + "> items");
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    LogUtils.d(TAG_NAME, " publication des <" + results.count + "> resultats");
                    clear();
                    addAll((List<String>) results.values);
                    notifyDataSetChanged();
                }
            }
        };
    }
}
