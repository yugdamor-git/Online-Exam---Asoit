package com.asoit.test.student.home.tab.upcoming

import android.os.Bundle
import android.util.Log
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
import com.asoit.test.student.home.tab.ActiveRecyclerAdapter
import com.asoit.test.student.models.TestModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kiwimob.firestore.livedata.livedata
import com.pixplicity.easyprefs.library.Prefs
import com.stone.vega.library.VegaLayoutManager

/**
 * A simple [Fragment] subclass.
 */
class UpcomingFragment : Fragment() {
    lateinit var recycler: RecyclerView
    lateinit var mAdapter: upComingAdapter
    lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upcoming, container, false)
        recycler = view.findViewById(R.id.recyclerview_upcoming)
        firestore = FirebaseFirestore.getInstance()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAdapter = upComingAdapter()

        //logic of upcoming
        firestore.collection("test")
            .whereEqualTo("semester",Prefs.getInt(STUDENT.STUDENT_SEMESTER,0))
            .whereEqualTo("user_id",Prefs.getString(STUDENT.STUDENT_ID,""))
            .whereEqualTo("branch_code",Prefs.getInt(STUDENT.STUDENT_BRANCH_CODE,0))
            .whereEqualTo("started",false)
            .whereEqualTo("submited",false)
            .whereEqualTo("published",true)
            .whereEqualTo("test_active",false)
            .whereGreaterThan("test_date",Timestamp.now())
            .orderBy("test_date",Query.Direction.DESCENDING)
            .livedata(ResultInfoClass::class.java)
            .observe(this.requireActivity(), Observer {
                mAdapter.submitList(it)
            })


        recycler.apply {
            this.layoutManager = VegaLayoutManager()
            this.adapter =mAdapter


        }
    }

}
