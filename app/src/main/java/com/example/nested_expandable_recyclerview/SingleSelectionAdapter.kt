package com.example.nested_expandable_recyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
Created by Masum Mehedi on 8/31/2024.
masumehedissl@gmail.com
 **/
data class ColorItem(val colorHex: String)

class ColorSelectionAdapter(private val colors: List<ColorItem>) :
    RecyclerView.Adapter<ColorSelectionAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION
    private var onColorSelectedListener: ((String) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorView: View = itemView.findViewById(R.id.colorPreview)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    setSelectedPosition(position)
                    onColorSelectedListener?.invoke(colors[position].colorHex)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_single_selection, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val colorItem = colors[position]
        holder.colorView.setBackgroundColor(Color.parseColor(colorItem.colorHex))
        holder.itemView.isSelected = position == selectedPosition
    }

    override fun getItemCount() = colors.size

    fun setSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }

    fun setOnColorSelectedListener(listener: (String) -> Unit) {
        onColorSelectedListener = listener
    }
}