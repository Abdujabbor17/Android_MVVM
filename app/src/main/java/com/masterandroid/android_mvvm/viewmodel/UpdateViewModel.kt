package com.masterandroid.android_mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.masterandroid.android_mvvm.model.Post
import com.masterandroid.android_mvvm.networking.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpdateViewModel: ViewModel() {

    val updatePost = MutableLiveData<Post>()

    fun apiPostUpdate(post: Post): LiveData<Post> {
        RetrofitHttp.postService.updatePost(post.id,post)!!.enqueue(object : Callback<Post?> {
            override fun onResponse(call: Call<Post?>, response: Response<Post?>) {
                updatePost.value = response.body()
            }

            override fun onFailure(call: Call<Post?>, t: Throwable) {
                updatePost.value = null
            }

        })
        return updatePost
    }


}