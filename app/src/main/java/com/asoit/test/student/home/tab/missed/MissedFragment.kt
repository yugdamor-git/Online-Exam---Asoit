package com.asoit.test.student.home.tab.missed

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
import com.kiwimob.firestore.livedata.livedata
import com.pixplicity.easyprefs.library.Prefs

/**
 * A simple [Fragment] subclass.
 */
class MissedFragment : Fragment() {
    lateinit var recycler: RecyclerView
    lateinit var mAdapter: missedAdapter
    lateinit var firestore: FirebaseFirestore
 var temp = ArrayList<ResultInfoClass>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_missed, container, false)
        recycler = view.findViewById(R.id.recyclerview_missed)
        mAdapter = missedAdapter(this)
        firestore = FirebaseFirestore.getInstance()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        firestore.collection("test")
            .whereEqualTo("test_active",false)
            .whereEqualTo("semester",Prefs.getInt(STUDENT.STUDENT_SEMESTER,0))
            .whereEqualTo("branch_code",Prefs.getInt(STUDENT.STUDENT_BRANCH_CODE,0))
            .whereEqualTo("started",false)
            .whereEqualTo("submited",false)
            .whereEqualTo("user_id",Prefs.getString(STUDENT.STUDENT_ID,""))
            .whereLessThan("valid_till",com.google.firebase.Timestamp.now())
            .livedata(ResultInfoClass::class.java)
            .observe(this.requireActivity(), Observer {

                mAdapter.submitList(it)
            })


        recycler.apply {
            this.layoutManager = LinearLayoutManager(this@MissedFragment.requireContext())
            this.adapter = mAdapter


        }
    }

}
