package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var toDoAdapter:ToDoAdapter
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible=true
            val response=try{
                RetrofitInstance.api.getTodos()
            }catch (e:IOException){
                Log.d("utk","$e.message")
                return@launchWhenCreated
            }catch(e:HttpException){
                Log.d("utk","http exception")
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body()!=null){
                toDoAdapter.todos= response.body()!!
            }else{
                Log.d("utk","response unsuccessful")
            }
            binding.progressBar.isVisible=false
        }
    }

    private fun setUpRecyclerView()=binding.rvTodos.apply{
        toDoAdapter=ToDoAdapter()
        adapter=toDoAdapter
        layoutManager=LinearLayoutManager(this@MainActivity)
    }
}