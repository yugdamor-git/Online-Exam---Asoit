package com.asoit.test.faculty.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity

import com.asoit.test.R
import com.asoit.test.student.STUDENT
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_faculty_profile.*

/**
 * A simple [Fragment] subclass.
 */
class FacultyProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faculty_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            name.text = "NAME : "+Prefs.getString(STUDENT.FACULTY_NAME,"")
            email.text = "EMAIL : "+Prefs.getString(STUDENT.FACULTY_EMAIL,"")
            mobile_no.text = "MOBILE NO : "+Prefs.getString(STUDENT.FACULTY_MOBILE,"")
    }

}
