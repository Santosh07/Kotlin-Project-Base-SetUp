package com.yipl.labelstep.repository

data class ResultOrErrorWrapper<out T>(val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ResultOrErrorWrapper<T> {
            return ResultOrErrorWrapper(data, null)
        }

        fun <T> error(msg: String, data: T?): ResultOrErrorWrapper<T> {
            return ResultOrErrorWrapper(data, msg)
        }
    }
}