package com.asoit.test.auth.signin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.asoit.test.R
import com.asoit.test.auth.signup.SignupFragment
import com.asoit.test.faculty.FacultyController
import com.asoit.test.student.STUDENT
import com.asoit.test.student.StudentHomeActivity
import com.asoit.test.user.FacultyInfoModel
import com.asoit.test.user.StudentInfoModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pixplicity.easyprefs.library.Prefs


/**
 * A simple [Fragment] subclass.
 */
class SigninFragment : Fragment() {

    lateinit var username:EditText
    lateinit var password:EditText
    lateinit var signIn:Button
    lateinit var fireAuth:FirebaseAuth
    lateinit var fireStore:FirebaseFirestore
    lateinit var parentLayout:FrameLayout
    lateinit var createAccountTv:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signin, container, false)
       username = view.findViewById(R.id.et_username)
        password = view.findViewById(R.id.et_password)
        signIn = view.findViewById(R.id.btn_signin)
        fireAuth = FirebaseAuth.getInstance()
        fireStore = FirebaseFirestore.getInstance()
        parentLayout = requireActivity().findViewById(R.id.parent_framelayout)
        createAccountTv = view.findViewById(R.id.create_account_tv)
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            requireActivity().window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAccountTv.setOnClickListener {
            onCreateAccountClick()
        }
        signIn.setOnClickListener {
            fireAuth.signInWithEmailAndPassword(username.text.toString(),password.text.toString()).addOnCompleteListener(
                OnCompleteListener {
                    if (it.isSuccessful)
                    {
                        var intent:Intent? = null
                        fireStore.collection("users")
                            .document(fireAuth.currentUser!!.uid)
                            .get()
                            .addOnSuccessListener {
                                val user = it.toObject(StudentInfoModel::class.java)
                                if (user?.role =="student")
                                {   Prefs.putBoolean(STUDENT.USERLOGEDIN,true)
                                    Prefs.putString(STUDENT.STUDENT_NAME,user.name)
                                    Prefs.putString(STUDENT.STUDENT_ID,user.user_id)
                                    Prefs.putInt(STUDENT.STUDENT_SEMESTER, user.semester!!)
                                    Prefs.putInt(STUDENT.STUDENT_BRANCH_CODE, user.branch_code!!)
                                    Prefs.putString(STUDENT.STUDENT_ENROLLMENT,user.enrollment_no)
                                     intent = Intent(this.requireActivity(),StudentHomeActivity::class.java)
                                    intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                if (user?.role =="faculty")
                                {
                                        Prefs.putBoolean(STUDENT.USERLOGEDIN,true)
                                        Prefs.putString(STUDENT.FACULTY_NAME,user.name)
                                        Prefs.putString(STUDENT.FACULTY_ID,user.user_id)
                                     intent = Intent(this.requireActivity(),FacultyController::class.java)
                                    intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

                                }
                                startActivity(intent)
                                this.activity?.finish()

                            }
                            .addOnFailureListener {
                                Toast.makeText(this.requireContext(),it.message,Toast.LENGTH_SHORT).show()
                            }


                    }
                    else
                    {
                        Toast.makeText(this.requireContext(),it.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }

    fun onLoginClick(View: View?) {


    }

    fun onCreateAccountClick()
    {
        setFragment(SignupFragment())
    }

    fun setFragment(fragment: Fragment)
    {
        val fragment_trans = activity?.supportFragmentManager!!.beginTransaction()
        fragment_trans.replace(parentLayout.id,fragment)
        fragment_trans.commit()
    }



}
