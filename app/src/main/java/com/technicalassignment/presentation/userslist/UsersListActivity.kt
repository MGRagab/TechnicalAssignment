package com.technicalassignment.presentation.userslist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.technicalassignment.databinding.ActivityUsersListBinding
import com.technicalassignment.domain.model.User
import com.technicalassignment.presentation.userdetails.UserDetailsActivity
import com.technicalassignment.presentation.userslist.adapter.UsersListAdapter
import com.technicalassignment.utils.Status
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
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.EMPTY -> hideLoading()
                Status.SUCCEED -> {
                    hideLoading()
                    it.data?.let { users ->
                        mAdapter.submitItems(users)
                    }
                }
                Status.FAILED -> {
                    hideLoading()
                    it.error?.printStackTrace()
                }
                Status.NO_CONNECTION -> {
                    hideLoading()
                }
            }
        }

        usersViewModel.getUsersList()

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
}