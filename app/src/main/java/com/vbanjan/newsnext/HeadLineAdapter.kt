package com.vbanjan.newsnext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.headline_layout.view.*
import java.security.AccessControlContext

class HeadLineAdapter(val headlines: ArrayList<HeadLine>,val context: Context):RecyclerView.Adapter<HeadLineAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title?.text=headlines.get(position).title
        holder.description?.text=headlines.get(position).description
        holder.publishedAt?.text=headlines.get(position).publishedAt.split("T")[0]+" "+headlines.get(position).publishedAt.split("T")[1].split("Z")[0]
        Picasso.get().load(headlines.get(position).urlTI).into(holder.image)
        holder.itemView.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(headlines.get(position).urlHL))
            startActivity(context,i,null);
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadLineAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return headlines.size
    }
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val title=view.titleTV;
        val publishedAt=view.publishAtTV;
        val description=view.descriptionTV;
        val image=view.imageView;

    }
}