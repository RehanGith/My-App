package com.example.myapp

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImageViewModel: ViewModel() {
    private var _uri = MutableLiveData<Uri>(null)
    val uri get() = _uri

    fun setUri(uri: Uri) {
        Log.d("uri", uri.toString())
       this.uri.value = uri
        Log.d("uri", this.uri.value.toString())
    }


}