package com.example.github

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github.Search.Favorite.MenuActivity
import com.example.github.Search.SearchActivity
import com.example.github.darkMode.ModeViewModel
import com.example.github.darkMode.SettingPreferences
import com.example.github.darkMode.ViewModelFactory
import com.example.github.databinding.ActivityMainBinding
import com.example.submission1.Avatars
import com.example.submission1.ListAvatarAdapter
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivityMainBinding

    private lateinit var rvAvatars: RecyclerView
    private val list = ArrayList<Avatars>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ModeViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }

        rvAvatars = findViewById(R.id.rv_avatars)
        rvAvatars.setHasFixedSize(true)

        list.addAll(listAvatars)
        showRecyclerList()
    }

    private val listAvatars : ArrayList<Avatars>
        @SuppressLint("Recycle")
        get() {
            val dataPhoto = resources.obtainTypedArray(R.array.avatar)
            val dataUsername = resources.getStringArray(R.array.username)
            val dataName = resources.getStringArray(R.array.name)
            val dataFollowers = resources.getStringArray(R.array.followers)
            val dataFollowing = resources.getStringArray(R.array.following)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataRepository = resources.getStringArray(R.array.repository)
            val listAvatars = ArrayList<Avatars>()
            for (i in dataUsername.indices) {
                val avatar = Avatars(
                    dataUsername[i],
                    dataName[i],
                    dataFollowers[i],
                    dataFollowing[i],
                    dataLocation[i],
                    dataCompany[i],
                    dataRepository[i],
                    dataPhoto.getResourceId(i, -1)
                )
                listAvatars.add(avatar)
            }
            return  listAvatars
        }

    private fun showRecyclerList () {
        rvAvatars.layoutManager = LinearLayoutManager(this)
        val listAvatarAdapter = ListAvatarAdapter(list)
        rvAvatars.adapter = listAvatarAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                val search = Intent(this, SearchActivity::class.java)
                startActivity(search)
                return true
            }
            R.id.menu2 -> {
                val i = Intent(this, MenuActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }
}