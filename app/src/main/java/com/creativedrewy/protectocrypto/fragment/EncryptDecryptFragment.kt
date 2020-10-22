package com.creativedrewy.protectocrypto.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.creativedrewy.protectocrypto.R
import com.creativedrewy.protectocrypto.viewmodel.DataProcessed
import com.creativedrewy.protectocrypto.viewmodel.EncryptDecryptViewModel
import com.creativedrewy.protectocrypto.viewmodel.EncryptDecryptViewModelFactory
import com.creativedrewy.protectocrypto.viewmodel.ErrorState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.encrypt_decrypt_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class EncryptDecryptFragment : Fragment() {

    companion object {
        const val ORIGINATING_INTENT: String = "incomingIntent"

        fun newInstance(intent: Intent? = null): EncryptDecryptFragment {
            val fragment = EncryptDecryptFragment()

            intent?.let {
                val args = Bundle()
                args.putParcelable(ORIGINATING_INTENT, it)
                fragment.arguments = args
            }

            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: EncryptDecryptViewModelFactory

    private lateinit var viewModel: EncryptDecryptViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.encrypt_decrypt_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(EncryptDecryptViewModel::class.java)
        viewModel.viewState.observe(this, Observer { state ->
            when (state) {
                is DataProcessed -> {
                    key_input_textview.setText(state.sourceKey)
                    data_input_textview.setText(state.sourceData)
                    operation_result_textview.setText(state.processingResult)
                }
                is ErrorState -> {
                    Snackbar.make(main, state.message, Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        encrypt_button.setOnClickListener {
            validateForm {
                viewModel.encodeData(key_input_textview.text.toString(), data_input_textview.text.toString())
            }
        }

        decrypt_button.setOnClickListener {
            validateForm {
                viewModel.decodeData(key_input_textview.text.toString(), data_input_textview.text.toString())
            }
        }

        clear_everything_button.setOnClickListener {
            viewModel.clearEverything()
        }

        arguments?.getParcelable<Intent>(ORIGINATING_INTENT)?.let {
            viewModel.handleIncomingData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.clearCacheIfNeeded()
    }

    fun handleNewIntent(intent: Intent) {
        viewModel.handleIncomingData(intent)
    }

    private fun validateForm(onValidated: () -> Unit) {
        if (key_input_textview.text.isNotEmpty() && data_input_textview.text.isNotEmpty()) {
            onValidated()
        }
    }
}