package com.yipl.labelstep.ui.home

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.provider.ContactsContract

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.aabumu.genericadapter.usingbinding.GenericAdapter
import com.aabumu.genericadapter.usingbinding.setUpBinding
import com.google.android.material.snackbar.Snackbar
import com.yipl.labelstep.R
import com.yipl.labelstep.ui.base.BaseActivity
import com.yipl.labelstep.ui.AppPreferences
import com.yipl.labelstep.databinding.ActivityMainBinding
import com.yipl.labelstep.databinding.LayoutItemUserBinding
import com.yipl.labelstep.db.model.Post
import com.yipl.labelstep.ui.post.PostActivity
import com.yipl.labelstep.ui.post.PostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appPreferences.example = "Test"

        val mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainActivityViewModel::class.java)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.setLifecycleOwner(this)
        binding.viewModel = mainActivityViewModel

        val adapter = binding.recyclerview.setUpBinding<Post, LayoutItemUserBinding>(
                R.layout.layout_item_user,
                { post -> this.post = post })

        binding.buttonGetdata.setOnClickListener{
            Log.e("MainActivity",appPreferences.example)
            mainActivityViewModel.getPosts()
        }

        button_gotopost.setOnClickListener {
            startActivity(Intent(this, PostActivity::class.java))
            finish()
        }

        mainActivityViewModel.posts.observe(this, Observer {
            adapter.setItem(it)
        })

        mainActivityViewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        mainActivityViewModel.uiState.observe(this, Observer {
            when (it) {
                UIState.LOADING -> {
                    binding.recyclerview.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                }
                UIState.SUCCESS ->  {
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                }
                UIState.ERROR -> {
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        })
    }
}


