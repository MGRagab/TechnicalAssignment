package com.technicalassignment.presentation.userslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.technicalassignment.domain.model.UpdateUser
import com.technicalassignment.utils.NoNetworkException
import com.technicalassignment.utils.Response
import com.technicalassignment.domain.model.User
import com.technicalassignment.domain.repository.UsersRepository
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

    private val _dataLive = MutableLiveData<Response<List<User>>>()
    val dataLive: LiveData<Response<List<User>>>
        get() = _dataLive

    private val _updateDataLive = MutableLiveData<Response<UpdateUser>>()
    val updateDataLive: LiveData<Response<UpdateUser>>
        get() = _updateDataLive

    fun getUsersList() {
        compositeDisposable.add(
            usersRepository.getUsersList(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _dataLive.value = Response.loading() }
                .subscribe({ res ->
                    _dataLive.value =
                        if (res.data.isEmpty()) Response.empty() else Response.succeed(res.data.map { it.toUser() })
                }, { t: Throwable ->
                    _dataLive.value = when (t) {
                        is NoNetworkException -> Response.networkLost()
                        else -> Response.error(t)
                    }
                })
        )
    }

    fun updateUserJob (id : Int , job : String){
        compositeDisposable.add(
            usersRepository.updateUser(id , job)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _updateDataLive.value = Response.loading() }
                .subscribe({ res ->
                    _updateDataLive.value =
                        if (res == null) Response.empty() else Response.succeed(res.toUpdateUser())
                }, { t: Throwable ->
                    _updateDataLive.value = when (t) {
                        is NoNetworkException -> Response.networkLost()
                        else -> Response.error(t)
                    }
                })
        )
    }
}