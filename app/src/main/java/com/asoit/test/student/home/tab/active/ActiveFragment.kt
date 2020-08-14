package com.asoit.test.student.home.tab.active

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import com.asoit.test.firestoremodel.TestInfoClass
import com.asoit.test.student.STUDENT
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kiwimob.firestore.livedata.livedata
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Time

/**
 * A simple [Fragment] subclass.
 */
class ActiveFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    lateinit var tv:TextView
    lateinit var recycler:RecyclerView
    lateinit var Madapter: studentActiveAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_active, container, false)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        recycler = view.findViewById(R.id.active_recyclerview)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tempList = ArrayList<ResultInfoClass>()
        Madapter = studentActiveAdapter(this.requireContext(),firestore)


        firestore.collection("test")
            .whereEqualTo("published",true)
            .whereEqualTo("user_id",Prefs.getString(STUDENT.STUDENT_ID,""))
            .whereEqualTo("submited",false)
            .whereEqualTo("branch_code",Prefs.getInt(STUDENT.STUDENT_BRANCH_CODE,0))
            .whereEqualTo("semester",Prefs.getInt(STUDENT.STUDENT_SEMESTER,0))
            .whereEqualTo("test_active",true)
            .orderBy("test_date",Query.Direction.DESCENDING)
            .livedata(ResultInfoClass::class.java)
            .observe(this.requireActivity(), Observer {
                Madapter.submitList(it)
            })


        /***
         *
         * if (Timestamp.now() > it.test_date!! && Timestamp.now() < it.valid_till!!)
        {
        list.add(it)
        }
         */
        recycler.apply {
            this.layoutManager = LinearLayoutManager(this@ActiveFragment.requireContext())
            this.adapter = Madapter


        }

    }

    fun refresh()
    {
        firestore.collection("test")
            .whereEqualTo("published",true)
            .whereEqualTo("user_id",Prefs.getString(STUDENT.STUDENT_ID,""))
            .whereEqualTo("submited",false)
            .whereEqualTo("branch_code",Prefs.getInt(STUDENT.STUDENT_BRANCH_CODE,0))
            .whereEqualTo("semester",Prefs.getInt(STUDENT.STUDENT_SEMESTER,0))
            .get()
            .addOnSuccessListener {
                Madapter.submitList(it.toObjects(ResultInfoClass::class.java))
            }
    }

}


/*
.whereEqualTo("user_id",Prefs.getString(STUDENT.STUDENT_ID,"null"))
            .whereEqualTo("semester",Prefs.getString(STUDENT.STUDENT_SEMESTER,"null"))
            .whereEqualTo("branch",Prefs.getString(STUDENT.STUDENT_BRANCH,"null"))
            .whereEqualTo("published",true)
            .whereEqualTo("submited",false)


              firestore.collection("test")
            .whereEqualTo("published",true)
            .whereGreaterThan("valid_till",Timestamp.now())
            .livedata(ResultInfoClass::class.java)
            .observe(this.requireActivity(), Observer {
                Madapter.submitList(it)
            })

 */