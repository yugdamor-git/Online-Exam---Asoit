package com.asoit.test.faculty.test

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.NavController
import com.asoit.test.faculty.home.alltest.AllTestFragment
import com.asoit.test.student.home.tab.completed.CompletedFragment
import com.asoit.test.student.home.tab.missed.MissedFragment
import com.asoit.test.student.home.tab.upcoming.UpcomingFragment

class FacultyTablayoutAdapter(fm: FragmentManager,val findNavController: NavController) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
    var fragment:Fragment? = null
        if (position == 0)
        {
            fragment = AllTestFragment()
        }
        if(position == 1)
        {
            fragment= UpcomingFragment()
        }
        if(position == 2)
        {
            fragment= MissedFragment()
        }
        if (position == 3)
        {
            fragment=
                CompletedFragment()
        }

        return fragment!!

    }

    override fun getCount(): Int {

        return 4
    }


}