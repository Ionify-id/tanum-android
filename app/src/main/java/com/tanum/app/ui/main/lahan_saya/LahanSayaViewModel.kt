package com.tanum.app.ui.main.lahan_saya

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LahanSayaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is lahan saya Fragment"
    }

    val text: LiveData<String> = _text
}