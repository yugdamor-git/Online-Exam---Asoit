package com.asoit.test.student.home.questions


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asoit.test.R
import com.asoit.test.firestoremodel.TestQuestionModel

class IndicatorAdapter :
    ListAdapter<TestQuestionModel, IndicatorAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.question_indicator_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IndicatorAdapter.ItemViewholder, position: Int) {
       holder.indicator.text = position.toString()
    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var indicator = itemView.findViewById<TextView>(R.id.tv_indicator_char)


    }
}

class DiffCallbackIndicator : DiffUtil.ItemCallback<TestQuestionModel>() {
    override fun areItemsTheSame(oldItem: TestQuestionModel, newItem: TestQuestionModel): Boolean {
        return false
    }

    override fun areContentsTheSame(
        oldItem: TestQuestionModel,
        newItem: TestQuestionModel
    ): Boolean {
       return false
    }

}
