package com.golf.vct.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {

    // api response > date
    const val API_FORMAT_DATE = "yyyy-MM-dd"
    // -> date 변경 할 표맷
    const val FORMAT_DATE_DOT = "yyyy.MM.dd"
    const val FORMAT_DATE_DOT_SHORT = "yyyy.M.d"
    const val FORMAT_DATE_DOT_ALBUM = "yyyy.MM.dd hh:mm"

    // api response > time
    const val API_FORMAT_DATE_TIME = "yyyy-MM-dd hh:mm:ss"


    // 2021-05-01 -> 2021.5.1 변환
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(dateStr: String?): String {
        dateStr?.let {
            val date = LocalDate.parse(it, DateTimeFormatter.ofPattern(API_FORMAT_DATE))
            return date.format(DateTimeFormatter.ofPattern(FORMAT_DATE_DOT_SHORT))
        }
        return ""
    }


}