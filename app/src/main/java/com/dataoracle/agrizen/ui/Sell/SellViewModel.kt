package com.dataoracle.agrizen.ui.Sell

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SellViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Sell Fragment"
    }
    val text: LiveData<String> = _text
}