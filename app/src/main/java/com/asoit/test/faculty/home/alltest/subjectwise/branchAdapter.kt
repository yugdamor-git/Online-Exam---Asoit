package com.asoit.test.faculty.home.alltest.subjectwise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asoit.test.R
import com.asoit.test.faculty.home.FacultyHomeFragmentDirections
import com.asoit.test.firestoremodel.BranchInfoModel

class branchAdapter() : ListAdapter<BranchInfoModel, branchAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.subjectwise_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: branchAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.subjectwise_name)
        val code = itemView.findViewById<TextView>(R.id.subjectwise_code)
        val root = itemView.findViewById<ConstraintLayout>(R.id.subjectwise_root)
        fun bind(item: BranchInfoModel) = with(itemView) {
            name.text = item.branch_name
            code.text = item.code.toString()

            root.setOnClickListener {
                val action = FacultyHomeFragmentDirections.actionFacultyHomeFragmentToSelectSemFragment()
                action.setBranchCode(item.code!!)
                findNavController().navigate(action)
            }
        }

    }
}

class DiffCallback : DiffUtil.ItemCallback<BranchInfoModel>() {
    override fun areItemsTheSame(oldItem: BranchInfoModel, newItem: BranchInfoModel): Boolean {

        return  oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: BranchInfoModel, newItem: BranchInfoModel): Boolean {
        return oldItem.code == newItem.code &&
                oldItem.branch_name == newItem.branch_name
    }

}