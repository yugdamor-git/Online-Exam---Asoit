package com.asoit.test.faculty.home.alltest.subjectwise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.asoit.test.R
import com.asoit.test.firestoremodel.BranchInfoModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kiwimob.firestore.livedata.livedata
import kotlinx.android.synthetic.main.fragment_select_branch.*

/**
 * A simple [Fragment] subclass.
 */
class SelectBranchFragment : Fragment() {

    lateinit var mAdapter:branchAdapter
    lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        firestore = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_select_branch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = branchAdapter()
        firestore.collection("branch")
            .livedata(BranchInfoModel::class.java)
            .observe(this.requireActivity(), Observer {
                mAdapter.submitList(it)
            })

        select_branch_recycler.apply {
            layoutManager = LinearLayoutManager(this@SelectBranchFragment.requireContext())
            adapter = mAdapter
        }
    }

}
