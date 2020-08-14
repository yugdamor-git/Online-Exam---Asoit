package com.asoit.test.faculty.test.testdetails.completed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import com.daimajia.numberprogressbar.NumberProgressBar

class CompletedAdapter : ListAdapter<ResultInfoClass, CompletedAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.completed_alltest_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CompletedAdapter.ItemViewholder, position: Int) {
      val item = getItem(position)
        holder.character.text = item.user_name!![0].toString()
        holder.enrollment.text = item.user_enrollment
        holder.username.text = item.user_name
        holder.progressbar.visibility = View.GONE
        holder.score.text = item.score.toString() +"/"+item.total_questions
    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val username = itemView.findViewById<TextView>(R.id.completed_alltest_name)
        val enrollment = itemView.findViewById<TextView>(R.id.completed_alltest_enrollment)
        val character = itemView.findViewById<TextView>(R.id.completed_alltest_char)
        val progressbar = itemView.findViewById<NumberProgressBar>(R.id.number_progress_bar)
        val score = itemView.findViewById<Button>(R.id.completed_alltest_score)

        }

}

class DiffCallback : DiffUtil.ItemCallback<ResultInfoClass>() {


    override fun areItemsTheSame(oldItem: ResultInfoClass, newItem: ResultInfoClass): Boolean {
         return oldItem.test_id == newItem.test_id &&
                 oldItem.user_id == newItem.user_id
    }

    override fun areContentsTheSame(oldItem: ResultInfoClass, newItem: ResultInfoClass): Boolean {

        return oldItem.started == newItem.started &&
                oldItem.submited == newItem.submited &&
                oldItem.user_id == newItem.user_id &&
                oldItem.test_id == newItem.test_id &&
                oldItem.score == newItem.score &&
                oldItem.time_left == newItem.time_left &&
                oldItem.user_enrollment == newItem.user_enrollment &&
                oldItem.user_name == newItem.user_name
    }
}