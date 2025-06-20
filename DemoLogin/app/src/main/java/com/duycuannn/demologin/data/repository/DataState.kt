package com.duycuannn.demologin.data.repository

sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val message: Exception) : DataState<Nothing>()
}