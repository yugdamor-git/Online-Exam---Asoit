package com.asoit.test

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asoit.test.auth.LoginActivity
import com.asoit.test.auth.OtpVerification
import com.asoit.test.auth.VerifyActivity
import com.asoit.test.faculty.FacultyController
import com.asoit.test.student.StudentHomeActivity
import com.asoit.test.user.StudentInfoModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SplashActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        var LoginIntent: Intent?  = null

        if (auth.currentUser != null) {

            firestore.collection("users")
                .document(auth.currentUser!!.uid)
                .get()
                .addOnSuccessListener {
                   val user =  it.toObject(StudentInfoModel::class.java)

                    if (user != null)
                    {
                        if (user?.role == "student")
                        {
                            LoginIntent = Intent(this,StudentHomeActivity::class.java)
                            LoginIntent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            // LoginIntent!!.addFlags(FLAG_ACTIVITY_NO_HISTORY)
                        }
                        if (user?.role=="faculty")
                        {
                            LoginIntent = Intent(this, FacultyController::class.java)
                            LoginIntent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            // LoginIntent!!.addFlags(FLAG_ACTIVITY_NO_HISTORY)
                        }

                        startActivity(LoginIntent)
                        this.finish()
                    }

                    else
                    {
                        auth.signOut()

                        LoginIntent = Intent(this, OtpVerification::class.java)
                        startActivity(LoginIntent)
                        this.finish()
                    }

                }


        } else {
            LoginIntent = Intent(this, OtpVerification::class.java)
            startActivity(LoginIntent)
            this.finish()

        }



    }
}
