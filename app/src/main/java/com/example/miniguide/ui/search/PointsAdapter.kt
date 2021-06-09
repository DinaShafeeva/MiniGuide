package com.example.miniguide.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.miniguide.R
import com.mapbox.search.result.SearchSuggestion

class PointsAdapter(
    private val clickLambda: (SearchSuggestion) -> Unit
) : RecyclerView.Adapter<PointsAdapter.LocationViewHolder>() {

    private val data = mutableListOf<SearchSuggestion>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LocationViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_point_list,
                parent,
                false
            ),
        clickLambda
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) =
        holder.bind(data[position])

    override fun onViewDetachedFromWindow(holder: LocationViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }

    fun updateData(newData: List<SearchSuggestion>?) {
        val diffCallback = DiffUtilCallback(data, newData ?: emptyList())
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData ?: emptyList())
        diffResult.dispatchUpdatesTo(this)
    }

    private inner class DiffUtilCallback(

        private val oldList: List<SearchSuggestion>,
        private val newList: List<SearchSuggestion>

    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem.name == newItem.name &&
                    oldItem.id == newItem.id
        }
    }

    inner class LocationViewHolder(
        itemView: View,
        private val clickLambda: (SearchSuggestion) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {

        private val point: TextView = itemView.findViewById(R.id.tvPoint)

        fun bind(pointData: SearchSuggestion) {
            point.text =
                itemView.context.getString(
                    R.string.point_text,
                    pointData.name,
                    pointData.address?.locality.toString()
                )
            itemView.setOnClickListener { clickLambda.invoke(pointData) }
        }
    }
}