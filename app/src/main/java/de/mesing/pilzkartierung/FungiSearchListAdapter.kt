package de.mesing.pilzkartierung

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import de.mesing.pilzkartierung.domain.FungusNameSearch

class FungiSearchListAdapter(context: Context, textViewResourceId: Int) : ArrayAdapter<String>(context, textViewResourceId) {

    private var suggestionList : List<String> = ArrayList()

    override fun getCount(): Int {
        return suggestionList.size
    }

    override fun getItem(index: Int): String? {
        return suggestionList[index]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    suggestionList = FungusNameSearch.findBySearchString(constraint.toString())
                    // Now assign the values and count to the FilterResults object
                    filterResults.values = suggestionList
                    filterResults.count = suggestionList.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }

}