package com.vbanjan.newsnext

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recents_list_item.view.sourceTextView
import kotlinx.android.synthetic.main.source_list_item.view.*

class RecentsRecyclerViewAdapter(
    val recents: ArrayList<Source>,
    val context: MainActivity
) :
    RecyclerView.Adapter<RecentsRecyclerViewAdapter.ViewHolder>() {

    private val mListener: MainActivity = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recents_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return recents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val source: Source = recents[position]
        holder.sourceTitle.text = source.name
        holder.sourceTitle.setOnClickListener {
            mListener.onSourceClick(recents[position])
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sourceTitle: TextView = itemView.sourceTextView

    }

    interface OnFragmentAdapterInteractionListener {
        fun onSourceClick(source: Source)
    }
}