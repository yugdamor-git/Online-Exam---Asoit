package com.asoit.test.faculty.home.alltest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import com.asoit.test.firestoremodel.TestInfoClass
import com.asoit.test.firestoremodel.allTestModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import com.kiwimob.firestore.livedata.livedata
import com.stone.vega.library.VegaLayoutManager
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class AllTestFragment() : Fragment() {

    lateinit var recycler:RecyclerView
   // lateinit var Madapter:AllTestRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_all_test, container, false)
        recycler = view.findViewById(R.id.all_test_recycler)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val setting = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        val db = FirebaseFirestore.getInstance()
        val adapterB = allTestAdapter(this.requireContext(),findNavController(),db)
        /**
         *   var dummy = ArrayList<ResultDataClass>()
        for ( i in 1..30)
        {
        val a = ResultDataClass()
        a.score = 0 +i
        a.started = true
        a.submited = false
        a.test_id = "171200"+i
        a.user_id = "171200107004"+i
        a.time_left = (1000000+i).toLong()
        a.user_enrollment = "171200107004"+i
        a.user_name = "damor yogesh ramanlal"+i
        dummy.add(a)
        }

        dummy.forEachIndexed { index, resultDataClass ->
        db.collection("test_result").document().set(resultDataClass).addOnFailureListener {

        }
        }

         */

        db.firestoreSettings = setting
        val data = ArrayList<TestInfoClass>()

            db.collection("all_test")
                .whereEqualTo("published",true)
                .orderBy("created_date",Query.Direction.DESCENDING)
                .livedata()
                .observe(this.requireActivity(), Observer {
                    adapterB.submitList(it.toObjects(allTestModel::class.java))
                })

        recycler.apply {
            layoutManager = LinearLayoutManager(this@AllTestFragment.requireContext())
            adapter = adapterB


        }
        /**
         *    db.collection("all_test").livedata(TestDataClass::class.java).observe(this.requireActivity(), Observer {

        all_test_recycler.adapter = AllTestRecyclerAdapter(this@AllTestFragment.requireContext(),it,findNavController)
        })
         *
         *  db.collection("all_test").get().addOnSuccessListener {
        val data = it.toObjects(testDetailsModel::class.java)
        listoftest.addAll(data)
        all_test_recycler?.let {
        it.adapter?.notifyDataSetChanged()
        }
        }
         */


      //  listoftest.add(TestModel("22 april 2020","221100 SE UNIT 1","70 mins","50"))

   //     listoftest.add(TestModel("24 april 2020","221100 SE UNIT 2","60 mins","40"))

   //     listoftest.add(TestModel("25 april 2020","221100 SE UNIT 3","10 mins","80"))

    //    listoftest.add(TestModel("26 april 2020","221100 SE UNIT 4","60 mins","60"))



    }




}
