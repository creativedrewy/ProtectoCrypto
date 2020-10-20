package com.creativedrewy.protectocrypto.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.creativedrewy.protectocrypto.R
import com.creativedrewy.protectocrypto.fragment.EncryptDecryptFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScreenAppActivity : AppCompatActivity() {

    private var sheetFragment: EncryptDecryptFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_activity)

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.BOTTOM)

        if (savedInstanceState == null) {
            sheetFragment = EncryptDecryptFragment.newInstance()
            sheetFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, it)
                    .commitNow()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.let {
            sheetFragment?.handleNewIntent(it)
        }
    }
}