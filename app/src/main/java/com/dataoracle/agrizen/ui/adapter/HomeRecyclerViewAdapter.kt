package com.dataoracle.agrizen.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cloudinary.android.MediaManager
import com.dataoracle.agrizen.R
import com.dataoracle.agrizen.ui.datamodel.HomeListItem

class HomeRecyclerViewAdapter(val homeItems: ArrayList<HomeListItem>, val context: Context) :
        RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemId: String? = null
        var thumbNail: ImageView
        var nameItem: TextView

        init {
            thumbNail = itemView.findViewById(R.id.id_item_thumbnail)
            nameItem = itemView.findViewById(R.id.id_item_title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recycler_view_home_item_tile,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return homeItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameItem.text = homeItems[position].nameItem
        Glide.with(context).load(MediaManager.get().url().generate("sample.jpg")).into(holder.thumbNail)
    }
}
