package id.co.gits.gitsutils.other

import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager
import android.util.Log
import java.io.IOException

/**
 * Created by hafizdwp on 9/25/2018.
 */
class ConnectivityStatus(context: Context) : ContextWrapper(context) {

    companion object {
        fun isConnected(context: Context): Boolean {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val connection = manager.activeNetworkInfo
            if (connection != null && connection.isConnectedOrConnecting) {
                return isInternet()
            }
            return false
        }

        private fun isInternet(): Boolean {

            val runtime = Runtime.getRuntime()
            try {
                val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
                val exitValue = ipProcess.waitFor()
                Log.i(ConnectivityStatus::class.java.simpleName, exitValue.toString() + "")
                return exitValue == 0
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            return false
        }
    }
}