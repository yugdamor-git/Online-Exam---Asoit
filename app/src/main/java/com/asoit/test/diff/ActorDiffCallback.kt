package com.asoit.test.diff

import androidx.recyclerview.widget.DiffUtil
import com.asoit.test.firestoremodel.TestInfoClass


class ActorDiffCallback(
    private val oldList: List<TestInfoClass>,
    private val newList: List<TestInfoClass>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].published == newList[newItemPosition].published &&
                oldList[oldItemPosition].branch == newList[newItemPosition].branch &&
                oldList[oldItemPosition].created_date == newList[newItemPosition].created_date &&
                oldList[oldItemPosition].duration == newList[newItemPosition].duration &&
                oldList[oldItemPosition].id == newList[newItemPosition].id &&
                oldList[oldItemPosition].semester == newList[newItemPosition].semester &&
                oldList[oldItemPosition].subject == newList[newItemPosition].subject &&
                oldList[oldItemPosition].test_date == newList[newItemPosition].test_date &&
                oldList[oldItemPosition].total_questions == newList[newItemPosition].total_questions

    }

}