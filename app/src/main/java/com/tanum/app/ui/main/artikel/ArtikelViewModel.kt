package com.tanum.app.ui.main.artikel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArtikelViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is artikel Fragment"
    }
    val text: LiveData<String> = _text
}