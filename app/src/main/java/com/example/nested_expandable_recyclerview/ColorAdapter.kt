package com.example.nested_expandable_recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
Created by Masum Mehedi on 8/31/2024.
masumehedissl@gmail.com
 **/
class ColorAdapter(val colors: List<String>) :
    RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    //var selectedColor: String = ""


    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorSwatch: View = itemView.findViewById(R.id.colorSwatch)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.color_swatch,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {

        return colors.size
    }

    override
    fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colors[position]
        holder.colorSwatch.setBackgroundColor(android.graphics.Color.parseColor(color))

        holder.itemView.setOnClickListener {
         //   selectedColor = color
            notifyDataSetChanged()
        }

      //  holder.itemView.isSelected = selectedColor == color
    }
}


