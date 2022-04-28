package com.masterandroid.android_mvvm.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.masterandroid.android_mvvm.R
import com.masterandroid.android_mvvm.model.Post
import com.masterandroid.android_mvvm.viewmodel.UpdateViewModel

class UpdateActivity : AppCompatActivity() {
    lateinit var et_title: EditText
    lateinit var post: Post
    lateinit var viewModel:UpdateViewModel
    lateinit var et_body: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        initViews()
    }


    private fun initViews() {
        viewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)
        et_title = findViewById(R.id.et_title)
        et_body = findViewById(R.id.et_body)
        val btn_cancel = findViewById<Button>(R.id.btn_cancel)
        val btn_post = findViewById<Button>(R.id.btn_update)
        setData()
        val post = intent.getSerializableExtra("post") as Post

        et_title.setText(post.title)
        et_body.setText(post.body)

        btn_cancel.setOnClickListener {
            finish()
        }
        btn_post.setOnClickListener {
            back(post)
        }
    }

    fun back(post: Post){
        post.title = et_title.text.toString()
        post.body = et_body.text.toString()

        viewModel.apiPostUpdate(post).observe(this) {
            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
    private fun setData() {
        post = intent.getSerializableExtra("post") as Post
        et_title.setText(post.title)
        et_body.setText(post.body)
    }


}