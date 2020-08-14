package com.asoit.test.faculty.home.alltest.subjectwise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.asoit.test.R
import com.asoit.test.firestoremodel.SubjectInfoModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_select_subject.*

/**
 * A simple [Fragment] subclass.
 */
class SelectSubjectFragment : Fragment() {


    lateinit var mAdapter: subjectAdapter
    lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firestore = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_select_subject, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val branch_code = arguments?.getInt("branch_code")
        val semester = arguments?.getInt("semester")

    mAdapter = subjectAdapter(branch_code,semester)
        firestore.collection("subject")
            .whereEqualTo("semester",semester)
            .whereEqualTo("branch_code",branch_code)
            .get()
            .addOnSuccessListener {
                val data = it.toObjects(SubjectInfoModel::class.java)
                mAdapter.submitList(data)
            }

        select_subject_recycler.apply {
            layoutManager = LinearLayoutManager(this@SelectSubjectFragment.requireContext())
            adapter = mAdapter
        }
    }

}
