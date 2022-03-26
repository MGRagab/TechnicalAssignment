package com.technicalassignment.presentation.userslist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.technicalassignment.R
import com.technicalassignment.databinding.ActivityUsersListBinding
import com.technicalassignment.domain.model.User
import com.technicalassignment.presentation.userdetails.UserDetailsActivity
import com.technicalassignment.presentation.userslist.adapter.UsersListAdapter
import com.technicalassignment.utils.showNetworkError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersListActivity : AppCompatActivity(), UsersListAdapter.UserItemClickListener {
    private val usersViewModel: UsersViewModel by viewModels()
    private lateinit var binding: ActivityUsersListBinding
    private val mAdapter by lazy {
        UsersListAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersListBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.let {
            with(it) {
                title = "Users"
            }
        }
        binding.usersRv.apply {
            adapter = mAdapter
        }
        usersViewModel.dataLive.observe(this) {
            hideLoading()
            mAdapter.submitItems(it)
        }
        usersViewModel.errorLive.observe(this) {
            hideLoading()
            showNetworkError(binding.root, it)
        }
        showLoading()
        usersViewModel.getUsersList()


    }

    private fun apply404Test() {
        showLoading()
        usersViewModel.test404()
    }

    private fun showLoading() {
        binding.loadingPb.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loadingPb.visibility = View.GONE
    }

    override fun onUserItemClickListener(user: User) {
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("USER", user)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.test_404 -> {
                apply404Test()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}