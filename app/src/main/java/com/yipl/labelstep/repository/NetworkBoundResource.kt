package com.yipl.labelstep.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*

abstract class NetworkBoundResource<DBDataModel, RetrofitDataModel> {

    private val result = MutableLiveData<ResultOrErrorWrapper<DBDataModel>>()

    suspend fun start():  NetworkBoundResource<DBDataModel, RetrofitDataModel> {
        result.value = ResultOrErrorWrapper.error("Fetching Data From DB", null)
        coroutineScope {
            val mediator =  MediatorLiveData<List<DBDataModel>>()
            val source = fetchDataFromDB()
            mediator.addSource(source, Observer {
                mediator.removeSource(source)
            })

            if (isDataFromNWRequired()) {
                fetchDataFromNW()
            } else {
                //result.value = ResultOrErrorWrapper.success(data)
            }
        }

        return this
    }

    fun asLiveData() = result as LiveData<ResultOrErrorWrapper<DBDataModel>>

    abstract suspend fun fetchDataFromDB(): LiveData<DBDataModel>

    abstract fun isDataFromNWRequired(): Boolean

    abstract fun createNWCall(): Deferred<RetrofitDataModel>

    abstract suspend fun processDownloadedData(data: RetrofitDataModel)

    suspend fun fetchDataFromNW() = coroutineScope {

        try {
            withContext(Dispatchers.IO) {
                val result = createNWCall().await()

                processDownloadedData(result)
            }
            result.value = ResultOrErrorWrapper.error("Data Fetch Successful", null)
        } catch (e: Exception) {
            result.value = ResultOrErrorWrapper.error(e.message ?: "Error Message not set", null)
        }

        //set end result for success

        //set end result for failure
    }
}