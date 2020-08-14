package com.asoit.test.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.asoit.test.R
import com.asoit.test.user.StudentInfoModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_otp_verification.*

class OtpVerification : AppCompatActivity() {

   var phone_no=""
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)
        firestore = FirebaseFirestore.getInstance()


        otp_send_btn.animation = AnimationUtils.loadAnimation(this,R.anim.fade_animation_right_to_left)
        otp_send_btn.animation.startOffset = 500

        otp_enter_enrollment.animation = AnimationUtils.loadAnimation(this,R.anim.fade_animation_left_to_right)
        otp_enter_enrollment.animation.startOffset = 500
     val ref=   firestore.collection("temp_users")
            .document()
        val user = StudentInfoModel()
        user.user_id = ref.id
        user.email ="damor300@gmail.com"
        user.role = "student"
        user.semester = 6
        user.name ="damor yogesh r"
        user.mobile_no ="7046661287"
        user.enrollment_no = "171200107004"
        user.branch_code =7
        user.branch ="ce"

        ref.set(user)
            .addOnSuccessListener {

            }



        otp_send_btn.setOnClickListener {

            if(otp_enter_enrollment.text.toString().isNullOrEmpty())
            {
                Alerter.create(this)
                    .setTitle("Empty enrollment")
                    .setIcon(R.drawable.ic_empty_enrollment)
                    .setBackgroundColor(R.color.warning_color)
                    .setText("if : no action, there won't be any reaction. enter enrollment no")
                    .show()
                return@setOnClickListener
            }
            if (otp_enter_enrollment.text.length < 10)
            {
                Alerter.create(this)
                    .setTitle("Wrong enrollment no")
                    .setIcon(R.drawable.ic_fake_smile)
                    .setBackgroundColor(R.color.warning_color)
                    .setText("The length of enrollment no should be 12 digit")
                    .show()
                return@setOnClickListener
            }

            val enrollment_no = otp_enter_enrollment.text.toString()


            checkEnrollmentNoAndGetMobileNo(enrollment_no)




        }
    }

    private fun checkEnrollmentNoAndGetMobileNo(enrollmentNo: String) {
        a_o_v_constraintLayout.visibility = View.VISIBLE
        firestore.collection("temp_users")
            .whereEqualTo("enrollment_no",enrollmentNo)
            .get()
            .addOnSuccessListener {
                val temp_user = it.toObjects(StudentInfoModel::class.java)

                if (temp_user.size == 0 || temp_user.isNullOrEmpty()) {

                    firestore.collection("users")
                        .whereEqualTo("enrollment_no",enrollmentNo)
                        .get()
                        .addOnSuccessListener {
                            val user = it.toObjects(StudentInfoModel::class.java)
                            if (user.size ==0 || user.isNullOrEmpty())
                            {

                                Alerter.create(this)
                                    .setTitle("Contact admin/faculty")
                                    .setIcon(R.drawable.ic_sad)
                                    .setBackgroundColor(R.color.error_color)
                                    .setText("we don't have any information about you !")
                                    .show()
                                a_o_v_constraintLayout.visibility = View.GONE
                                return@addOnSuccessListener
                            }
                            else{
                                //navigate to sign in using email and password
                                val intent = Intent(this,SignInUsingEmailAndPassword::class.java)
                                intent.putExtra("email",user[0].email)
                                intent.putExtra("role",user[0].role)
                                intent.putExtra("semester",user[0].semester)
                                intent.putExtra("branch",user[0].branch)
                                intent.putExtra("branch_code",user[0].branch_code)
                                intent.putExtra("name",user[0].name)
                                intent.putExtra("mobile_no",user[0].mobile_no)
                                intent.putExtra("user_id",user[0].user_id)
                                intent.putExtra("enrollment_no",user[0].enrollment_no)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                a_o_v_constraintLayout.visibility = View.GONE
                                startActivity(intent)
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                                finish()
                            }

                        }
                        .addOnFailureListener {
                            Alerter.create(this)
                                .setTitle("Firestore error")
                                .setIcon(R.drawable.ic_sad)
                                .setBackgroundColor(R.color.error_color)
                                .setText(it.message)
                                .show()
                            a_o_v_constraintLayout.visibility = View.GONE
                        }
                    return@addOnSuccessListener
                } else {

                    phone_no = temp_user[0].mobile_no.toString()

                    val intent = Intent(this, VerifyActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("enrollment_no", enrollmentNo)
                    intent.putExtra("phone_no", phone_no)
                    intent.putExtra("name", temp_user[0].name)
                    intent.putExtra("user_id", temp_user[0].user_id)
                    intent.putExtra("email",temp_user[0].email)
                    /*
            user.branch
            user.branch_code
            user.enrollment_no
            user.mobile_no
            user.name
            user.semester
                     */
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                    finish()
                }
            }
            .addOnFailureListener {
                Alerter.create(this)
                    .setTitle("Firestore error")
                    .setIcon(R.drawable.ic_sad)
                    .setBackgroundColor(R.color.error_color)
                    .setText(it.message)
                    .show()
                a_o_v_constraintLayout.visibility = View.GONE
            }

    }
}
