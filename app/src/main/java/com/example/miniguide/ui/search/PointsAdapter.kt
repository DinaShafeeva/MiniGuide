package com.example.miniguide.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.miniguide.R
import com.example.miniguide.data.PointModel

class PointsAdapter (
    private val clickLambda: (PointModel) -> Unit
) : RecyclerView.Adapter<PointsAdapter.LocationViewHolder>() {

    private val data = mutableListOf<PointModel>()

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

    fun updateData(newData: List<PointModel>?) {
        val diffCallback = DiffUtilCallback(data, newData ?: emptyList())
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData ?: emptyList())
        diffResult.dispatchUpdatesTo(this)
    }

    private inner class DiffUtilCallback(

        private val oldList: List<PointModel>,
        private val newList: List<PointModel>

    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].location == newList[newItemPosition].location

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem.name == newItem.name &&
                    oldItem.location == newItem.location
        }
    }

    inner class LocationViewHolder(
        itemView: View,
        private val clickLambda: (PointModel) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {

        private val point: TextView = itemView.findViewById(R.id.tvPoint)

        fun bind(pointData: PointModel) {
            point.text =
                itemView.context.getString(R.string.point_text, pointData.name, pointData.location)
            itemView.setOnClickListener { clickLambda.invoke(pointData) }
        }
    }
}