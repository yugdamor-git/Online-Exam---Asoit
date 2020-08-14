package com.asoit.test.faculty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_faculty_controller.*

class FacultyController : AppCompatActivity() {

    lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_controller)

        navController = findNavController(R.id.faculty_navHostFragment)
        val actionbar = findViewById<Toolbar>(R.id.faculty_toolbar)

        NavigationUI.setupWithNavController(actionbar,navController)

        faculty_bottom_nav_bar.setupWithNavController(navController)


       // val firestore = FirebaseFirestore.getInstance()
        /*
         val ref = firestore.collection("test")
            .document()
        val d = ResultInfoClass()
        d.document_id = ref.id
        d.user_name = "damor yogesh r"
        d.user_enrollment = "171200107004"
        d.submited = false
        d.started = false
        d.score = 0
        d.user_id = "PXG7F2D4zLdGS2fcD2TImWjLcGM2"
        d.test_id ="PXG7F2D4zLdGS2fcD2TImWjLcG"
        d.published = true
        d.created_date = Timestamp.now()
        d.test_date = Timestamp.now()
        d.CountSelected = 0
        d.branch = "ce"
        d.semester =6
        d.subject ="ae"
        d.total_questions = 10
        d.time_left = 10000000
        d.valid_till = Timestamp.now()

        ref.set(d).addOnSuccessListener {

        }
            .addOnFailureListener {
                Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
            }
         */

    }


}
