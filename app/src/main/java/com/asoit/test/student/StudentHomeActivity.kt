package com.asoit.test.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import com.asoit.test.student.home.HomeFragment
import com.asoit.test.user.StudentInfoModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_student_home.*

class StudentHomeActivity : AppCompatActivity() {



    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firestore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_student_home)
        navController = findNavController(R.id.navHostFragment)
        val actionbar = findViewById<Toolbar>(R.id.toolbar)
        //setSupportActionBar(actionbar)
        NavigationUI.setupWithNavController(actionbar,navController)
       supportActionBar?.elevation = 10f
      bottom_nav_bar.setupWithNavController(navController)

     //   NavigationUI.setupWithNavController(btmnav,navController)




    }

    fun setFragment(fragment: Fragment)
    {
        val fragment_trans = supportFragmentManager.beginTransaction()
       // fragment_trans.replace(navHost.id,fragment)
        fragment_trans.commit()
    }
}
