package com.asoit.test.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.asoit.test.R
import com.asoit.test.auth.signin.SigninFragment

class LoginActivity : AppCompatActivity() {

    lateinit var parentLayout:FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        parentLayout = findViewById(R.id.parent_framelayout)
        setFragment(SigninFragment())
    }

    fun setFragment(fragment: Fragment)
    {
        val fragment_trans = supportFragmentManager.beginTransaction()
        fragment_trans.replace(parentLayout.id,fragment)
        fragment_trans.commit()
    }
}
