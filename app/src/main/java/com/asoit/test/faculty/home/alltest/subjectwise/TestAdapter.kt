package com.asoit.test.faculty.home.alltest.subjectwise


import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import java.text.SimpleDateFormat
import java.util.*

class TestAdapter(val findNavController: NavController) : ListAdapter<ResultInfoClass, TestAdapter.ItemViewholder>(TestAdapterDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.alltest_recyclerview_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TestAdapter.ItemViewholder, position: Int) {
        val format = SimpleDateFormat("dd-MM-yyyy  hh:mm aa", Locale.getDefault())

        holder.starton.text = "START : "+ format.format(getItem(position).test_date!!.toDate())
        holder.duration.text = getItem(position).duration.toString()
        holder.total.text = getItem(position).total_questions.toString()
        holder.created_on.text = DateUtils.getRelativeTimeSpanString(getItem(position).created_date!!.toDate().time)
        holder.validtill.text = "END : "+format.format(getItem(position).valid_till!!.toDate())
        holder.semAndBranch.text = getItem(position).branch +"-"+getItem(position).semester
        holder.titleTest.text ="SE UNIT 7 MOCK TEST"
        holder.moreInfoBtn.setOnClickListener {
            val action = SelectTestFragmentDirections.actionSelectTestFragmentToTestDetailsFragment()
            action.setTestId(getItem(position).test_id!!)

            findNavController.navigate(action)

        }

    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var total = itemView.findViewById<TextView>(R.id.alltest_total_tv)
        var duration = itemView.findViewById<TextView>(R.id.alltest_time_tv)
        var starton = itemView.findViewById<TextView>(R.id.alltest_starts_on)
        var validtill = itemView.findViewById<TextView>(R.id.alltest_valid_till)
        var semAndBranch = itemView.findViewById<TextView>(R.id.alltest_branch_sem)
        var titleTest = itemView.findViewById<TextView>(R.id.alltest_title_tv)
        var moreInfoBtn = itemView.findViewById<Button>(R.id.alltest_more_info)
        var created_on = itemView.findViewById<TextView>(R.id.alltest_created_on)
    }
    }


class TestAdapterDiff : DiffUtil.ItemCallback<ResultInfoClass>() {
    override fun areItemsTheSame(oldItem: ResultInfoClass, newItem: ResultInfoClass): Boolean {
        return oldItem?.document_id == newItem?.document_id
    }

    override fun areContentsTheSame(oldItem: ResultInfoClass, newItem: ResultInfoClass): Boolean {
        return oldItem.published ==  newItem.published &&
                oldItem.total_questions ==  newItem.total_questions &&
                oldItem.test_date ==  newItem.test_date &&
                oldItem.subject ==  newItem.subject &&
                oldItem.semester ==  newItem.semester &&
                oldItem.document_id ==  newItem.document_id &&
                oldItem.duration ==  newItem.duration &&
                oldItem.created_date ==  newItem.created_date &&
                oldItem.branch ==  newItem.branch &&
                oldItem.valid_till ==  newItem.valid_till
    }

}