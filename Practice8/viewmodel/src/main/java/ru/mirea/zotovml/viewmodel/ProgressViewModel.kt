package ru.mirea.zotovml.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProgressViewModel : ViewModel() {
    companion object {
        private val isShowProgress:MutableLiveData<Boolean?> = MutableLiveData()
    }

    fun showProgress() {
        isShowProgress.postValue(true)
        Handler().postDelayed(Runnable { isShowProgress.postValue(false) }, 10000)
    }

    // Возвращает состояние прогресс?
    fun getProgressState(): MutableLiveData<Boolean?>? {
        return isShowProgress
    }
}