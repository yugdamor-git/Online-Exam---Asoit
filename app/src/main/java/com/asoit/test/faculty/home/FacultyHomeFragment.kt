package com.asoit.test.faculty.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView

import com.asoit.test.R
import com.asoit.test.student.STUDENT
import com.asoit.test.student.home.questions.QuestionsActivity
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_faculty_home.*

/**
 * A simple [Fragment] subclass.
 */
class FacultyHomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_faculty_home, container, false)


        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //set faculty name from shared prefs

       //setup icon
       speeddail.addActionItem(
           SpeedDialActionItem.Builder(R.id.fab_no_label,R.drawable.ic_home_add_test)
               .setLabel("add test")
               .setFabBackgroundColor(Color.parseColor("#0F8882"))
               .create()
       )

        speeddail.addActionItem(
            SpeedDialActionItem.Builder(R.id.add_subject,R.drawable.ic_add_alert_black_24dp)
                .setLabel("ADD SUBJECT")
                .setFabBackgroundColor(Color.parseColor("#0F8882"))
                .create()
        )
        speeddail.addActionItem(
            SpeedDialActionItem.Builder(R.id.start_exam,R.drawable.ic_add_alert_black_24dp)
                .setLabel("Start exam")
                .setFabBackgroundColor(Color.parseColor("#0F8882"))
                .create()
        )
        speeddail.addActionItem(
            SpeedDialActionItem.Builder(R.id.add_students,R.drawable.ic_add_user)
                .setLabel("ADD USERS")
                .setFabBackgroundColor(Color.parseColor("#0F8882"))
                .create()
        )
        speeddail.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener {

            when (it.id){
                R.id.fab_no_label -> {
                    val action = FacultyHomeFragmentDirections.actionFacultyHomeFragmentToAddTestFragment()
                    findNavController().navigate(action)
                   // Toast.makeText(this.requireContext(),"add_ test",Toast.LENGTH_SHORT).show()
                    return@OnActionSelectedListener  false
                }
            }
            when(it.id)
            {
                R.id.add_subject -> {
                    val action = FacultyHomeFragmentDirections.actionFacultyHomeFragmentToAddSubjectFragment()
                    findNavController().navigate(action)
                    return@OnActionSelectedListener false
                }
            }
            when(it.id)
            {
                R.id.start_exam -> {
                   val intent = Intent(this.requireContext(),QuestionsActivity::class.java)
                    startActivity(intent)
                    // val action = FacultyHomeFragmentDirections.actionFacultyHomeFragmentToAddSubjectFragment()
                   // findNavController().navigate(action)
                    return@OnActionSelectedListener false
                }
            }
            when(it.id)
            {
                R.id.add_students ->{
                    val action = FacultyHomeFragmentDirections.actionFacultyHomeFragmentToAddStudentsFragment()
                    findNavController().navigate(action)
                    return@OnActionSelectedListener false
                }
            }
            false
        })

        tabLayout_faculty.animation =  AnimationUtils.loadAnimation(context,R.anim.fade_animation_right_to_left)
        tabLayout_faculty.animation.startOffset = 500
        viewPager_faculty.adapter = FacultyHomeTablayoutAdapter(childFragmentManager)
        tabLayout_faculty.elevation =0f
        tabLayout_faculty.setupWithViewPager(viewPager_faculty)
       // tabLayout_faculty.setTabTextColors(Color.parseColor("#FFFFFF"),Color.WHITE)
        tabLayout_faculty.getTabAt(0)?.let {
            it.setIcon(R.drawable.ic_all_test)
            it.setText("All Test")
        }

        tabLayout_faculty.getTabAt(1)?.let {
            it.setIcon(R.drawable.ic_subjectwise_icon)
            it.setText("Branch wise")
        }

        tabLayout_faculty.getTabAt(2)?.let {
            it.setIcon(R.drawable.ic_unpublished)
            it.setText("Unpublished")
        }
    }




    private fun showAddSubjectDialog() {
        val dialog =
            MaterialDialog(this.requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                title(text = "hello world")
                customView(
                    R.layout.custom_add_subject_view,
                    scrollable = true,
                    horizontalPadding = true
                )
                positiveButton(text = "submit") { dialog ->
                    // Pull the password out of the custom view when the positive button is pressed

                }
                negativeButton(android.R.string.cancel)


            }

    }}
