package com.asoit.test.faculty.test.testdetails

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.asoit.test.R
import kotlinx.android.synthetic.main.fragment_test_details.*

/**
 * A simple [Fragment] subclass.
 */
class TestDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testid =  arguments?.getString("test_id")
        Toast.makeText(this.requireContext(),testid,Toast.LENGTH_SHORT).show()
        test_details_viewpager.adapter = TablayoutTestDetailsAdapter(childFragmentManager,testid)
        test_details_tablayout.elevation =0f
        test_details_tablayout.setupWithViewPager(test_details_viewpager)
       // test_details_tablayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.WHITE)
        test_details_tablayout.getTabAt(0)?.let {
            it.setIcon(R.drawable.ic_ongoing_test)
            it.setText("Ongoing")
        }

        test_details_tablayout.getTabAt(1)?.let {
            it.setIcon(R.drawable.ic_missed_test)
            it.setText("Missed")
        }
        test_details_tablayout.getTabAt(2)?.let {
            it.setIcon(R.drawable.ic_right)
            it.setText("Completed")
        }


    }

}
