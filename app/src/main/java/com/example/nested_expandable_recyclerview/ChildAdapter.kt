package com.example.nested_expandable_recyclerview

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nested_expandable_recyclerview.databinding.ChildItemBinding

/**
Created by Masum Mehedi on 8/21/2024.
masumehedissl@gmail.com
 **/
class ChildAdapter(
    private val childClickListener: OnChildItemClickListener,
    private val subChildClickListener: OnSubChildItemClickListener
) :
    ListAdapter<ChildItem, ChildAdapter.ChildViewHolder>(
        GenericDiffCallback(
            areItemsTheSame = { oldItem, newItem -> oldItem.name == newItem.name },
            areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
        )
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding = ChildItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ChildViewHolder(private val binding: ChildItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(childItem: ChildItem) {
            binding.tvChildTitle.text = childItem.name

            // Toggle expansion when the arrow is clicked
            binding.ivArrow.setOnClickListener {
                // Toggle isExpanded value
                childItem.isExpanded = !childItem.isExpanded

                // Notify the adapter to refresh the UI for this item
                notifyItemChanged(adapterPosition)

                // Notify the click listener (if needed)
                childClickListener.onChildItemClick(childItem)
            }
            binding.tvChildTitle.setOnClickListener {
                if (childItem.isExpanded) {
                    childItem.isExpanded = true

                }
                notifyItemChanged(adapterPosition)
                childClickListener.onChildItemClick(childItem)
            }
            // Set up sub-child RecyclerView
            binding.subChildRecyclerView.apply {
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = SubChildAdapter(subChildClickListener).apply {
                    submitList(childItem.subChildItems)
                }
                visibility = if (childItem.isExpanded) View.VISIBLE else View.GONE
            }


        }
    }
}
