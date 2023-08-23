package com.jpmc.nycschools.ui.schoolslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jpmc.nycschools.data.repository.NycSchool
import com.jpmc.nycschools.databinding.ItemSchoolBinding

class NycSchoolsListAdapter(val clickListener: ClickListener) :
    RecyclerView.Adapter<NycSchoolsListAdapter.NycSchoolViewHolder>() {

    var schools: List<NycSchool> = emptyList()
        set(value) {
            val diffUtil = SchoolsDiffUtil(schools, value)
            val diffResult = DiffUtil.calculateDiff(diffUtil)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    inner class NycSchoolViewHolder(private val binding: ItemSchoolBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(school: NycSchool) {
            binding.school = school
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NycSchoolViewHolder {
        return NycSchoolViewHolder(
            ItemSchoolBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return schools.size
    }

    override fun onBindViewHolder(holder: NycSchoolViewHolder, position: Int) {
        val school = schools.getOrNull(position)
        school?.let {
            holder.bind(it)
        }
    }
}

// This is to calculate the diffUtil.
// It helps in improving the performance of recyclerview by not updating all the items
class SchoolsDiffUtil(
    private val oldList: List<NycSchool>,
    private val newList: List<NycSchool>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.getOrNull(oldItemPosition)?.schoolId == newList.getOrNull(newItemPosition)?.schoolId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.getOrNull(oldItemPosition) == newList.getOrNull(newItemPosition)
    }

}

class ClickListener(val clickListener: (String) -> Unit) {
    fun onClick(nycSchool: NycSchool) = clickListener(nycSchool.schoolId)
}