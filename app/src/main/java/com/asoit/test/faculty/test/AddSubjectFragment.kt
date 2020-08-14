package com.asoit.test.faculty.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.asoit.test.R
import com.minibugdev.sheetselection.SheetSelection
import com.minibugdev.sheetselection.SheetSelectionItem
import kotlinx.android.synthetic.main.fragment_add_subject.*
import kotlinx.android.synthetic.main.fragment_add_test.*

/**
 * A simple [Fragment] subclass.
 */
class AddSubjectFragment : Fragment() {

    var selectedSemPos:Int =0
    var selectedBranchPos:Int =0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_subject, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_subject_sem_btn.setOnClickListener {
            showSemDialog()
        }

        add_subject_branch_btn.setOnClickListener {
            showBranchDialog()
        }

        add_subject_submit_btn.setOnClickListener {

        }
    }

    private fun showBranchDialog() {
        val sem_item = listOf(
            SheetSelectionItem("1", "CE"),
            SheetSelectionItem("2", "ME"),
            SheetSelectionItem("3", "AE"),
            SheetSelectionItem("4", "EE")
        )

        SheetSelection.Builder(this.requireContext()    )
            .title("Select Branch")
            .items(sem_item)
            .selectedPosition(selectedBranchPos)
            .showDraggedIndicator(true)
            .searchEnabled(false)
            .onItemClickListener { item, position ->
                // DO SOMETHING
                selectedBranchPos = position
                add_subject_branch_btn.text = item.value
            }
            .show()
    }

    private fun showSemDialog() {
        val sem_item = listOf(
            SheetSelectionItem("1", "1"),
            SheetSelectionItem("2", "2"),
            SheetSelectionItem("3", "3"),
            SheetSelectionItem("4", "4"),
            SheetSelectionItem("5", "5"),
            SheetSelectionItem("6", "6"),
            SheetSelectionItem("7", "7"),
            SheetSelectionItem("8", "8")
        )

        SheetSelection.Builder(this.requireContext()    )
            .title("Select Semester")
            .items(sem_item)
            .selectedPosition(selectedSemPos)
            .showDraggedIndicator(true)
            .searchEnabled(false)
            .onItemClickListener { item, position ->
                // DO SOMETHING
                selectedSemPos = position
                add_subject_sem_btn.text = item.value
            }
            .show()
    }

}
