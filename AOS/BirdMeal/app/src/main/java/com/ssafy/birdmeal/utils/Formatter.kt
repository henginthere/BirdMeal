package com.ssafy.birdmeal.utils

class Formatter {
    object DateFormatter {
        fun String.formatDate(): String {
            return this.substring(0, 4) + "년 " + this.substring(
                4,
                6
            ) + "월 " + this.substring(6) + "일"
        }
    }
}