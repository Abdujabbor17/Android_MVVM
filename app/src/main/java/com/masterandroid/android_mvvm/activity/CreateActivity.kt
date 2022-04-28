package com.masterandroid.android_mvvm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.masterandroid.android_mvvm.R
import com.masterandroid.android_mvvm.model.Post
import com.masterandroid.android_mvvm.viewmodel.CreateViewModel

class CreateActivity : AppCompatActivity(){
    var TAG = CreateActivity::class.java.simpleName
    lateinit var et_title: EditText
    lateinit var et_body: EditText
    lateinit var viewModel: CreateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        initViews()
    }
    private fun initViews() {
        viewModel = ViewModelProvider(this).get(CreateViewModel::class.java)
        et_title = findViewById(R.id.et_title)
        et_body = findViewById(R.id.et_body)
        val btn_cancel = findViewById<Button>(R.id.btn_cancel)
        val btn_post = findViewById<Button>(R.id.btn_post)

        btn_cancel.setOnClickListener {
            finish()
        }
        btn_post.setOnClickListener {
            back()
        }
    }

    private fun back(){
        val title:String = et_title.text.toString()
        val body:String = et_body.text.toString()

        val post = Post(1,2,title,body)

        viewModel.apiPostCreate(post).observe(this, Observer {
            val intent = Intent()
            setResult(RESULT_OK,intent)
            finish()
        })
    }
}