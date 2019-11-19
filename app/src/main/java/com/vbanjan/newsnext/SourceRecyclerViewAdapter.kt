package com.vbanjan.newsnext

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.source_list_item.view.*

class SourceRecyclerViewAdapter(
    val sources: ArrayList<Source>,
    val context: OnFragmentAdapterInteractionListener
) :
    RecyclerView.Adapter<SourceRecyclerViewAdapter.ViewHolder>() {

    private val mListener: OnFragmentAdapterInteractionListener = context

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val source: Source = sources[position]
        holder.sourceTitle.text = source.name
        holder.sourceDescription.text = source.description
        holder.sourceTitle.setOnClickListener {
            mListener.onSourceClick(sources[position])
        }
        holder.sourceDescription.setOnClickListener {
            mListener.onSourceClick(sources[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.source_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return sources.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sourceTitle: TextView = itemView.sourceTextView
        val sourceDescription: TextView = itemView.sourceDescriptionTextView
    }

    interface OnFragmentAdapterInteractionListener {
        fun onSourceClick(source: Source)
    }

}