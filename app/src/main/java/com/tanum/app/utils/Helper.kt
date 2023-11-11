package com.tanum.app.utils

import android.content.Context
import android.app.DatePickerDialog
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.tanum.app.R
import com.tanum.app.model.remote.response.ErrorResponse
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale

object DatePickerHelper {
    fun showDatePickerDialog(context: Context, initialDate: Calendar?, onDateSelected: (String) -> Unit) {
        val calendar = initialDate ?: Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(context, { _, year, month, day ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, day)
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
            val formattedDate = dateFormat.format(selectedDate.time)
            onDateSelected(formattedDate)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }
}

object DateFormatter {
    fun formatToZFormat(
        fullDateString: String
    ): String {
        val inputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'", Locale("id", "ID"))
        val date = inputDateFormat.parse(fullDateString)
        return outputDateFormat.format(date)
    }

    fun formatToFullDateFormat(
        zDateString: String
    ): String {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'", Locale("id", "ID"))
        val outputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        val date = inputDateFormat.parse(zDateString)
        return outputDateFormat.format(date)
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

    fun showCompleteAlertDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonLabel: String,
        negativeButtonLabel: String,
        positiveButtonAction:() -> Unit
    ) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveButtonLabel) { dialog, _ ->
                positiveButtonAction()
                dialog.dismiss()
            }
            setNegativeButton(negativeButtonLabel) { dialog, _ ->
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

fun calculateAge(dateStart: String): String {
    val tanggalMulai = Instant.parse(dateStart)
    val usia = Period.between(tanggalMulai.atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now())

    return "${usia.years} tahun ${usia.months} bulan"
}

fun getRandomNumber(): String {
    return ((1..10).random()).toString()
}

fun getImage(imageId: String): Int {
    return when (imageId) {
        "1" -> R.drawable.image_lahan_1
        "2" -> R.drawable.image_lahan_2
        "3" -> R.drawable.image_lahan_3
        "4" -> R.drawable.image_lahan_4
        "5" -> R.drawable.image_lahan_5
        "6" -> R.drawable.image_lahan_6
        "7" -> R.drawable.image_lahan_7
        "8" -> R.drawable.image_lahan_8
        "9" -> R.drawable.image_lahan_9
        "10" -> R.drawable.image_lahan_10
        else -> 0
    }
}