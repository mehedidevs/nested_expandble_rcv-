package com.example.nested_expandable_recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nested_expandable_recyclerview.databinding.ParentItemBinding

/**
Created by Masum Mehedi on 8/21/2024.
masumehedissl@gmail.com
 **/
class ParentAdapter(
    private val parentClickListener: OnParentItemClickListener,
    private val childClickListener: OnChildItemClickListener,
    private val subChildClickListener: OnSubChildItemClickListener
) : ListAdapter<ParentItem, ParentAdapter.ParentViewHolder>(
    GenericDiffCallback(
        areItemsTheSame = { oldItem, newItem -> oldItem.title == newItem.title },
        areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = ParentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ParentViewHolder(private val binding: ParentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val childAdapter = ChildAdapter(childClickListener, subChildClickListener)

        init {
            // Initialize RecyclerView once in the ViewHolder constructor
            binding.childRecyclerView.apply {
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = childAdapter
            }
        }

        fun bind(parentItem: ParentItem) {
            binding.tvParentTitle.text = parentItem.title

            // Set the correct arrow state based on isExpanded
            binding.ivArrow.setImageResource(if (parentItem.isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)

            // Update child list and visibility based on isExpanded
            binding.childRecyclerView.visibility =
                if (parentItem.isExpanded) View.VISIBLE else View.GONE
            if (parentItem.isExpanded) {
                childAdapter.submitList(parentItem.childItems)
            }

            // Toggle expansion when the arrow or title is clicked
            binding.root.setOnClickListener {
                parentItem.isExpanded = !parentItem.isExpanded
                notifyItemChanged(adapterPosition)
                parentClickListener.onParentItemClick(parentItem)
            }
        }
    }
}


