package com.asoit.test.faculty.test.testdetails.ongoing

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
import com.google.firebase.firestore.FirebaseFirestore
import com.kiwimob.firestore.livedata.livedata

/**
 * A simple [Fragment] subclass.
 */
class OngoingTestFragment(val testid: String?) : Fragment() {

    lateinit var recycler:RecyclerView
    lateinit var firestore:FirebaseFirestore
    lateinit var madapter:OngoingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_ongoing_test, container, false)
        recycler = view.findViewById(R.id.ongoing_test_recycler)
        firestore = FirebaseFirestore.getInstance()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


      madapter = OngoingAdapter(firestore,this)
        firestore.collection("test")
            .whereEqualTo("started",true)
            .whereEqualTo("submited",false)
            .whereEqualTo("test_id",testid)
            .livedata()
            .observe(this.requireActivity(), Observer {
                val data = it.toObjects(ResultInfoClass::class.java)
                madapter.submitList(data)
            })


        recycler.apply {
            layoutManager = LinearLayoutManager(this@OngoingTestFragment.requireContext())
            adapter = madapter
        }
    }



}
