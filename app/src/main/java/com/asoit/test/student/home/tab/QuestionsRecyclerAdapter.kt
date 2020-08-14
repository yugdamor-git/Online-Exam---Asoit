package com.asoit.test.student.home.tab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asoit.test.R
import com.asoit.test.student.models.QuestionModel
import com.asoit.test.student.models.TestModel


class QuestionsRecyclerAdapter(val context:Context, val listOfTest:ArrayList<QuestionModel>):RecyclerView.Adapter<QuestionsRecyclerAdapter.viewHolder>(){

    inner class viewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        var question = view.findViewById<TextView>(R.id.question)

        var optionA = view.findViewById<Button>(R.id.option_a)

        var optionB = view.findViewById<Button>(R.id.option_b)
        var optionC = view.findViewById<Button>(R.id.option_c)
        var optionD = view.findViewById<Button>(R.id.option_d)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.question_recycler_item,parent,false)

        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfTest.size
    }




    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        holder.optionA.text = listOfTest[position].A
        holder.question.text =listOfTest[position].question


    }



}