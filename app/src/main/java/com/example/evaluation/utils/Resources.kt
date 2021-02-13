package com.example.evaluation.utils

data class Resources<out T>(val status: Status, val data : T?, val message: String?){

    companion object {
        fun <T> success(data : T?) : Resources<T> {
            return Resources(Status.SUCCESS,data,null)
        }
        fun <T> loading(data : T?) : Resources<T> {
            return Resources(Status.LOADING,data,null)
        }

        fun <T> error(message: String?, data : T?) : Resources<T> {
            return Resources(Status.ERROR,data,message)
        }
    }
}