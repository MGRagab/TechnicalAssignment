package com.technicalassignment.presentation.userslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technicalassignment.databinding.UserListItemBinding
import com.technicalassignment.domain.model.User

class UsersListAdapter(val userItemClickListener: UserItemClickListener) :
    RecyclerView.Adapter<UsersListAdapter.UserViewHolder>() {
    private var items: ArrayList<User> = arrayListOf()


    fun submitItems(users: List<User>) {
        val diffCallback = UsersListDiffCallback(this.items, users)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(users)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class UserViewHolder(private val userListItemBinding: UserListItemBinding) :
        RecyclerView.ViewHolder(
            userListItemBinding.root
        ) {
        fun bind(user: User) {
            userListItemBinding.userNameTv.text =
                String.format("%s %s", user.first_name, user.last_name)
            userListItemBinding.userEmailTv.text =
                user.email
            Picasso.get().load(user.avatar).resize(1000, 1000)
                .centerCrop().into(userListItemBinding.userImage)
            userListItemBinding.root.setOnClickListener {
                userItemClickListener.onUserItemClickListener(user)
            }
        }
    }

    interface UserItemClickListener {
        fun onUserItemClickListener(user: User)
    }
}