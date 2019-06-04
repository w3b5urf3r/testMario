package com.mario.test.numbers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mario.test.base.BaseViewModel
import com.mario.test.util.architecture.AppException
import com.mario.test.util.architecture.Resource
import kotlinx.coroutines.launch
import timber.log.Timber.e

class NumbersViewModel : BaseViewModel() {
    private val numbersLiveData: MutableLiveData<Resource<List<Int>>> = MutableLiveData()
    internal val numbers: LiveData<Resource<List<Int>>> = numbersLiveData

    fun getDefaultNumbers() {
        numbersLiveData.postValue(Resource.loading(numbers.value?.data.orEmpty()))

        launch {
            io({
                e(this)
                numbersLiveData.postValue(Resource.error(AppException(this)))
            }) {
                val defaultNumbers = repository.getDefaultNumbers()
                numbersLiveData.postValue(Resource.success(defaultNumbers))

            }
        }

    }
}