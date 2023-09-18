package com.example.jetpack

import android.os.Bundle
import android.util.Log

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpack.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.Converter.Factory
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://64fdd73c596493f7af7ea861.mockapi.io/User/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // create Api service
        val apiService = retrofit.create(Api::class.java)
        var callAllPosts = apiService.getPosts()
        var posts= mutableListOf<Post>()
        callAllPosts.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {


                for (post in response.body()!!){
                    posts.add(post)

                }

            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {

            }

        })
        setContent {
            EmailPasswordListApp(posts)
        }
    }
}

@Composable
fun EmailPasswordList(posts: List<Post>) {
    var showList by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        item {
            Button(
                onClick = { showList = !showList },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (showList) "Hide List" else "Show List")
            }
        }

        if (showList) {
            items(posts) { post ->
                EmailPasswordCard(emailPassword = "Email: ${post.email}, Password: ${post.password}")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

    }
}

@Composable
fun EmailPasswordCard(emailPassword: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = Color.White,
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = emailPassword,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.body1
            )
        }
    }
}



@Composable
fun EmailPasswordListApp(posts: List<Post>) {
    MaterialTheme {
        EmailPasswordList(posts)
    }
}
