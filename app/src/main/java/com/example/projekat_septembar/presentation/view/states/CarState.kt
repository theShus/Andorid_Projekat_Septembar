package com.example.projekat_septembar.presentation.view.states


import com.example.projekat_septembar.data.models.Car
import com.example.projekat_septembar.data.models.serverResponses.UserDetails

sealed class CarState {
    object DataFetched: CarState()
    object Loading: CarState()
    data class Success(val cars: List<Car>): CarState()
    data class Saved(val message: String): CarState()
    data class Error(val message: String): CarState()
    data class Contacted(val message: String): CarState()

}