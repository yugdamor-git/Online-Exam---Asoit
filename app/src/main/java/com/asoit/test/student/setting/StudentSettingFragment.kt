package com.asoit.test.student.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.asoit.test.R
import com.asoit.test.SplashActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_student_setting.*

/**
 * A simple [Fragment] subclass.
 */
class StudentSettingFragment : Fragment() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_student_setting, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        student_logout.setOnClickListener {
            val intent = Intent(this.requireActivity(),SplashActivity::class.java)
            Prefs.clear()
           firestore.clearPersistence().addOnSuccessListener {  }
            firebaseAuth.signOut()
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this.activity?.finish()
        }
    }

}
