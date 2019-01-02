package com.tmosest.calculator

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.util.Log
import java.math.BigDecimal

private const val TAG = "CalculatorViewModel"
/**
 * Example of a view model class.
 */
class BigDecimalViewModel : ViewModel() {
    private var operand: BigDecimal? = null
    private var pendingOperation = "="

    private val result = MutableLiveData<BigDecimal>()
    val stringResult : LiveData<String>
        get() = Transformations.map(result, BigDecimal::toString)

    private val newNumber = MutableLiveData<String>()
    val stringNewNumber : LiveData<String>
        get() = newNumber

    private val operation = MutableLiveData<String>()
    val stringOperation : LiveData<String>
        get() = operation

    fun digitPressed(caption: String) {
        if (newNumber.value != null) {
            if (caption == "." && newNumber.toString().contains(".")) {
                return
            }
            newNumber.value += caption
        } else {
            newNumber.value = caption
        }
    }

    fun operandPressed(op: String) {
        Log.d(TAG, "operandPressed: with $op")
        try {
            val value = newNumber.value?.toBigDecimal()
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
            val negative = value.toBigDecimal() * BigDecimal(-1)
            newNumber.value = negative.toString()
        } catch (exception: NumberFormatException) {
            newNumber.value = "-"
        }
    }

    private fun performOperation(value: BigDecimal, operation: String) {
        Log.d(TAG, "performOperation: with $value and $operation")
        if (operand == null) {
            operand = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand = value
                "/" -> operand = if (value == BigDecimal(0.0)) {
                    BigDecimal(Double.NaN)
                } else {
                    operand!! / value
                }
                "*" -> operand = operand!! * value
                "-" -> operand = operand!! - value
                "+" -> operand = operand!! + value
            }

            result.value = operand
            newNumber.value = ""
        }
    }
}
