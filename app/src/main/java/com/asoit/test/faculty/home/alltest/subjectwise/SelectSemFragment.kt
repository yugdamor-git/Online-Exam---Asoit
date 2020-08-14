package com.asoit.test.faculty.home.alltest.subjectwise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.asoit.test.R
import kotlinx.android.synthetic.main.fragment_select_sem.*

/**
 * A simple [Fragment] subclass.
 */
class SelectSemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_sem, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val branch_code = arguments?.getInt("branch_code")

        var list = arrayListOf<String>("1","2","3","4","5","6","7","8")
        select_sem_recycler.apply {
            layoutManager = LinearLayoutManager(this@SelectSemFragment.requireContext())
            adapter = SemRecyclerAdapter(this@SelectSemFragment.requireContext(),list,findNavController(),branch_code)
        }
    }

}
