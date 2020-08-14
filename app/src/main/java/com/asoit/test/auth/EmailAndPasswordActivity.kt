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
import com.asoit.test.user.StudentInfoModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pixplicity.easyprefs.library.Prefs
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_email_and_password.*
import kotlinx.android.synthetic.main.fragment_signup.*

class EmailAndPasswordActivity : AppCompatActivity() {

    var temp_user_id:String =""
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
   var user_temp = StudentInfoModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_and_password)
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        temp_user_id = intent.getStringExtra("user_id")
        val email = intent.getStringExtra("email")

        email_edit_text.setText(email)

        firebaseAuth.currentUser?.delete()?.addOnFailureListener {
            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
        }
            ?.addOnSuccessListener {

                Toast.makeText(this,"deleted",Toast.LENGTH_SHORT).show()
            }

        submit_btn.setOnClickListener {

            if (email_edit_text.text.isNullOrEmpty())
            {
                Alerter.create(this)
                    .setTitle("Email")
                    .setIcon(R.drawable.ic_sad)
                    .setBackgroundColor(R.color.warning_color)
                    .setText("Please enter email address")
                    .show()
                return@setOnClickListener
            }

            if (! password_et.text.toString().equals(confirm_et.text.toString()) || password_et.text.isNullOrEmpty() || confirm_et.text.isNullOrEmpty())
            {
                Alerter.create(this)
                    .setTitle("Password")
                    .setIcon(R.drawable.ic_sad)
                    .setBackgroundColor(R.color.warning_color)
                    .setText("Problemo! The password you provided doesn’t match. Wanna try that again?")
                    .show()
                return@setOnClickListener
            }

            createUserwithEmailAndPassword(email_edit_text.text.toString(),confirm_et.text.toString())

            // removeUserFromTempUsers()
        }
    }

    private fun createUserwithEmailAndPassword(email: String, password: String) {
        e_and_p_constraintLayout.visibility = View.VISIBLE
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                addUserToUsers(temp_user_id,it.user!!.uid)
            }
            .addOnFailureListener {
                Alerter.create(this)
                    .setTitle("Firebase Auth error")
                    .setIcon(R.drawable.ic_sad)
                    .setBackgroundColor(R.color.error_color)
                    .setText(it.message)
                    .show()
                e_and_p_constraintLayout.visibility = View.GONE
            }
    }

    private fun addUserToUsers(tempUserId: String?, uid: String) {

        firestore.collection("temp_users")
            .whereEqualTo("user_id",tempUserId)
            .get()
            .addOnSuccessListener {
                val user = it.toObjects(StudentInfoModel::class.java)[0]
                user.user_id = uid

                user.role = user.role
                user.email = email_edit_text.text.toString()
               user_temp = user
                val ref = firestore.collection("users").document(uid)
                ref.set(user)
                    .addOnSuccessListener {

                        removeUserFromTempUsers(tempUserId)
                    }
                    .addOnFailureListener {
                        Alerter.create(this)
                            .setTitle("Firebase Auth error")
                            .setIcon(R.drawable.ic_sad)
                            .setBackgroundColor(R.color.error_color)
                            .setText(it.message)
                            .show()
                        e_and_p_constraintLayout.visibility = View.GONE}
            }
            .addOnFailureListener {
                Alerter.create(this)
                    .setTitle("Firebase Auth error")
                    .setIcon(R.drawable.ic_sad)
                    .setBackgroundColor(R.color.error_color)
                    .setText(it.message)
                    .show()
                e_and_p_constraintLayout.visibility = View.GONE
            }

    }

    private fun removeUserFromTempUsers(tempUserId: String?) {

        firestore.collection("temp_users")
            .document(temp_user_id)
            .delete()
            .addOnSuccessListener {
                //navigate to home page

                if (user_temp.role == "student")
                {
                    Prefs.putBoolean(STUDENT.USERLOGEDIN,true)
                    Prefs.putString(STUDENT.STUDENT_NAME,user_temp.name)
                    Prefs.putString(STUDENT.STUDENT_ID,user_temp.user_id)
                    Prefs.putInt(STUDENT.STUDENT_SEMESTER, user_temp.semester!!)
                    Prefs.putInt(STUDENT.STUDENT_BRANCH_CODE,user_temp. branch_code!!)
                    Prefs.putString(STUDENT.STUDENT_ENROLLMENT,user_temp.enrollment_no)

                    val intent = Intent(this,StudentHomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                    finish()
                }
                 if(user_temp.role == "faculty")
                {Prefs.putBoolean(STUDENT.USERLOGEDIN,true)
                    Prefs.putString(STUDENT.FACULTY_NAME,user_temp.name)
                    Prefs.putString(STUDENT.FACULTY_ID,user_temp.user_id)
                    Prefs.putString(STUDENT.FACULTY_EMAIL,user_temp.mobile_no)
                    Prefs.putString(STUDENT.FACULTY_EMAIL,user_temp.email)
                    val intent = Intent(this,FacultyController::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                    finish()
                }

            }
            .addOnFailureListener {
                Alerter.create(this)
                    .setTitle("Firebase Auth error")
                    .setIcon(R.drawable.ic_sad)
                    .setBackgroundColor(R.color.error_color)
                    .setText(it.message)
                    .show()
                e_and_p_constraintLayout.visibility = View.GONE
            }
    }
}
