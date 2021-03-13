package com.codewithjun.findit.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.codewithjun.findit.repository.SearchActivityRepository

class SearchActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SearchActivityRepository(application)
    val showProgress: LiveData<Boolean>
//    val resultList : LiveData<List<Result>>

    init {
        this.showProgress = repository.showProgress
//        this.resultList = repository.locationList
    }


    fun changeState() {
        repository.changeState()
    }

    fun searchLocation(searchString: String) {
        //repository.searchLocation(searchString)
    }

}