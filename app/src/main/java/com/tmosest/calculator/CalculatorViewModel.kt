package com.tmosest.calculator

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log

private const val TAG = "CalculatorViewModel"
/**
 * Example of a view model class.
 */
class CalculatorViewModel : ViewModel() {
    private var operand: Double? = null
    private var pendingOperation = "="

    val result = MutableLiveData<String>()
    val newNumber = MutableLiveData<String>()
    val operation = MutableLiveData<String>()

    fun digitPressed(caption: String) {
        if (newNumber.value != null) {
            newNumber.value += caption
        } else {
            newNumber.value = caption
        }
    }

    fun operandPressed(op: String) {
        Log.d(TAG, "operandPressed: with $op")
        try {
            val value = newNumber.value?.toDouble()
            if (value != null) {
                performOperation(value, op)
            }
        } catch (exception: NumberFormatException) {
            newNumber.value = ""
        }
        pendingOperation = op
        operation.value = pendingOperation
    }

    fun negPressed() {
        val value = newNumber.value
        Log.d(TAG, "negPressed: with $value")
        if (value == null || value.isEmpty()) {
            newNumber.value = "-"
            return
        }
        try {
            val negative = value.toDouble() * -1
            newNumber.value = negative.toString()
        } catch (exception: NumberFormatException) {
            newNumber.value = "-"
        }
    }

    private fun performOperation(value: Double, operation: String) {
        Log.d(TAG, "performOperation: with $value and $operation")
        if (operand == null) {
            operand = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand = value
                "/" -> operand = if (value == 0.0) {
                    Double.NaN
                } else {
                    operand!! / value
                }
                "*" -> operand = operand!! * value
                "-" -> operand = operand!! - value
                "+" -> operand = operand!! + value
            }

            result.value = operand.toString()
            newNumber.value = ""
        }
    }
}