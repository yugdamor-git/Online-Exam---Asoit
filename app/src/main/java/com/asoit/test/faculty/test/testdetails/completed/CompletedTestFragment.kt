package com.asoit.test.faculty.test.testdetails.completed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import com.google.firebase.firestore.FirebaseFirestore
import com.kiwimob.firestore.livedata.livedata
import kotlinx.android.synthetic.main.fragment_completed_test.*

/**
 * A simple [Fragment] subclass.
 */
class CompletedTestFragment(val testid: String?) : Fragment() {

    lateinit var madapter:CompletedAdapter
    lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        madapter = CompletedAdapter()

        firestore.collection("test")
            .whereEqualTo("test_id",testid)
            .whereEqualTo("started",true)
            .whereEqualTo("submited",true)
            .livedata(ResultInfoClass::class.java)
            .observe(this.requireActivity(), Observer {
                madapter.submitList(it)
            })


        completed_test_recycler.apply {
            layoutManager = LinearLayoutManager(this@CompletedTestFragment.requireContext())
            adapter = madapter
        }

    }

}
