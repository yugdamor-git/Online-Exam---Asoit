package com.asoit.test.faculty.test.testdetails.ongoing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asoit.test.R
import com.asoit.test.student.models.QuestionModel
import com.asoit.test.student.models.TestModel

class OngoingTestRecyclerAdapter(val context:Context, val listOfTest:ArrayList<String>):RecyclerView.Adapter<OngoingTestRecyclerAdapter.viewHolder>() {

    inner class viewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        //var question = view.findViewById<TextView>(R.id.question)
        ///var countIndicator = view.findViewById<TextView>(R.id.number_indicator)
       // var optionA = view.findViewById<TextView>(R.id.option_a)
      //  var name = view.findViewById<TextView>(R.id.student_details_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.student_details_recycler_item,parent,false)

        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfTest.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
     //   holder.name.text = listOfTest[position]
        // holder.countIndicator.text = ""
       // holder.optionA.text = listOfTest[position].A
       // holder.question.text =listOfTest[position].question


    }
}