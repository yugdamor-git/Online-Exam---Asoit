package com.asoit.test.student.home.tab.completed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import com.asoit.test.student.STUDENT
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kiwimob.firestore.livedata.livedata
import com.pixplicity.easyprefs.library.Prefs

/**
 * A simple [Fragment] subclass.
 */
class CompletedFragment : Fragment() {

    lateinit var recycler: RecyclerView
    lateinit var mAdapter: completedAdapter
    lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_completed, container, false)
        mAdapter = completedAdapter(this)
        firestore = FirebaseFirestore.getInstance()
        recycler = view.findViewById(R.id.recyclerview_completed)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        firestore.collection("test")
            .whereEqualTo("user_id",Prefs.getString(STUDENT.STUDENT_ID,""))
            .whereEqualTo("started",true)
            .whereEqualTo("submited",true)
            .whereEqualTo("branch_code",Prefs.getInt(STUDENT.STUDENT_BRANCH_CODE,0))
            .whereEqualTo("semester",Prefs.getInt(STUDENT.STUDENT_SEMESTER,0))
            .orderBy("test_date",Query.Direction.DESCENDING)
            .livedata(ResultInfoClass::class.java)
            .observe(this.requireActivity(), Observer {
                mAdapter.submitList(it)
            })



        recycler.apply {
            this.layoutManager = LinearLayoutManager(this@CompletedFragment.requireContext())
            this.adapter = mAdapter

        }
    }

}
