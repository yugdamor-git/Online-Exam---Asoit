package com.asoit.test.faculty.test.testdetails.missed

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
import kotlinx.android.synthetic.main.fragment_missed_test.*

/**
 * A simple [Fragment] subclass.
 */
class MissedTestFragment(val testid: String?) : Fragment() {

    lateinit var firestore: FirebaseFirestore
    lateinit var madapter:MissedAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_missed_test, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
       madapter = MissedAdapter(this)

        firestore.collection("test")
            .whereEqualTo("test_active",false)
            .whereEqualTo("test_id",testid)
            .whereEqualTo("started",false)
            .whereEqualTo("submited",false)
            .whereLessThan("valid_till",com.google.firebase.Timestamp.now())
            .livedata()
            .observe(this.requireActivity(), Observer {
                madapter.submitList(it.toObjects(ResultInfoClass::class.java))
            })

        missed_test_recycler.apply {
            layoutManager = LinearLayoutManager(this@MissedTestFragment.requireContext())
            adapter = madapter
        }

    }

}
