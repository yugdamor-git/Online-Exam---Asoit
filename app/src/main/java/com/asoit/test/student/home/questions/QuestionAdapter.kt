package com.asoit.test.student.home.questions


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultQuestionClass
import com.asoit.test.firestoremodel.TestQuestionModel
import com.asoit.test.student.STUDENT
import com.google.firebase.firestore.FirebaseFirestore
import com.lsjwzh.widget.recyclerviewpager.TabLayoutSupport
import com.pixplicity.easyprefs.library.Prefs


class QuestionAdapter(
    val db: FirebaseFirestore,
    val testid: String,
    val docid: String?
) : ListAdapter<TestQuestionModel, QuestionAdapter.ItemViewholder>(DiffCallback()),
    TabLayoutSupport.ViewPagerTabLayoutAdapter {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.question_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuestionAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position))

        if (getItem(position).selected_ans =="a")
        {

            holder.optionA.setTextColor(Color.WHITE)
            holder.optionB.setTextColor(Color.GRAY)
            holder.optionC.setTextColor(Color.GRAY)
            holder.optionD.setTextColor(Color.GRAY)

            holder.rootA.background.setTint(Color.parseColor("#21bf73"))
            holder.rootB.background.setTint(Color.parseColor("#F6F6F6"))
            holder.rootC.background.setTint(Color.parseColor("#F6F6F6"))
            holder.rootD.background.setTint(Color.parseColor("#F6F6F6"))

            holder.A.setTextColor(Color.WHITE)
            holder.B.setTextColor(Color.GRAY)
            holder.C.setTextColor(Color.GRAY)
            holder.D.setTextColor(Color.GRAY)
        }
        if (getItem(position).selected_ans =="b")
        {
            holder.rootA.background.setTint(Color.parseColor("#F6F6F6"))
            holder.rootB.background.setTint(Color.parseColor("#21bf73"))
            holder.rootC.background.setTint(Color.parseColor("#F6F6F6"))
            holder.rootD.background.setTint(Color.parseColor("#F6F6F6"))

            holder.optionA.setTextColor(Color.GRAY)
            holder.optionB.setTextColor(Color.WHITE)
            holder.optionC.setTextColor(Color.GRAY)
            holder.optionD.setTextColor(Color.GRAY)

            holder.A.setTextColor(Color.GRAY)
            holder.B.setTextColor(Color.WHITE)
            holder.C.setTextColor(Color.GRAY)
            holder.D.setTextColor(Color.GRAY)
        }

        if (getItem(position).selected_ans == "c")
        {
            holder.rootA.background.setTint(Color.parseColor("#F6F6F6"))
            holder.rootB.background.setTint(Color.parseColor("#F6F6F6"))
            holder.rootC.background.setTint(Color.parseColor("#21bf73"))
            holder.rootD.background.setTint(Color.parseColor("#F6F6F6"))

            holder.optionA.setTextColor(Color.GRAY)
            holder.optionB.setTextColor(Color.GRAY)
            holder.optionC.setTextColor(Color.WHITE)
            holder.optionD.setTextColor(Color.GRAY)

            holder.A.setTextColor(Color.GRAY)
            holder.B.setTextColor(Color.GRAY)
            holder.C.setTextColor(Color.WHITE)
            holder.D.setTextColor(Color.GRAY)
        }

        if (getItem(position).selected_ans =="d")
        {
            holder.rootA.background.setTint(Color.parseColor("#F6F6F6"))
            holder.rootB.background.setTint(Color.parseColor("#F6F6F6"))
            holder.rootC.background.setTint(Color.parseColor("#F6F6F6"))
            holder.rootD.background.setTint(Color.parseColor("#21bf73"))

            holder.optionA.setTextColor(Color.GRAY)
            holder.optionB.setTextColor(Color.GRAY)
            holder.optionC.setTextColor(Color.GRAY)
            holder.optionD.setTextColor(Color.WHITE)

            holder.A.setTextColor(Color.GRAY)
            holder.B.setTextColor(Color.GRAY)
            holder.C.setTextColor(Color.GRAY)
            holder.D.setTextColor(Color.WHITE)
        }


        holder.rootA.setOnClickListener {opa->

            db.collection("test")
                .document(docid.toString())
                .collection("questions")
                .document(getItem(position).id!!)
                .update("selected_ans","a")
                .addOnSuccessListener {


                }
        }
        holder.rootB.setOnClickListener {opb->

            db.collection("test")
                .document(docid.toString())
                .collection("questions")
                .document(getItem(position).id!!)
                .update("selected_ans","b")
                .addOnSuccessListener {

                }
        }
        holder.rootC.setOnClickListener {opc->

            db.collection("test")
                .document(docid.toString())
                .collection("questions")
                .document(getItem(position).id!!)
                .update("selected_ans","c")
                .addOnSuccessListener {
                    //opc.setBackgroundColor(Color.GREEN)

                }
        }
        holder.rootD.setOnClickListener {opd->

            db.collection("test")
                .document(docid.toString())
                .collection("questions")
                .document(getItem(position).id!!)
                .update("selected_ans","d")
                .addOnSuccessListener {

                }
        }
    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TestQuestionModel?) {
            question.text = item?.question
            optionA.text = item?.a
            optionB.text = item?.b
            optionC.text = item?.c
            optionD.text = item?.d


        }

        var question = itemView.findViewById<TextView>(R.id.question)

        var optionA = itemView.findViewById<TextView>(R.id.option_a)
        var optionB = itemView.findViewById<TextView>(R.id.option_b)
        var optionC = itemView.findViewById<TextView>(R.id.option_c)
        var optionD = itemView.findViewById<TextView>(R.id.option_d)
        var rootA = itemView.findViewById<LinearLayout>(R.id.root_a)
        var rootB = itemView.findViewById<LinearLayout>(R.id.root_b)
        var rootC = itemView.findViewById<LinearLayout>(R.id.root_c)
        var rootD = itemView.findViewById<LinearLayout>(R.id.root_d)
        var A = itemView.findViewById<TextView>(R.id.tv_a)
        var B = itemView.findViewById<TextView>(R.id.tv_b)
        var C = itemView.findViewById<TextView>(R.id.tv_c)
        var D = itemView.findViewById<TextView>(R.id.tv_d)
        }

    override fun getPageTitle(p0: Int): String {
        return (p0+1).toString()
    }


}

class DiffCallback : DiffUtil.ItemCallback<TestQuestionModel>() {


    override fun areItemsTheSame(oldItem: TestQuestionModel, newItem: TestQuestionModel): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TestQuestionModel, newItem: TestQuestionModel): Boolean {
        return  oldItem.id == newItem.id &&
                oldItem.a == newItem.a &&
                oldItem.b == newItem.b &&
                oldItem.c == newItem.c &&
                oldItem.d  == newItem.d &&
                oldItem.correct_ans == newItem.correct_ans &&
                oldItem.question == newItem.question &&
                oldItem.selected_ans == newItem.selected_ans
    }
}