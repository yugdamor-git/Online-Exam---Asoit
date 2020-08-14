package com.asoit.test.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.asoit.test.R
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_verify.*
import java.util.concurrent.TimeUnit

class VerifyActivity : AppCompatActivity() {

    var verificationID:String=""
    var temp_user_id=""
    var email=""
    var phone_no =""
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
        firebaseAuth = FirebaseAuth.getInstance()
        val enrollment = intent.getStringExtra("enrollment_no")
         phone_no = intent.getStringExtra("phone_no")
        val name = intent.getStringExtra("name")
        email = intent.getStringExtra("email")
        a_v_constraintLayout.visibility= View.VISIBLE
        temp_user_id = intent.getStringExtra("user_id")
        name_with_hello.text = "Hello, "+ name
        information_mobile_tv.text = "Enter the verification code we just sent you on \n+91 "+phone_no
       sendVerificationTOPhoneNo(phone_no)

        otp_verify_btn.setOnClickListener {
            if (enter_otp.text.isNullOrEmpty())
            {

                Alerter.create(this)
                    .setTitle("Empty OTP")
                    .setIcon(R.drawable.ic_empty_enrollment)
                    .setBackgroundColor(R.color.warning_color)
                    .setText("if : no action, there won't be any reaction. enter OTP")
                    .show()
                return@setOnClickListener
            }
            a_v_constraintLayout.visibility = View.VISIBLE
            val code = enter_otp.text.toString().trim()
            verifyCode(code)
        }

    }

    private fun verifyCode(code:String)
    {
        val cred = PhoneAuthProvider.getCredential(verificationID,code)
        signInWithCred(cred)
    }

    private fun signInWithCred(cred: PhoneAuthCredential) {

        firebaseAuth.signInWithCredential(cred)
            .addOnSuccessListener {

                // set  email and password activity
                //move user from temp_user to users
                val intent = Intent(this,EmailAndPasswordActivity::class.java)
                intent.putExtra("user_id",temp_user_id)
                intent.putExtra("email",email)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                finish()

            }
            .addOnFailureListener {
                a_v_constraintLayout.visibility = View.GONE

                Alerter.create(this)
                    .setTitle("Firebase error")
                    .setIcon(R.drawable.ic_sad)
                    .setBackgroundColor(R.color.error_color)
                    .setText(it.message)
                    .show()
            }
    }

    private fun sendVerificationTOPhoneNo(phoneNo: String?) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91"+phoneNo!!,
            60,
            TimeUnit.SECONDS,
            this,
            mcallack

        )





    }

    val mcallack = object :PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {
        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            verificationID = p0

            Alerter.create(this@VerifyActivity)
                .setTitle("OTP")
                .setIcon(R.drawable.ic_happy_smiley)
                .setBackgroundColor(R.color.success_color)
                .setText("OTP sent on +91 "+ phone_no )
                .show()
            a_v_constraintLayout.visibility = View.GONE

        }
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            val code = p0.smsCode
            Toast.makeText(this@VerifyActivity,"verified",Toast.LENGTH_SHORT).show()
           enter_otp.setText(code.toString())

            if (code != null)
            {
                verifyCode(code)
            }
            if (code == null)
            {
                firebaseAuth.signInWithCredential(p0)
                val intent = Intent(this@VerifyActivity,EmailAndPasswordActivity::class.java)
                intent.putExtra("user_id",temp_user_id)
                intent.putExtra("email",email)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                finish()
            }
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Alerter.create(this@VerifyActivity)
                .setTitle("Verification Failed")
                .setIcon(R.drawable.ic_sad)
                .setBackgroundColor(R.color.error_color)
                .setText(p0.message)
                .show()
        }

    }
}




