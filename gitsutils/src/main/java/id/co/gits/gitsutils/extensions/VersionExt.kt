package id.co.gits.gitsutils.extensions

import android.os.Build

/**
 *
 * In syaa Allah created & modified
 * by mochadwi on 27/02/19
 * dedicated to build e-nutri
 *
 */

val isAboveHoneyComb
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB

val isAboveKitKat
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

val isAboveLollipop
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

val isAboveM
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

val isAboveN
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N