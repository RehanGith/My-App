package com.example.myapp

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImageViewModel: ViewModel() {
    private var _uri = MutableLiveData<Uri>(null)
    val uri get() = _uri

    fun setUri(uri: Uri) {
       this.uri.value = uri
    }


}