package com.kou.fisaa.utils

/** a LiveData that gets consumed once **/
class ConsumableValue<T>(private val data: T) {
    private var consumed = false

    fun consume(block: ConsumableValue<T>.(T) -> Unit) {
        if (!consumed) {
            consumed = true
            block(data)
        }
    }

    fun getConsumedValue(): T {
        return data
    }
}