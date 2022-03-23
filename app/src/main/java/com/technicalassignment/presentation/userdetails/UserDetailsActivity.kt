package com.technicalassignment.presentation.userdetails

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.technicalassignment.databinding.ActivityUserDetailsBinding
import com.technicalassignment.domain.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding

    private lateinit var user: User
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

        binding.userNameTv.text =
            String.format("%s %s", user.first_name, user.last_name)
        binding.userEmailTv.text =
            user.email
        Picasso.get().load(user.avatar).resize(1000, 1000)
            .centerCrop().into(binding.userImage)

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