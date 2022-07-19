package com.example.github.Search

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.API.APIResponse
import com.example.github.Search.Detail.DetailActivity
import com.example.github.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter : SearchAdapter


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivitySearchBinding.inflate(layoutInflater).also { binding = it }
        setContentView(binding.root)

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback{
            override fun onItemClicked(data: APIResponse) {
                Intent(this@SearchActivity, DetailActivity::class.java ).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_URL, data.avatar_url)
                    it.putExtra(DetailActivity.EXTRA_FOURL, data.followers_url)
                    it.putExtra(DetailActivity.EXTRA_FIURL, data.following_url)
                    startActivity(it)
                }
            }

        })

        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SearchViewModel::class.java)

        binding.apply {
            rvSearch.layoutManager = LinearLayoutManager(this@SearchActivity)
            rvSearch.setHasFixedSize(true)
            rvSearch.adapter = adapter

            searchView.setOnClickListener {
                viewProgress() }

            searchView.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    viewProgress()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUsers().observe(this) {
            if (it != null) {
                adapter.setSearchList(it)
                progressBar(false)
            }
            else{
                progressBar(true)
            }
        }
    }

    private fun viewProgress(){
        progressBar(true)
        binding.apply {
            val query = searchView.text.toString()
            if (query.isEmpty())
                return progressBar(true)
            with(viewModel) {
                if (query.isEmpty())
                    return progressBar(true)
                setSearchUsers(query)
            }
        }
    }

    private fun progressBar(state : Boolean){
        if (state){
            binding.progressBar.visibility = VISIBLE
        }
        else{
            binding.progressBar.visibility = GONE
        }
    }

}