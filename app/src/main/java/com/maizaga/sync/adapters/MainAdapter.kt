package com.maizaga.sync.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.maizaga.sync.R
import kotlinx.android.synthetic.main.list_item_row.view.*

/**
 *
 * Created by maizaga on 2019-09-03.
 */
class MainAdapter(private val context: Context): RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var selectedJson = 0
    private val jsonResources = arrayListOf(
        R.raw.export, R.raw.sessions_initial, R.raw.sessions_edited,
        R.raw.sessions_deleted)
    // This is the actual data source, if it was a dynamic one, it should be exposed to be changed.
    // After that change, notifyDataSetChanged() should be called to update the view.
    private val jsonResourcesText = arrayListOf("Export", "sessions_initial", "sessions_edited",
        "sessions_deleted")
    val selectedJsonResource
        get() = jsonResources[selectedJson]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_row, parent,
            false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title?.text = jsonResourcesText[position]

        if (selectedJson == position) {
            holder.background?.setBackgroundColor(ResourcesCompat.getColor(context.resources,
                R.color.colorPrimary, null))
        } else {
            holder.background?.setBackgroundColor(ResourcesCompat.getColor(context.resources,
                android.R.color.transparent, null))
        }

        holder.background?.setOnClickListener {
            val previous = selectedJson
            selectedJson = position
            notifyItemChanged(previous)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = jsonResources.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView? = itemView.itemTitle
        val background: LinearLayout? = itemView.itemBackground
    }
}