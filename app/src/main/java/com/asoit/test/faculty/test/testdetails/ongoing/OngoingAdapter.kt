package com.asoit.test.faculty.test.testdetails.ongoing

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import com.asoit.test.firestoremodel.TestQuestionModel
import com.daimajia.numberprogressbar.NumberProgressBar
import com.google.firebase.firestore.FirebaseFirestore
import com.kiwimob.firestore.livedata.livedata

class OngoingAdapter(
    val firestore: FirebaseFirestore,
    val requireContext: OngoingTestFragment
) : ListAdapter<ResultInfoClass, OngoingAdapter.ItemViewholder>(DiffCallback()) {

    var old =0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.student_details_recycler_item, parent, false)
        )
    }
        fun startAnim(
            progressbar: NumberProgressBar,
            new: Int,
            old:Int
        )
        {
            val ani = ObjectAnimator.ofInt(progressbar,"progress",old*100,new * 100)
            ani.setDuration(1500)
            ani.interpolator = LinearInterpolator()
            ani.start()

        }


    override fun onBindViewHolder(holder: OngoingAdapter.ItemViewholder, position: Int) {
      val item = getItem(position)
        var count =0
        firestore.collection("test")
            .document(item.document_id!!)
            .collection("questions")
            .livedata(TestQuestionModel::class.java)
            .observe(requireContext, Observer {
              count=0
                it.forEach {
                  if (it.selected_ans != "0")
                  {
                      count++
                  }
              }
                if (count != old)
                {
                    startAnim(holder.progressbar,count,old)
                    old = count
                }
            })
        holder.character.text = item.user_name!![0].toString()
        holder.enrollment.text = item.user_enrollment
        holder.username.text = item.user_name
        holder.progressbar.max = item.total_questions!! * 100


    }


    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val username = itemView.findViewById<TextView>(R.id.test_details_name)
        val enrollment = itemView.findViewById<TextView>(R.id.test_details_enrollment)
        val character = itemView.findViewById<TextView>(R.id.test_details_char)
        val progressbar = itemView.findViewById<NumberProgressBar>(R.id.number_progress_bar)

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
        oldItem.user_name == newItem.user_name &&
                oldItem.CountSelected == newItem.CountSelected

    }
}