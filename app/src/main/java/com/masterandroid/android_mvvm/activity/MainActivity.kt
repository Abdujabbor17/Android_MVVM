package com.masterandroid.android_mvvm.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.masterandroid.android_mvvm.R
import com.masterandroid.android_mvvm.adapter.PostAdapter
import com.masterandroid.android_mvvm.model.Post
import com.masterandroid.android_mvvm.utils.Utils
import com.masterandroid.android_mvvm.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        viewModel.apiPostList()
        viewModel.allPosts.observe(this, Observer {
            refreshAdapter(it)
        })

        Utils.setItemTouchHelperRightToLeft(this,recyclerView)

        val btn_floating: FloatingActionButton = findViewById(R.id.f_button     )
        btn_floating.setOnClickListener {
            callCreateActivity()
        }
    }

    private fun callCreateActivity() {
        val intent = Intent(this,CreateActivity::class.java)
        createLauncher.launch(intent)
    }

    private var createLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            viewModel.apiPostList()
            viewModel.allPosts.observe(this, Observer{
                refreshAdapter(it)
            })
        }
    }

    private fun refreshAdapter(posters: ArrayList<Post>) {
        val adapter = PostAdapter(this, posters)
        recyclerView.setAdapter(adapter)
    }

    fun callUpdateActivity(post: Post?){
        val intent = Intent(this,UpdateActivity::class.java)
        intent.putExtra("post",post)
        updateLauncher.launch(intent)
    }

    var updateLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){

            viewModel.apiPostList()
            viewModel.allPosts.observe(this, Observer{
                refreshAdapter(it)
            })
        }
    }
}