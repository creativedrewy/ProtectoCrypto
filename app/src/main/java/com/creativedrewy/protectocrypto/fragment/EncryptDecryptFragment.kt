package com.creativedrewy.protectocrypto.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        fun newInstance() = EncryptDecryptFragment()
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

        encrypt_button.setOnClickListener {
            viewModel.encodeData("key", "value")
        }

        decrypt_button.setOnClickListener {

        }
    }

}