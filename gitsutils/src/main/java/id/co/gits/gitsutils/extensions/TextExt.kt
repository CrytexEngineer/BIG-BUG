package id.co.gits.gitsutils.extensions

import android.text.SpannableStringBuilder
import com.google.gson.Gson
import java.text.DecimalFormat
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 *
 * In syaa Allah created & modified
 * by mochadwi on 13/01/19
 * dedicated to build android-cus-store
 *
 */

// TODO: e.g: "string".putSpans(howmany, start, end, flags) { ForgroundColorSpan(color), StyleSpan(BOLD) }
fun String.putSpans(vararg flags: Int.() -> Unit, spanBuilder: SpannableStringBuilder.() -> Unit):
        SpannableStringBuilder = SpannableStringBuilder(this).apply(spanBuilder).apply {
    //    while ()
}

fun Int.convertToIDR(): String {
    val formatter = DecimalFormat("###,###,###")
    return "Rp " + formatter.format(java.lang.Double.parseDouble(this.toString()))
}

fun Any.convertToString(): String {
    return Gson().toJson(this)
}

@ExperimentalContracts
@Suppress("UNUSED_PARAMETER")
fun Int?.isNullOrBlank(): Boolean {
    contract {
        returns(false) implies (this@isNullOrBlank != null)
    }

    return this == null || this.isBlank()
}

// Assume default value is -1, flagged it as blank, unless
fun Int.isBlank(): Boolean {
    return this == -1
}

fun Int?.isNullOrZero(): Boolean = this == null || this.isZero()

fun Int.isZero(): Boolean = this == 0