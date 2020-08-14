package com.asoit.test.faculty.home.unpublish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import com.asoit.test.firestoremodel.TestInfoClass
import com.google.firebase.firestore.FirebaseFirestore
import com.kiwimob.firestore.livedata.livedata
import com.stone.vega.library.VegaLayoutManager

/**
 * A simple [Fragment] subclass.
 */
class UnpublishedFragment : Fragment() {

    lateinit var firestore:FirebaseFirestore
    lateinit var recycler:RecyclerView
    lateinit var progress:ConstraintLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firestore = FirebaseFirestore.getInstance()

        val view= inflater.inflate(R.layout.fragment_unpublished, container, false)
        progress = view.findViewById<ConstraintLayout>(R.id.unpublished_constrain)
        recycler = view.findViewById(R.id.unpublished_recycler)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var listoftest = ArrayList<TestInfoClass>()
        val madapter =  UnpublishedAdapter(firestore,this,progress)
        recycler.apply {
            layoutManager = VegaLayoutManager()
           adapter = madapter
            // adapter = UnpublishedRecyclerAdapter(this@UnpublishedFragment.requireContext(),listoftest,findNavController(),firestore)
        }
        firestore.collection("all_test")
            .whereEqualTo("published",false)
            .livedata()
            .observe(this.requireActivity()
        , Observer {

                val data =  it.toObjects(ResultInfoClass::class.java)

                    madapter.submitList(data)



            })






    }

}
