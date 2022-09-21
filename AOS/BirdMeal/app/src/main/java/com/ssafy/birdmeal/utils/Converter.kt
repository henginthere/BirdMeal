package com.ssafy.birdmeal.utils

import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat

class Converter {
    object DecimalConverter {

        // 숫자 천단위 표시
        fun BigDecimal.priceConvert(): String {
            val myFormat = DecimalFormat("###,###")
            return myFormat.format(this)
        }

        // Wei -> Ether 단위 변환
        object DecimalConverter {
            fun BigInteger.fromWeiToEther(): BigDecimal {
                return Convert.fromWei(toBigDecimal(), Convert.Unit.ETHER)
            }
        }
    }
}