package com.tanum.app.utils

import android.content.Context
import android.app.DatePickerDialog
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.tanum.app.data.remote.response.ErrorResponse
import retrofit2.HttpException
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

object AlertDialogHelper{
    fun showAlertDialogPositive(
        context: Context,
        title: String,
        message: String,
        positiveButtonLabel: String,
        positiveButtonAction:() -> Unit
    ) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveButtonLabel) { dialog, _ ->
                positiveButtonAction()
                dialog.dismiss()
            }
            create()
            show()
        }
    }
}

fun handleCatchError(e: Throwable): Result<String> {
    return when (e) {
        is HttpException -> {
            val jsonRes = Gson().fromJson(e.response()?.errorBody()?.string(), ErrorResponse::class.java)
            val msg = jsonRes.message
            Result.Error(msg)
        }
        else -> {
            Result.Error(e.message.toString())
        }
    }
}
