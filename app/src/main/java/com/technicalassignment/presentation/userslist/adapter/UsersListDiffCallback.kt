package com.technicalassignment.presentation.userslist.adapter

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.technicalassignment.domain.model.User

/**
 * Created By MGRagab 23/11/2021
 */
class UsersListDiffCallback(
    private val oldList: List<User>,
    private val newList: List<User>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_, value, name) = oldList[oldPosition]
        val (_, value1, name1) = newList[newPosition]

        return name == name1 && value == value1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}