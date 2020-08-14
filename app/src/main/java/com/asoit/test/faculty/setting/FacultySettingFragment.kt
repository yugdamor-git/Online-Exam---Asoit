package com.asoit.test.faculty.setting

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
import com.minibugdev.sheetselection.SheetSelection
import com.minibugdev.sheetselection.SheetSelectionItem
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_add_test.*
import kotlinx.android.synthetic.main.fragment_faculty_setting.*
import kotlinx.android.synthetic.main.fragment_student_setting.*

/**
 * A simple [Fragment] subclass.
 */
class FacultySettingFragment : Fragment() {

    lateinit var firestore: FirebaseFirestore
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_faculty_setting, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        faculty_logout.setOnClickListener {
            val list = listOf<SheetSelectionItem>(
                SheetSelectionItem("1","yes"),
                SheetSelectionItem("2","no")
            )

            SheetSelection.Builder(this.requireContext()    )
                .title("Are you sure ?")
                .items(list)
                .selectedPosition(0)
                .showDraggedIndicator(true)
                .searchEnabled(false)
                .onItemClickListener { item, position ->
                    // DO SOMETHING

                    if (position == 0)
                    {

                        val intent = Intent(this.requireActivity(), SplashActivity::class.java)
                        Prefs.clear()
                        firestore.clearPersistence().addOnSuccessListener {  }
                        firebaseAuth.signOut()
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        this.activity?.finish()
                    }
                    if (position == 1)
                    {

                    }
                }
                .show()






        }



    }

}
