package com.example.github.Search.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.github.Search.Detail.Fragment.SectionPagerAdapter
import com.example.github.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
        const val EXTRA_FOURL = "extra_fourl"
        const val EXTRA_FIURL = "extra_fiurl"
    }

    private lateinit var binding : ActivityDetailBinding

    private lateinit var viewModelView: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val followersUrl = intent.getStringExtra(EXTRA_FOURL)
        val followingUrl = intent.getStringExtra(EXTRA_FIURL)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

       viewModelView = ViewModelProvider(this).get(DetailViewModel::class.java)

        username?.let { viewModelView.setUsersDetail(it) }

        viewModelView.getUsersDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "Followers ${it.followers}"
                    tvFollowing.text = "Following ${it.following} "
                    tvLocation.text = "Location: ${it.location}"
                    tvCompany.text = "Company: ${it.company}"
                    tvRepos.text = "Repository ${it.public_repos}"

                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imgItemPhoto)
                }
            }
        }

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModelView.checkFavorite(id)
            withContext(Dispatchers.Main){
                if ( count !=null) {
                    if (0 < count){
                        binding.toggleFavorite.isChecked = true
                        isChecked = true
                    }else{
                        binding.toggleFavorite.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener{
            isChecked = ! isChecked
            if (isChecked){
                if (username != null) {
                    if (avatarUrl != null) {
                        if (followersUrl != null) {
                            if (followingUrl != null) {
                                viewModelView.addFavorite(username,id, avatarUrl, followersUrl, followingUrl)
                            }
                        }
                    }
                }
            }else{
                viewModelView.removeFavorite(id)
            }
            binding.toggleFavorite.isChecked = isChecked
        }

        val sectionPager = SectionPagerAdapter(this,supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPager
            tabs.setupWithViewPager(viewPager)
        }
    }



}