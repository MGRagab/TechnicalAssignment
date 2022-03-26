package com.technicalassignment.presentation.userdetails

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make
import com.squareup.picasso.Picasso
import com.technicalassignment.databinding.ActivityUserDetailsBinding
import com.technicalassignment.domain.model.User
import com.technicalassignment.presentation.userslist.UsersViewModel
import com.technicalassignment.utils.LoadingDialog
import com.technicalassignment.utils.Status
import com.technicalassignment.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding

    private lateinit var user: User
    private val usersViewModel: UsersViewModel by viewModels()
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getParcelableExtra<User>("USER")?.let {
            user = it
        } ?: run {
            onBackPressed()
        }
        supportActionBar?.let {
            with(it) {
                title = "User Details"
                elevation = 4f
                setDisplayHomeAsUpEnabled(true)
            }
        }


        usersViewModel.updateDataLive.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.EMPTY -> hideLoading()
                Status.SUCCEED -> {
                    hideLoading()
                    make(binding.root, "User Job Updated", Snackbar.LENGTH_SHORT).show()
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


        binding.userNameTv.text =
            String.format("%s %s", user.first_name, user.last_name)
        binding.userEmailTv.text =
            user.email
        Picasso.get().load(user.avatar).resize(1000, 1000)
            .centerCrop().into(binding.userImage)
        binding.userJobEt.doOnTextChanged { text, start, before, count ->
            text?.let {
                binding.updateJobBtn.isEnabled = it.isNotEmpty()
            }
        }

        binding.updateJobBtn.setOnClickListener {
            hideKeyboard()
            usersViewModel.updateUserJob(user.id, binding.userJobEt.text.toString())
        }

    }

    private fun showLoading() {
        loadingDialog = LoadingDialog(this)
        loadingDialog.startLoadingDialog()
    }

    private fun hideLoading() {
        if (this::loadingDialog.isInitialized) {
            loadingDialog.dismissLoadingDialog()
        }
    }

    override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}