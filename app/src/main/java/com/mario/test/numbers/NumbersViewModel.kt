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

    fun addNumbers(number: Int) {

        launch {
            io({
                e(this)
                numbersLiveData.postValue(Resource.error(AppException(this)))
            }) {

                repository.addNumbers(number)
                val numbers = mutableListOf<Int>()
                //note these two functions can be combined into one at the repository level to abstract the source of data
                numbers.addAll(repository.getNumbers())
                numbers.addAll(repository.getDefaultNumbers())

                numbersLiveData.postValue(Resource.success(numbers))

            }
        }
    }
}