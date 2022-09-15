package com.example.projekat_septembar.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekat_septembar.data.models.Car
import com.example.projekat_septembar.data.repositories.CarRepository
import com.example.projekat_septembar.presentation.contract.CarContract
import com.example.projekat_septembar.presentation.view.states.CarState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CarViewModel  (private val carRepository: CarRepository ) : ViewModel(), CarContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val carState: MutableLiveData<CarState> = MutableLiveData()
    override val paginationList: MutableLiveData<List<Car>> = MutableLiveData()
    override var allCars: List<Car> = arrayListOf()
    private var size = 0


    override fun fetchAllCarsFromServer() {
        val subscription = carRepository
            .fetchAllFromServer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    carState.value = CarState.Success(it)
                },
                {
                    carState.value = CarState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun loadPagination(initial: Boolean) {
        val tmpArrayList: ArrayList<Car> = arrayListOf()
        when {
            initial -> size = 9
            size + 10 <= allCars.size -> size += 10
            else -> size += (allCars.size - size)
        }

        println("counter $size list ${allCars.size}")

        if (size <= allCars.size) {
            for (i in 0 until size)
                tmpArrayList.add(allCars[i])
            paginationList.value = tmpArrayList
        }
    }

}