package com.asoit.test.faculty.home.alltest.subjectwise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import com.asoit.test.student.models.TestModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_select_test.*

/**
 * A simple [Fragment] subclass.
 */
class SelectTestFragment : Fragment() {

    lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firestore = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_select_test, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mAdapter = TestAdapter(findNavController())
        val branch_code = arguments?.getInt("branch_code")
        val subject_code = arguments?.getInt("subject_code")
        val semester = arguments?.getInt("semester")

        firestore.collection("test")
            .whereEqualTo("branch_code",branch_code)
            .whereEqualTo("subject_code",subject_code)
            .whereEqualTo("semester",semester)
            .get()
            .addOnSuccessListener {
                val data = it.toObjects(ResultInfoClass::class.java)
                mAdapter.submitList(data)
            }
        var listoftest = ArrayList<TestModel>()

          select_test_recycler.apply {
            layoutManager = LinearLayoutManager(this@SelectTestFragment.requireContext())
            adapter = mAdapter
        }
    }

}
