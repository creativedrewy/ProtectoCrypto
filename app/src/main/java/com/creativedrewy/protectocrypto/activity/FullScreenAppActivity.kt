package com.creativedrewy.protectocrypto.activity

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.creativedrewy.protectocrypto.R
import com.creativedrewy.protectocrypto.fragment.EncryptDecryptFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScreenAppActivity : AppCompatActivity() {

    private var sheetFragment: EncryptDecryptFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_activity)

        createAndRevealSheet()
    }

    override fun onStart() {
        super.onStart()

        if (sheetFragment == null) {
            createAndRevealSheet()
        } else {
            sheetFragment?.let {
                val sheetRef = it.view?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                sheetRef?.let {
                    BottomSheetBehavior.from(sheetRef).state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        sheetFragment = null
    }

    private fun createAndRevealSheet() {
        sheetFragment = EncryptDecryptFragment.newInstance()
        sheetFragment?.show(supportFragmentManager, "ui")
    }
}