package ru.mirea.zotovml.mireaproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.zotovml.mireaproject.R

class CustomResyclerAdapter(private val strs:List<String>)
    : RecyclerView.Adapter<CustomResyclerAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv:TextView = itemView.findViewById(R.id.textView)
    }

    override fun getItemCount(): Int = strs.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv.text = strs[position]
    }
}