package com.asoit.test.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.asoit.test.R
import com.asoit.test.faculty.FacultyController
import com.asoit.test.student.STUDENT
import com.asoit.test.student.StudentHomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.pixplicity.easyprefs.library.Prefs
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_sign_in_using_email_and_password.*

class SignInUsingEmailAndPassword : AppCompatActivity() {
    var role =""
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_using_email_and_password)
        firebaseAuth = FirebaseAuth.getInstance()

        val email = intent.getStringExtra("email")
        role = intent.getStringExtra("role")
        email_password_email_et.setText(email)
        val name = intent.getStringExtra("name")
        val user_id = intent.getStringExtra("user_id")
        val semester = intent.getIntExtra("semester",0)
        val branch_code = intent.getIntExtra("branch_code",0)
        val enrollment_no = intent.getStringExtra("enrollment_no")




        email_password_signin_btn.setOnClickListener {

            if (email_password_et_pwd.text.toString().isNullOrEmpty())
            {
                Alerter.create(this)
                    .setTitle("Empty Password")
                    .setIcon(R.drawable.ic_sad)
                    .setBackgroundColor(R.color.warning_color)
                    .setText("Problemo! The password you provided is empty !!")
                    .show()
                return@setOnClickListener
            }

            using_e_and_p_constraintLayout.visibility = View.VISIBLE

            firebaseAuth.signInWithEmailAndPassword(email_password_email_et.text.toString(),email_password_et_pwd.text.toString())
                .addOnSuccessListener {


                    if (role =="student")
                    {
                        Prefs.putBoolean(STUDENT.USERLOGEDIN,true)
                        Prefs.putString(STUDENT.STUDENT_NAME,name)
                        Prefs.putString(STUDENT.STUDENT_ID,user_id)
                        Prefs.putInt(STUDENT.STUDENT_SEMESTER, semester!!)
                        Prefs.putInt(STUDENT.STUDENT_BRANCH_CODE, branch_code!!)
                        Prefs.putString(STUDENT.STUDENT_ENROLLMENT,enrollment_no)

                        val intent = Intent(this,StudentHomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    else if(role=="faculty")
                    {   Prefs.putBoolean(STUDENT.USERLOGEDIN,true)
                        Prefs.putString(STUDENT.FACULTY_NAME,name)
                        Prefs.putString(STUDENT.FACULTY_ID,user_id)
                        val intent = Intent(this,FacultyController::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }


                }
                .addOnFailureListener {
                    using_e_and_p_constraintLayout.visibility = View.GONE
                    Alerter.create(this)
                        .setTitle("Firestore error")
                        .setIcon(R.drawable.ic_sad)
                        .setBackgroundColor(R.color.error_color)
                        .setText(it.message)
                        .show()
                }
        }
    }
}
