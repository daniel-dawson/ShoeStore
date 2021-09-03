package com.udacity.shoestore.screens.shoeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import timber.log.Timber

class ShoeListViewModel : ViewModel() {

    private val _shoeList = MutableLiveData<MutableList<Shoe>>(mutableListOf())
    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean>
        get() = _isError

    private val _isShoeSaved = MutableLiveData(false)
    val isShoeSaved: LiveData<Boolean>
        get() = _isShoeSaved

    init {
        val newShoe = Shoe("Test Shoe", 10.5, "Shoemaker", "A shoe")
        saveShoe(newShoe)
        setIsShoeSaved(false)
    }

    fun validateShoe(shoeCandidate: Shoe) {
        if (shoeCandidate.name.isEmpty() ||
            shoeCandidate.size < 0 ||
            shoeCandidate.company.isEmpty() ||
            shoeCandidate.description.isEmpty()
        ) {
            Timber.d(shoeCandidate.size.toString())
            setError(true)
        } else {
            saveShoe(shoeCandidate)
        }
    }

    fun saveShoe(newShoe: Shoe) {
        _shoeList.value?.add(newShoe)
        _isShoeSaved.value = true
    }

    fun setError(errorPresent: Boolean) {
        _isError.value = errorPresent
    }

    fun setIsShoeSaved(isShoeSaved: Boolean) {
        _isShoeSaved.value = isShoeSaved
    }

    fun clearShoeList() {
        _shoeList.value?.clear()
    }
}