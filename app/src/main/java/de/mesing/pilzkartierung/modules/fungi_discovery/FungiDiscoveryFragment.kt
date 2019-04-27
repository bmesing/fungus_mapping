package de.mesing.pilzkartierung.modules.fungi_discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import de.mesing.pilzkartierung.R
import kotlinx.android.synthetic.main.fragment_fungi_discovery.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
*/
class FungiDiscoveryFragment : Fragment() {

    val interactor = FungiDiscoveryInteractor()

    companion object {
        fun newInstance() : FungiDiscoveryFragment {
            return FungiDiscoveryFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fungi_discovery, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = FungiDiscoveryRecyclerViewAdapter()
        share_button.setOnClickListener {
            interactor.shareDiscoveryList(requireContext())
        }
    }
}
