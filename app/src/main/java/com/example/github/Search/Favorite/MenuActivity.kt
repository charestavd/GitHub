package com.example.github.Search.Favorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.API.APIResponse
import com.example.github.Search.Detail.DetailActivity
import com.example.github.Search.SearchAdapter
import com.example.github.databinding.ActivityMenuBinding
import com.example.submission3.model.FavoriteUser

class MenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMenuBinding
    private lateinit var adapter : SearchAdapter
    private lateinit var viewModelView: FavoriteViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()

        viewModelView = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback{
            override fun onItemClicked(data: APIResponse) {
                Intent(this@MenuActivity, DetailActivity::class.java ).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_URL, data.avatar_url)
                    it.putExtra(DetailActivity.EXTRA_FOURL, data.followers_url)
                    it.putExtra(DetailActivity.EXTRA_FIURL, data.following_url)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvSearch.setHasFixedSize(true)
            rvSearch.layoutManager = LinearLayoutManager(this@MenuActivity)
            rvSearch.adapter = adapter
        }

        viewModelView.getFavorite()?.observe(this) {
            if (it != null) {
                val list = map(it)
                adapter.setSearchList(list)
            }else
                showToast("tidak ditemukan data")
        }

    }

    private fun map(users: List<FavoriteUser>): ArrayList <APIResponse>{
        val listUsers = ArrayList<APIResponse>()
        for (user in users){
            val userMapped = APIResponse(
                user.login,
                user.id,
                user.avatar_url,
                user.followers_url,
                user.following_url
            )
            listUsers.add(userMapped)
        }
        return  listUsers
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}