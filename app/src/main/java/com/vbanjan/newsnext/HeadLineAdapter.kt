package com.vbanjan.newsnext

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.headline_layout.view.*
import org.ocpsoft.prettytime.PrettyTime
import java.security.AccessControlContext
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class HeadLineAdapter(val headlines: ArrayList<HeadLine>, val context: Context) :
    RecyclerView.Adapter<HeadLineAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!headlines.get(position).title.isEmpty()) {
            holder.title?.text = headlines.get(position).title
        } else {
            holder.title?.text = "Click Here for Full Article"
        }
        if (!headlines.get(position).description.isEmpty()) {
            holder.description?.text = headlines.get(position).description
        } else if (!headlines.get(position).content.isEmpty()) {
            holder.description?.text = headlines.get(position).content.substring(0, 200) + "..."
        } else {
            holder.description?.text = "Click Here for Full Article"
        }
        if (!headlines.get(position).publishedAt.isEmpty()) {
            var date =
                headlines.get(position).publishedAt.split("T")[0] + " " + headlines.get(position).publishedAt.split(
                    "T"
                )[1].split("Z")[0]
            holder.publishedAt?.text = PrettyTime().format(
                SimpleDateFormat("YYYY-MM-DD HH:MM:SS", Locale.ENGLISH).parse(date)
            )
        } else {
            holder.publishedAt?.text = "Date NA"
        }
        Log.d("demo", headlines.get(position).urlTI)
        if (headlines.get(position).urlTI.isEmpty()) {
            holder.image.setImageResource(R.drawable.no_image)
        } else {
            Picasso.get().load(headlines.get(position).urlTI).into(holder.image)
        }
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (!headlines.get(position).urlHL.isEmpty()) {
                val builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(context, Uri.parse(headlines.get(position).urlHL))
            } else {
                Toast.makeText(context, "Article is Unavailable", Toast.LENGTH_SHORT).show();
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadLineAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.headline_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return headlines.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.titleTV;
        val publishedAt = view.publishAtTV;
        val description = view.descriptionTV;
        val image = view.imageView;

    }
}