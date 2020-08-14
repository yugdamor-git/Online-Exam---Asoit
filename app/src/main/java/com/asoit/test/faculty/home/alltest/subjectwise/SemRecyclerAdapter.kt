package com.asoit.test.faculty.home.alltest.subjectwise

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.asoit.test.R

class SemRecyclerAdapter(
    val context: Context,
    val listOfTest: ArrayList<String>,
    val findNavController: NavController,
    val branchCode: Int?
):RecyclerView.Adapter<SemRecyclerAdapter.viewHolder>() {

    inner class viewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        //var time = view.findViewById<TextView>(R.id.test_time_tv)
        //var title = view.findViewById<TextView>(R.id.test_title_tv)
       // var date = view.findViewById<TextView>(R.id.test_date_tv)
       // var total = view.findViewById<TextView>(R.id.test_total_tv)
        var name = view.findViewById<TextView>(R.id.subjectwise_name)
        val root = view.findViewById<ConstraintLayout>(R.id.subjectwise_root)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.subjectwise_item,parent,false)

        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfTest.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
       // holder.date.text = listOfTest[position].date
       // holder.title.text = listOfTest[position].title
        //holder.time.text =listOfTest[position].time
      //  holder.total.text = listOfTest[position].totalQues

        holder.name.text = listOfTest[position]
        holder.root.setOnClickListener {
            //navigate to
            val action = SelectSemFragmentDirections.actionSelectSemFragmentToSelectSubjectFragment()
            action.setBranchCode(this!!.branchCode!!)
            action.setSemester(listOfTest[position].toInt())
            findNavController.navigate(action)
        }
       // holder.title.setOnClickListener {
         //       val action = FacultyHomeFragmentDirections.actionFacultyHomeFragmentToTestDetailsFragment()
           //     findNavController.navigate(action)
       // }
    }
}