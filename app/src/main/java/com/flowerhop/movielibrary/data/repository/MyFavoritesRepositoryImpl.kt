package com.flowerhop.movielibrary.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.flowerhop.movielibrary.data.dto.MovieDetailDto
import com.flowerhop.movielibrary.domain.repository.MyFavoritesRepository
import com.flowerhop.movielibrary.domain.repository.TMDBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.Exception

// The data will only stored locally, but now just stored in memory
class MyFavoritesRepositoryImpl(
    private val api: TMDBRepository,
    private val cacheFile: File? = null
) : MyFavoritesRepository {
    private val _mutableIdListLiveData: MutableLiveData<MutableList<Int>> =
        MutableLiveData<MutableList<Int>>().also {
            it.value = mutableListOf()
        }

    override fun init(onSuccess: (Unit) -> Unit, onError: (Unit) -> Unit) {
        try {
            val list: MutableList<Int> = cacheFile?.readText()?.split(",")?.map { it.toInt() }?.toMutableList() ?: mutableListOf()
            _mutableIdListLiveData.postValue(list)
            onSuccess(Unit)
        } catch (e: Exception) {
            onError(Unit)
        }
    }

    override fun add(movieId: Int) {
        _mutableIdListLiveData.value?.add(movieId)
        _mutableIdListLiveData.postValue(_mutableIdListLiveData.value)
    }

    override fun remove(movieId: Int) {
        _mutableIdListLiveData.value?.remove(movieId)
        _mutableIdListLiveData.postValue(_mutableIdListLiveData.value)
    }

    override suspend fun getList(): List<MovieDetailDto> = withContext(Dispatchers.IO) {
        val idList = _mutableIdListLiveData.value ?: emptyList()
        val jobs = idList.map { async { api.getMovieById(it) } }
        jobs.mapNotNull { it.await().body() }

        idList.map {
            async {
                api.getMovieById(it)
            }
        }.mapNotNull {
            it.await().body()
        }
    }

    override fun contains(movieId: Int): Boolean = _mutableIdListLiveData.value?.contains(movieId) ?: false
    override fun getIdListLiveData(): LiveData<List<Int>> {
        return Transformations.map(_mutableIdListLiveData) { it }
    }

    private suspend fun writeToDisk() = withContext(Dispatchers.IO) {
        cacheFile?.let { file ->
            val idStr = _mutableIdListLiveData.value?.joinToString(",") ?: return@let
            file.writeText(idStr)
        }
    }
}