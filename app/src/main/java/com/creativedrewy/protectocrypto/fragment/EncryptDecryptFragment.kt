package com.creativedrewy.protectocrypto.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.creativedrewy.protectocrypto.R
import com.creativedrewy.protectocrypto.viewmodel.MainViewModel
import com.creativedrewy.protectocrypto.viewmodel.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EncryptDecryptFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    companion object {
        fun newInstance() =
            EncryptDecryptFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.encrypt_decrypt_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

}