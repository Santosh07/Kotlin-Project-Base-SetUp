package com.yipl.labelstep.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.AndroidInjection


abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var binding: ViewDataBinding

    abstract fun getLayout(): Int


    open fun isDataBindingEnabled(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        if (!isDataBindingEnabled()) {
            setContentView(getLayout())
        } else {
            binding = DataBindingUtil.setContentView(this, getLayout())
        }
    }

}