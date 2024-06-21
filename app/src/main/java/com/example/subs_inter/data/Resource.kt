package com.example.subs_inter.data

sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    data object Loading: Resource<Nothing>()
    class Error<T>(message: String, error: Throwable? = null, data: T? = null) : Resource<T>(data, message, error)
}
