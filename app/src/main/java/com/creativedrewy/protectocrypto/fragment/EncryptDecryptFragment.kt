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
import com.creativedrewy.protectocrypto.viewmodel.EncryptDecryptViewModel
import com.creativedrewy.protectocrypto.viewmodel.EncryptDecryptViewModelFactory
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
        viewModel.viewState.observe(this, Observer {
            key_input_textview.setText(it.sourceKey)
            data_input_textview.setText(it.sourceData)
            operation_result_textview.setText(it.processingResult)
        })

        encrypt_button.setOnClickListener {
            viewModel.encodeData(key_input_textview.text.toString(), data_input_textview.text.toString())
        }

        decrypt_button.setOnClickListener {
            viewModel.decodeData(key_input_textview.text.toString(), data_input_textview.text.toString())
        }

        arguments?.getParcelable<Intent>(ORIGINATING_INTENT)?.let {
            viewModel.handleIncomingData(it)
        }
    }

    fun handleNewIntent(intent: Intent) {
        viewModel.handleIncomingData(intent)
    }
}