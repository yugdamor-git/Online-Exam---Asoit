package com.asoit.test.student.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.view.get

import com.asoit.test.R
import com.asoit.test.student.STUDENT
import com.asoit.test.student.home.tab.TablayoutAdapter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_faculty_home.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout.animation =  AnimationUtils.loadAnimation(context,R.anim.fade_animation_right_to_left)
        tabLayout.animation.startOffset = 500

        viewPager.adapter = TablayoutAdapter(childFragmentManager)
        tabLayout.elevation =0f
        tabLayout.setupWithViewPager(viewPager)
        //tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"),Color.WHITE)
        tabLayout.getTabAt(0)?.let {
            it.setIcon(R.drawable.ic_active_test_icon)
            it.setText("Active")
        }

        tabLayout.getTabAt(1)?.let {
            it.setIcon(R.drawable.ic_upcoming_icon)
            it.setText("Upcoming")
        }
        tabLayout.getTabAt(2)?.let {
            it.setIcon(R.drawable.ic_missed_test)
            it.setText("Missed")
        }
        tabLayout.getTabAt(3)?.let {
            it.setIcon(R.drawable.ic_right)
            it.setText("Completed")
        }



    }

}
