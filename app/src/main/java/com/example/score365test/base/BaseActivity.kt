package com.example.score365test.base

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<T: BaseVM>: AppCompatActivity(){

    abstract fun setVMClass() : Class<T>

    protected val mViewModel by lazy {
        ViewModelProvider(this).get(setVMClass())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportActionBar?.hide();
        super.onCreate(savedInstanceState)
        mViewModel.loadData()
    }

}