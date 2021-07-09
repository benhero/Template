package com.ben.template.function.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel
 *
 * @author Benhero
 * @date   2019/10/29
 */
class MyViewModel : ViewModel() {
    val num = MutableLiveData(0)
}