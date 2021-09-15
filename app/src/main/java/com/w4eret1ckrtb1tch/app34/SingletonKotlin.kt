package com.w4eret1ckrtb1tch.app34

class SingletonKotlin private constructor(var value: String) {

    fun getInstance(value: String): SingletonKotlin? {
        val result = instance
        if (result != null) return result

        synchronized(SingletonKotlin::class.java) {
            if (instance == null) instance =
                SingletonKotlin(value)
        }
        return instance
    }

    companion object {
        @Volatile
        private var instance: SingletonKotlin? = null
    }
}