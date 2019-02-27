package de.mesing.pilzkartierung.modules.fungi_discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.mesing.pilzkartierung.R

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
*/
class FungiDiscoveryFragment : Fragment() {

    // TODO: auf columnCount 3 umstellen und Adapter refactoren:
    // https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
    private var columnCount = 1

    companion object {
        fun newInstance() : FungiDiscoveryFragment {
            return FungiDiscoveryFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.view_fungidiscovery_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = FungiDiscoveryRecyclerViewAdapter()
            }
        }
        return view
    }
}
