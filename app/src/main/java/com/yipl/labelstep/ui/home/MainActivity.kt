package com.yipl.labelstep.ui.home

import android.content.Intent
import android.os.Bundle

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.yipl.labelstep.R
import com.yipl.labelstep.ui.base.BaseActivity
import com.yipl.labelstep.ui.AppPreferences
import com.yipl.labelstep.databinding.ActivityMainBinding
import com.yipl.labelstep.ui.post.PostActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var appPreferences: AppPreferences

    override fun isDataBindingEnabled(): Boolean {
        return true
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    fun  getBinding(): ActivityMainBinding {
        return binding as ActivityMainBinding
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appPreferences.example = "Test"

        val mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainActivityViewModel::class.java)

        binding.setLifecycleOwner(this)
        getBinding().viewModel = mainActivityViewModel

        getBinding().recyclerview.adapter = ListsAdapter(this)

        getBinding().buttonGetdata.setOnClickListener{
            Log.e("MainActivity",appPreferences.example)
            mainActivityViewModel.getPosts()
        }

        button_gotopost.setOnClickListener {
            startActivity(Intent(this, PostActivity::class.java))
            finish()
        }
    }
}


