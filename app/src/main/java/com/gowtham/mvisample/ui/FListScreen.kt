package com.gowtham.mvisample.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gowtham.mvisample.R
import com.gowtham.mvisample.adpters.AdPassenger
import com.gowtham.mvisample.databinding.FListScreenBinding
import com.gowtham.mvisample.models.ApiResult
import com.gowtham.mvisample.models.Passenger
import com.gowtham.mvisample.util.MainState
import com.gowtham.mvisample.util.gone
import com.gowtham.mvisample.util.show
import com.gowtham.mvisample.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

@AndroidEntryPoint
class FListScreen : Fragment(R.layout.f_list_screen) {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FListScreenBinding

    private lateinit var listOfPassenger: ArrayList<Passenger>

    private val adPassenger: AdPassenger by lazy {
        AdPassenger()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FListScreenBinding.bind(view)

        setDataInView()
        subscribeObservers()
    }

    private fun subscribeObservers() {

        lifecycleScope.launch {
            viewModel.state.collect {

                when(it){
                    MainState.Idle -> {}
                    MainState.Loading -> {
                        binding.progressView.show()
                    }
                    is MainState.Success -> {
                        binding.progressView.gone()
//                        val data=it.data as Response<ApiResult>
//                        listOfPassenger.addAll(data.body()!!.passeners)
                    }
                    is MainState.Failure -> {
                        binding.progressView.gone()
                    }
                }
            }
        }
    }

    private fun setDataInView() {
        listOfPassenger=ArrayList()
        binding.listView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adPassenger
        }
    }

}