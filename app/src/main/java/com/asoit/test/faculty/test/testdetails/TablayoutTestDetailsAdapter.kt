package com.asoit.test.faculty.test.testdetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.asoit.test.faculty.test.testdetails.completed.CompletedTestFragment
import com.asoit.test.faculty.test.testdetails.missed.MissedTestFragment
import com.asoit.test.faculty.test.testdetails.ongoing.OngoingTestFragment

class TablayoutTestDetailsAdapter(
    fm: FragmentManager,
    val testid: String?
) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
    var fragment:Fragment? = null
        if (position == 0)
        {
            fragment = OngoingTestFragment(testid)
        }
        if(position == 1)
        {
            fragment= MissedTestFragment(testid)
        }
        if(position == 2)
        {
            fragment= CompletedTestFragment(testid)
        }


        return fragment!!

    }

    override fun getCount(): Int {

        return 3
    }


}