package com.example.registrationservlet.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registrationservlet.model.InsertModel
import com.example.registrationservlet.model.User
import com.example.registrationservlet.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class UserViewModel: ViewModel() {

    private val apiService = RetrofitClient()
    private val disposable = CompositeDisposable()

    var userInsert_response = MutableLiveData<User>();
    var userInsert_response_error = MutableLiveData<Boolean>();

    fun doInsert(model: InsertModel) {

        disposable.add(
            apiService.doInsert(model)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<User>() {
                    override fun onSuccess(model: User) {
                        model?.let {
                            Log.d("Success ", "onSuccess: " +model.errorMessage)
                            userInsert_response.value=model
                        }

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        userInsert_response_error.value = true
                        Log.e("Login-->", e.toString())

                    }

                })
        )
    }

}