package de.mesing.pilzkartierung.modules.fungi_discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import de.mesing.pilzkartierung.R
import kotlinx.android.synthetic.main.fragment_fungi_discovery.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
*/
class FungiDiscoveryFragment : Fragment() {

    private val interactor = FungiDiscoveryInteractor()
    lateinit var adapter : FungiDiscoveryRecyclerViewAdapter

    companion object {
        fun newInstance() : FungiDiscoveryFragment {
            return FungiDiscoveryFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fungi_discovery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.layoutManager = LinearLayoutManager(context)
        adapter = FungiDiscoveryRecyclerViewAdapter()
        list.adapter = adapter
        share_button.setOnClickListener { interactor.shareDiscoveryList(requireContext()) }
        delete_button.setOnClickListener { requestClearList() }
    }

    private fun requestClearList() {
        AlertDialog.Builder(requireContext())
                .setMessage(R.string.confirm_delete_dialog_text)
                .setPositiveButton(R.string.confirm_delete_positive_button_text) { _, _ ->
                    interactor.deleteDiscoveryList()
                    adapter.reloadDiscoveryList()
                }
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show()
    }

}
