package com.yipl.labelstep.repository

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

abstract class NetworkBoundResource<DBDataModel, RetrofitDataModel>: CoroutineScope {

    val results = Channel<ResultOrErrorWrapper<DBDataModel>>()

    suspend fun start():  NetworkBoundResource<DBDataModel, RetrofitDataModel> {

        results.send(ResultOrErrorWrapper.error("Fetching Data From DB", null))

        val dataFromDB = fetchDataFromDB()

        results.send(ResultOrErrorWrapper.error("DB Fetch Complete", null))

        if (isDataFromNWRequired(dataFromDB)) {
            results.send(ResultOrErrorWrapper.error("Fetching Data From NW", null))

            fetchDataFromNW()
        } else {
            results.send(ResultOrErrorWrapper.error("NW Fetch Not Required", dataFromDB))

        }

        return this
    }

    abstract suspend fun fetchDataFromDB(): DBDataModel

    abstract fun isDataFromNWRequired(dbDataModel: DBDataModel): Boolean

    abstract fun createNWCall(): Deferred<RetrofitDataModel>

    abstract suspend fun processDownloadedData(data: RetrofitDataModel)

    suspend fun fetchDataFromNW() = coroutineScope {

        try {
            withContext(Dispatchers.IO) {
                val result = createNWCall().await()

                processDownloadedData(result)
            }

            results.send(ResultOrErrorWrapper.error("Data Fetch from NW Successful", null))
        } catch (e: Exception) {
            results.send(ResultOrErrorWrapper.error(e.message ?: "Error Message not set", null))
        }
    }
}