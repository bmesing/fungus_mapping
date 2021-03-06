package de.mesing.pilzkartierung.modules.fungidiscovery


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.mesing.pilzkartierung.R
import de.mesing.pilzkartierung.domain.FungusDiscoveryRegistry
import kotlinx.android.synthetic.main.view_fungidiscovery.view.*

class FungiDiscoveryRecyclerViewAdapter
    : RecyclerView.Adapter<FungiDiscoveryRecyclerViewAdapter.ViewHolder>() {

    private var values: List<FungusDiscoveryRegistry.FungusDiscovery> = FungusDiscoveryRegistry.getDiscoveries()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_fungidiscovery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.latinNameView.text = item.fungus.latinName()
        holder.dateView.text = item.time.toString()
        holder.fungiCountView.text = item.count.toString()
    }

    override fun getItemCount(): Int = values.size

    fun reloadDiscoveryList() {
        values = FungusDiscoveryRegistry.getDiscoveries()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val latinNameView: TextView = view.latin_name
        val fungiCountView: TextView = view.fungi_count
        val dateView: TextView = view.discovery_date
    }
}
