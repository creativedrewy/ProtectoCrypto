package com.creativedrewy.protectocrypto.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.creativedrewy.protectocrypto.R
import com.creativedrewy.protectocrypto.fragment.EncryptDecryptFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScreenAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_activity)

        //if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, EncryptDecryptFragment.newInstance())
//                    .commitNow()

            val fragment = EncryptDecryptFragment.newInstance()
            fragment.show(supportFragmentManager, "ui")
        //}

    }
}