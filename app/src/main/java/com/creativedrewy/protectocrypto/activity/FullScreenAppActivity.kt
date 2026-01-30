package com.creativedrewy.protectocrypto.activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.creativedrewy.protectocrypto.ui.EncryptDecryptScreen
import com.creativedrewy.protectocrypto.ui.theme.ProtectoCryptoTheme
import com.creativedrewy.protectocrypto.viewmodel.EncryptDecryptViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScreenAppActivity : ComponentActivity() {

    private var viewModel: EncryptDecryptViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable activity transitions before calling super.onCreate
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        // Set up slide from bottom transition
        val slideUp = Slide(Gravity.BOTTOM).apply {
            duration = 300
            excludeTarget(R.id.statusBarBackground, true)
            excludeTarget(R.id.navigationBarBackground, true)
        }
        window.enterTransition = slideUp
        window.exitTransition = slideUp

        super.onCreate(savedInstanceState)

        setContent {
            ProtectoCryptoTheme {
                val vm: EncryptDecryptViewModel = hiltViewModel()
                viewModel = vm

                // Handle incoming intent on first creation
                if (savedInstanceState == null) {
                    vm.handleIncomingData(intent)
                }

                EncryptDecryptScreen(
                    viewModel = vm,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        viewModel?.handleIncomingData(intent)
    }

    override fun onStop() {
        super.onStop()
        viewModel?.clearCacheIfNeeded()
    }

    override fun finish() {
        // Use finishAfterTransition to reverse the slide animation when closing
        finishAfterTransition()
    }
}