package com.asoit.test.student.home.tab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asoit.test.R
import com.asoit.test.student.models.TestModel

class ActiveRecyclerAdapter(val context:Context,val listOfTest:ArrayList<TestModel>):RecyclerView.Adapter<ActiveRecyclerAdapter.viewHolder>() {

    inner class viewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        var time = view.findViewById<TextView>(R.id.test_time_tv)
        var title = view.findViewById<TextView>(R.id.test_title_tv)
      //  var date = view.findViewById<TextView>(R.id.test_date_tv)
        var total = view.findViewById<TextView>(R.id.test_total_tv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.test_recyclerview_item,parent,false)

        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfTest.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
       // holder.date.text = listOfTest[position].date
        holder.title.text = listOfTest[position].title
        holder.time.text =listOfTest[position].time
        holder.total.text = listOfTest[position].totalQues
    }
}