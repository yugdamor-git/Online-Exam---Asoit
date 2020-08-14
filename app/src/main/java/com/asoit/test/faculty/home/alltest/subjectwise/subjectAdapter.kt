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
import com.asoit.test.firestoremodel.SubjectInfoModel

class subjectAdapter(val branchCode: Int?, val semester: Int?) :
    ListAdapter<SubjectInfoModel, subjectAdapter.ItemViewholder>(DiffCallbackSubject()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.subjectwise_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: subjectAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position),branchCode,semester)



    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById<TextView>(R.id.subjectwise_name)
        val code = itemView.findViewById<TextView>(R.id.subjectwise_code)

        val root = itemView.findViewById<ConstraintLayout>(R.id.subjectwise_root)
        fun bind(
            item: SubjectInfoModel,
            branchCode: Int?,
            semester: Int?
        ) = with(itemView) {

            name.text = item.name
            code.text = item.subject_code.toString()
            root.setOnClickListener {
                val action = SelectSubjectFragmentDirections.actionSelectSubjectFragmentToSelectTestFragment()
                action.setSubjectCode(item.subject_code!!.toInt())
                action.setSemester(semester!!)
               action.setBranchCode(branchCode!!)
              findNavController().navigate(action)


            }

        }



    }
}

class DiffCallbackSubject : DiffUtil.ItemCallback<SubjectInfoModel>() {
    override fun areItemsTheSame(oldItem: SubjectInfoModel, newItem: SubjectInfoModel): Boolean {
        return oldItem.subject_code == newItem.subject_code
    }

    override fun areContentsTheSame(oldItem: SubjectInfoModel, newItem: SubjectInfoModel): Boolean {
        return  oldItem.subject_code == newItem.subject_code &&
                oldItem.branch_code == newItem.branch_code &&
                oldItem.name == newItem.name &&
                oldItem.semester == newItem.semester
    }

}