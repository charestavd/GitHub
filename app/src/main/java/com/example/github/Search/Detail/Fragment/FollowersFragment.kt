package com.example.submission3.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.R
import com.example.github.Search.Detail.DetailActivity
import com.example.github.Search.Detail.Fragment.FollowersViewModel
import com.example.github.Search.SearchAdapter
import com.example.github.databinding.FragmentFollowBinding


class FollowersFragment : Fragment(R.layout.fragment_follow){

    private var Binding : FragmentFollowBinding? = null
    private val binding get() = Binding!!

    private lateinit var ModelView: FollowersViewModel
    private lateinit var adapter : SearchAdapter
    private lateinit var username : String


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()

        Binding = FragmentFollowBinding.bind(view)

        adapter= SearchAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvSearch.setHasFixedSize(true)
            rvSearch.layoutManager = LinearLayoutManager(activity)
            rvSearch.adapter = adapter
        }
        showProgress(true)
        ModelView = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersViewModel::class.java)
        ModelView.setListFollowers(username)
        ModelView.getListFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setSearchList(it)
                showProgress(false)
            }
        }
    }
    private fun showProgress(state : Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }
        else{
            binding.progressBar.visibility = View.GONE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Binding = null
    }
}