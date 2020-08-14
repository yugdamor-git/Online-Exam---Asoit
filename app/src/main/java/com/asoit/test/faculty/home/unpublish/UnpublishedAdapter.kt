package com.asoit.test.faculty.home.unpublish


import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import com.asoit.test.firestoremodel.TestQuestionModel
import com.asoit.test.user.StudentInfoModel
import com.google.firebase.firestore.FirebaseFirestore
import com.tapadoo.alerter.Alerter
import java.text.SimpleDateFormat
import java.util.*

class UnpublishedAdapter(
    val firestore: FirebaseFirestore,
    val unpublishedFragment: UnpublishedFragment,
    val progress: ConstraintLayout
) :
    ListAdapter<ResultInfoClass, UnpublishedAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.unpublished_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UnpublishedAdapter.ItemViewholder, position: Int) {
        //holder.date.text = item.branch
       // holder.title.text = item.subject
       // holder.time.text =item.duration.toString()
        //holder.total.text = item.total_questions.toString()
        val format = SimpleDateFormat("dd-MM-yyyy  hh:mm aa", Locale.getDefault())
        val item = getItem(position)
        holder.starton.text = "START : "+ format.format(item.test_date!!.toDate())
        holder.duration.text = item.duration.toString()
        holder.total.text = item.total_questions.toString()
        holder.created_on.text = DateUtils.getRelativeTimeSpanString(item.created_date!!.toDate().time)
        holder.validtill.text = "END : "+format.format(item.valid_till!!.toDate())
        holder.semAndBranch.text = item.branch +"-"+item.semester
        holder.titleTest.text = getItem(position).test_title + " : "+ getItem(position).subject
       holder.publishBtn.setOnClickListener {
            progress.visibility = View.VISIBLE
           firestore.collection("all_test")
               .document(item.document_id!!)
               .collection("questions")
               .get()
               .addOnSuccessListener {

                   val questions = it.toObjects(TestQuestionModel::class.java)

                   firestore.collection("users")
                       .whereEqualTo("semester",item.semester)
                       .whereEqualTo("branch_code",item.branch_code)
                       .whereEqualTo("role","student")
                       .get()
                       .addOnSuccessListener {
                           val users = it.toObjects(StudentInfoModel::class.java)
                        firestore.collection("all_test")
                            .document(item.document_id!!)
                            .update("published",true)
                            .addOnSuccessListener {  }
                           users.forEach {user->
                               val reference = firestore.collection("test").document()
                               item.user_id = user.user_id
                               item.mobile_no = user.mobile_no
                               item.user_enrollment = user.enrollment_no
                               item.user_name = user.name
                               item.published = true
                               val id = reference.id
                               item.document_id = id

                               reference.set(item).addOnSuccessListener {
                                           questions.forEach {
                                               val questionReference = firestore.collection("test")
                                                   .document(reference.id)
                                                   .collection("questions")
                                                   .document(it.id!!)
                                                   .set(it)
                                                   .addOnSuccessListener {

                                                   }
                                               // Toast.makeText(this.requireContext(),"Please Select Semester",Toast.LENGTH_SHORT).show()

                                           }






                               }
                                   .addOnFailureListener {
                                       Log.d("index",it.message)
                                   }

                           }
                       }
                       .addOnFailureListener {
                           Log.d("index",it.message)
                       }
                   Alerter.create(unpublishedFragment.requireActivity())
                       .setTitle("Successfully published")
                       .setIcon(R.drawable.ic_happy_smiley)
                       .setBackgroundColor(R.color.success_color)
                       .setText("Added in all test list")
                       .show()
                   progress.visibility = View.GONE
               }







        }
    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var total = itemView.findViewById<TextView>(R.id.unpublished_total_tv)
        var duration = itemView.findViewById<TextView>(R.id.unpublished_time_tv)
        var starton = itemView.findViewById<TextView>(R.id.unpublished_starts_on)
        var validtill = itemView.findViewById<TextView>(R.id.unpublished_valid_till)
        var semAndBranch = itemView.findViewById<TextView>(R.id.unpublished_branch_sem)
        var titleTest = itemView.findViewById<TextView>(R.id.unpublished_title_tv)
        var publishBtn = itemView.findViewById<Button>(R.id.unpublished_publish_btn)
        var created_on = itemView.findViewById<TextView>(R.id.unpublished_created_on)
}

class DiffCallback : DiffUtil.ItemCallback<ResultInfoClass>() {


    override fun areItemsTheSame(oldItem: ResultInfoClass, newItem: ResultInfoClass): Boolean {
        return oldItem?.test_id == newItem?.test_id
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
                oldItem.valid_till ==  newItem.valid_till


    /*
     r.total_questions = item.total_questions
                                r.created_date = item.created_date
                                r.test_date = item.test_date
                                r.valid_till = item.valid_till
                                r.published = true
                                r.semester = item.semester
                                r.branch = item.branch
                                r.subject = item.subject
                                r.duration = item.duration
                                r.total_questions = item.total_questions
                                r.document_id = ref.id

     */
    }
}}