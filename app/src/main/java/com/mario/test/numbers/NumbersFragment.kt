package com.mario.test.numbers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.mario.test.R
import com.mario.test.base.BaseFragment
import com.mario.test.databinding.FragmentNumbersBinding
import com.mario.test.util.architecture.Resource.Status.*
import com.mario.test.util.architecture.observe
import com.mario.test.util.architecture.withViewModelActivity
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_numbers.*
import org.jetbrains.anko.toast

class NumbersFragment : BaseFragment<NumbersViewModel>() {

    private lateinit var binding: FragmentNumbersBinding
    override fun initViewModel(): NumbersViewModel = withViewModelActivity()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_numbers, container, false)
        return binding.root
    }

    override fun initUi() {

        compositeDisposable += et_number.textChanges()
                .filter { it.isNotEmpty() }
                .switchMap { number ->
                    btn_press_me.clicks().map { number.toString().toInt() } //assumiong
                }
                .subscribe {
                    viewModel.addNumbers(it)
                }
    }

    override val starter: NumbersViewModel.() -> Unit = { viewModel.getDefaultNumbers() }
    override val render: NumbersViewModel.() -> Unit = {
        observe(numbers) {
            when (it?.status) {

                SUCCESS -> {
                    binding.numbers = it.data
                    binding.average = it.data?.average().toString()
                }
                ERROR -> activity?.toast("error super mario") //this is for fun
                LOADING -> activity?.toast(R.string.loading)
            }
        }
    }
}