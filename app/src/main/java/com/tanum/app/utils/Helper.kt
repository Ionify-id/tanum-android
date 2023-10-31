package com.tanum.app.utils

import android.content.Context
import android.app.DatePickerDialog
import com.google.gson.Gson
import com.tanum.app.data.remote.response.ErrorResponse
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DatePickerHelper {
    fun showDatePickerDialog(context: Context, initialDate: Calendar?, onDateSelected: (String) -> Unit) {
        val calendar = initialDate ?: Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(context, { _, year, month, day ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, day)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("id", "ID"))
            val formattedDate = dateFormat.format(selectedDate.time)
            onDateSelected(formattedDate)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        datePickerDialog.show()
    }
}

fun convertErrorResponse(stringRes: String?): ErrorResponse {
    return Gson().fromJson(stringRes, ErrorResponse::class.java)
}
