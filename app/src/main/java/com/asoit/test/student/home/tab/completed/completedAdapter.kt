package com.asoit.test.student.home.tab.completed


import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import java.text.SimpleDateFormat
import java.util.*

class completedAdapter(val completedFragment: CompletedFragment) :
    ListAdapter<ResultInfoClass, completedAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.student_active_test_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: completedAdapter.ItemViewholder, position: Int) {
        val item = getItem(position)
        val format = SimpleDateFormat("dd-MM-yyyy  hh:mm aa", Locale.getDefault())

        holder.starton.text = "START : "+ format.format(item.test_date!!.toDate())
        holder.duration.text = item.duration.toString()
        holder.total.text = item.total_questions.toString()
        holder.created_on.text = DateUtils.getRelativeTimeSpanString(item.created_date!!.toDate().time)
        holder.validtill.text = "END : "+format.format(item.valid_till!!.toDate())
        holder.semAndBranch.text = item.branch +"-"+item.semester
        holder.titleTest.text = getItem(position).test_title + " : "+ getItem(position).subject
        holder.moreInfoBtn.text = item.score.toString()+"/"+item.total_questions

        holder.root.animation = AnimationUtils.loadAnimation(completedFragment.requireContext(),R.anim.fade_animation_left_to_right)
        holder.root.animation.startOffset = 400





    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var total = itemView.findViewById<TextView>(R.id.student_active_total_tv)
        var duration = itemView.findViewById<TextView>(R.id.student_active_time_tv)
        var starton = itemView.findViewById<TextView>(R.id.student_active_starts_on)
        var validtill = itemView.findViewById<TextView>(R.id.student_active_valid_till)
        var semAndBranch = itemView.findViewById<TextView>(R.id.student_active_branch_sem)
        var titleTest = itemView.findViewById<TextView>(R.id.student_active_title_tv)
        var moreInfoBtn = itemView.findViewById<Button>(R.id.student_active_more_info)
        var created_on = itemView.findViewById<TextView>(R.id.student_active_created_on)
        var root = itemView.findViewById<ConstraintLayout>(R.id.active_root)
    }
}

class DiffCallback : DiffUtil.ItemCallback<ResultInfoClass>() {
    override fun areItemsTheSame(oldItem: ResultInfoClass, newItem: ResultInfoClass): Boolean {
        return oldItem?.document_id == newItem?.document_id
    }

    override fun areContentsTheSame(oldItem: ResultInfoClass, newItem: ResultInfoClass): Boolean {
        return oldItem.published ==  newItem.published &&
                oldItem.total_questions ==  newItem.total_questions &&
                oldItem.test_date ==  newItem.test_date &&
                oldItem.subject ==  newItem.subject &&
                oldItem.semester ==  newItem.semester &&
                oldItem.test_id ==  newItem.test_id &&
                oldItem.duration ==  newItem.duration &&
                oldItem.created_date ==  newItem.created_date &&
                oldItem.branch ==  newItem.branch &&
                oldItem.valid_till ==  newItem.valid_till &&

                oldItem.user_id == newItem.user_id &&
                oldItem.score ==  newItem.score &&
                oldItem.started == newItem.started &&
                oldItem.submited == newItem.submited &&
                oldItem.time_left == newItem.time_left &&
                oldItem.user_name == newItem.user_name &&
                oldItem.user_enrollment == newItem.user_enrollment




    }

}