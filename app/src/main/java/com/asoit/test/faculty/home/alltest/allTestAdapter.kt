package com.asoit.test.faculty.home.alltest



import android.content.Context
import android.graphics.Color
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asoit.test.R
import com.asoit.test.faculty.home.FacultyHomeFragmentDirections
import com.asoit.test.firestoremodel.ResultInfoClass
import com.asoit.test.firestoremodel.allTestModel
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class allTestAdapter(
    val context: Context,
    val findNavController: NavController,
    val db: FirebaseFirestore
) : ListAdapter<allTestModel, allTestAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.alltest_recyclerview_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: allTestAdapter.ItemViewholder, position: Int) {
       val format = SimpleDateFormat("dd-MM-yyyy  hh:mm aa",Locale.getDefault())

        holder.starton.text = "START : "+ format.format(getItem(position).test_date!!.toDate())
        holder.duration.text = getItem(position).duration.toString()
        holder.total.text = getItem(position).total_questions.toString()
        holder.created_on.text = DateUtils.getRelativeTimeSpanString(getItem(position).created_date!!.toDate().time)
        holder.validtill.text = "END : "+format.format(getItem(position).valid_till!!.toDate())
        holder.semAndBranch.text = getItem(position).branch +"-"+getItem(position).semester
        holder.titleTest.text = getItem(position).test_title + " : "+ getItem(position).subject
        holder.root.animation = AnimationUtils.loadAnimation(context,R.anim.fade_animation_left_to_right)
        holder.root.animation.startOffset = 500
       getItem(position)?.test_active.let {
           if (it == true)
           {
               holder.start_stop.text = "stop"
               holder.start_stop.background.setTint(Color.parseColor("#c72c41"))
           }
           if (it == false)
           {
               holder.start_stop.text = "start"
               holder.start_stop.background.setTint(Color.parseColor("#21bf73"))
           }
       }
        holder.start_stop.setOnClickListener {
            if (getItem(position).test_active == true)
            {
                //make is active false
                db.collection("all_test")
                    .document(getItem(position).test_id!!)
                    .update("test_active",false)
                    .addOnSuccessListener {
                        db.collection("test")
                            .whereEqualTo("test_id",getItem(position).test_id)
                            .get()
                            .addOnSuccessListener {
                                val users = it.toObjects(ResultInfoClass::class.java)
                                users.forEach {
                                    db.collection("test")
                                        .document(it.document_id!!)
                                        .update("test_active",false)
                                }
                            }
                    }
                    .addOnFailureListener {  }
            }
            if (getItem(position).test_active == false)
            {
                //make is active true
                db.collection("all_test")
                    .document(getItem(position).test_id!!)
                    .update("test_active",true)
                    .addOnSuccessListener {
                        db.collection("test")
                            .whereEqualTo("test_id",getItem(position).test_id)
                            .get()
                            .addOnSuccessListener {
                                val users = it.toObjects(ResultInfoClass::class.java)
                                users.forEach {
                                    db.collection("test")
                                        .document(it.document_id!!)
                                        .update("test_active",true)
                                }
                            }
                    }
                    .addOnFailureListener {  }
            }
        }

        holder.moreInfoBtn.setOnClickListener {
            val action = FacultyHomeFragmentDirections.actionFacultyHomeFragmentToTestDetailsFragment()
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
        var moreInfoBtn = itemView.findViewById<TextView>(R.id.alltest_more_info)
        var created_on = itemView.findViewById<TextView>(R.id.alltest_created_on)
        var start_stop = itemView.findViewById<Button>(R.id.start_stop_btn)
        var root = itemView.findViewById<ConstraintLayout>(R.id.alltest_root)
    }
    }


class DiffCallback : DiffUtil.ItemCallback<allTestModel>() {


    override fun areItemsTheSame(oldItem: allTestModel, newItem: allTestModel): Boolean {
        return oldItem?.test_id == newItem?.test_id
    }

    override fun areContentsTheSame(oldItem: allTestModel, newItem: allTestModel): Boolean {
        return oldItem.published ==  newItem.published &&
                oldItem.total_questions ==  newItem.total_questions &&
                oldItem.test_date ==  newItem.test_date &&
                oldItem.subject ==  newItem.subject &&
                oldItem.semester ==  newItem.semester &&
                oldItem.test_active ==  newItem.test_active &&
                oldItem.duration ==  newItem.duration &&
                oldItem.created_date ==  newItem.created_date &&
                oldItem.branch ==  newItem.branch &&
                oldItem.valid_till ==  newItem.valid_till
    }
}