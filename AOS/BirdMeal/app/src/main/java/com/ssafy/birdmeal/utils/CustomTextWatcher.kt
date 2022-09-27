package com.ssafy.birdmeal.utils

import android.text.Editable
import android.text.Selection
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat

// EditText 천단위 콤마 설정
class CustomTextWatcher internal constructor(private val editText: EditText) : TextWatcher {
    var strAmount = ""
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (!TextUtils.isEmpty(s.toString()) && s.toString() != strAmount) {
            strAmount = makeStringComma(s.toString().replace(",", ""))
            editText.setText(strAmount)
            val editable = editText.text
            Selection.setSelection(editable, strAmount.length)
        }
    }

    override fun afterTextChanged(s: Editable) {}
    protected fun makeStringComma(str: String): String {    // 천단위 콤마설정.
        if (str.length == 0) {
            return ""
        }
        val value = str.toLong()
        val format = DecimalFormat("###,###")
        return format.format(value)
    }
}