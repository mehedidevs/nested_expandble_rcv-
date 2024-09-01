package com.example.nested_expandable_recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nested_expandable_recyclerview.databinding.SubChildItemBinding

/**
Created by Masum Mehedi on 8/21/2024.
masumehedissl@gmail.com
 **/
class SubChildAdapter(
    private val subChildClickListener: OnSubChildItemClickListener
) :
    ListAdapter<SubChildItem, SubChildAdapter.SubChildViewHolder>(
        GenericDiffCallback(
            areItemsTheSame = { oldItem, newItem -> oldItem.name == newItem.name },
            areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
        )
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubChildViewHolder {
        val binding = SubChildItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubChildViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SubChildViewHolder(private val binding: SubChildItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subChildItem: SubChildItem) {
            binding.tvSubChildTitle.text = subChildItem.name
            binding.root.setOnClickListener {
                subChildClickListener.onSubChildItemClick(subChildItem)
            }
        }
    }
}
