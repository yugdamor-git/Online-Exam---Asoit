package com.asoit.test.faculty.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.asoit.test.faculty.home.alltest.AllTestFragment
import com.asoit.test.faculty.home.alltest.subjectwise.SelectBranchFragment
import com.asoit.test.faculty.home.unpublish.UnpublishedFragment

class FacultyHomeTablayoutAdapter(
    fm: FragmentManager

) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 ->
                 AllTestFragment()
            1 ->
                 SelectBranchFragment()
            2 ->
                 UnpublishedFragment()
            else ->
                null!!

        }

    }

    override fun getCount(): Int {

        return 3
    }


}