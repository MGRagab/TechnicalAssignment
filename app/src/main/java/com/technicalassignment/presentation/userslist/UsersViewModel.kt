package com.technicalassignment.presentation.userslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.technicalassignment.domain.model.UpdateUser
import com.technicalassignment.domain.model.User
import com.technicalassignment.domain.repository.UsersRepository
import com.technicalassignment.utils.NetworkException
import com.technicalassignment.utils.mapException
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _dataLive = MutableLiveData<List<User>>()
    val dataLive: LiveData<List<User>>
        get() = _dataLive

    private val _errorLive = MutableLiveData<NetworkException>()
    val errorLive: MutableLiveData<NetworkException>
        get() = _errorLive

    private val _updateDataLive = MutableLiveData<UpdateUser>()
    val updateDataLive: LiveData<UpdateUser>
        get() = _updateDataLive

    fun getUsersList() {
        compositeDisposable.add(usersRepository.getUsersList(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    response?.let {
                        _dataLive.value = response.data.map { it.toUser() }
                    } ?: run {
                        _dataLive.value = emptyList()
                    }
                },
                { error -> _errorLive.value = error.mapException() }
            )
        )
    }

    fun updateUserJob(id: Int, job: String) {

        compositeDisposable.add(usersRepository.updateUser(id, job)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    response?.let {
                        _updateDataLive.value = response.toUpdateUser()
                    }
                },
                { error -> _errorLive.value = error.mapException() }
            )
        )
    }
}