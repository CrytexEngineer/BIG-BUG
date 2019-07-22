package id.co.gits.gitsutils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import id.co.gits.gitsutils.EnutriHelper.Navigation.startActivity
import id.co.gits.gitsutils.extensions.BundlePair
import id.co.gits.gitsutils.extensions.bundleOf
import java.io.IOException

// This helper to bridge between java interop
// If you use kotlin, navigate to ext package instead

class EnutriHelper {

    object Navigation {
        @JvmStatic fun startActivity(context: Context, destinationPage: AppCompatActivity) {
            context.startActivity(Intent(context, destinationPage::class.java))
        }


/**
 * If you want to start activity with custom configuration
 * e.g:
 * startActivity<YourActivity>(ctx) { "this: Intent"
 *      flags = FLAG_ACTIVITY_CLEAR_TOP
 *      putExtras(bundle)
 * }
 *//*

        @JvmStatic
        inline fun <reified T> startActivity(ctx: Context,
                                             vararg bundlePair: BundlePair,
                                             todo: Intent.() -> Intent) {
            with(ctx) {
                startActivity(Intent(this, T::class.java).apply { todo.invoke(this) })
            }
        }

        */
/**
 * If you want to start activity with minimal configuration
 * and include your
 * @see BundlePair
 * e.g:
 * startActivity<YourActivity>(ctx, "key0" bundleTo "val0", "key1" bundleTo "val1")
 */

        @JvmStatic
        inline fun <reified T> startActivity(ctx: Context,
                                             vararg bundlePair: BundlePair,
                                             withFlags: Boolean = false) {
            with(ctx) {
                startActivity(
                        Intent(this, T::class.java).apply {
                            if (withFlags)
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                            putExtras(bundleOf(*bundlePair))
                        }
                )
            }
        }
    }


    object String {
        @JvmStatic
        fun loadJSONFromAsset(context: Context?, fileName: kotlin.String): kotlin.String {
            var json = ""

            context?.apply {
                try {
                    val inputStream = assets.open(fileName)
                    val size = inputStream.available()
                    val buffer = ByteArray(size)

                    inputStream.read(buffer)
                    inputStream.close()

                    json = String(buffer)
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    return ex.message ?: ""
                }
            }

            return json
        }

        fun encryptionText(message: kotlin.String) = StringEncryptionTools().encryptText(message)

        fun decryptionText(encryptionText: kotlin.String) = StringEncryptionTools().decryptText(encryptionText)
    }

    object Const {
        // If getting data from intent
        const val EXTRA_MODULE_ITEM = "EXTRA_MODULE_ITEM"
        const val EXTRA_MODULE_ID = "EXTRA_MODULE_ID"
        const val EXTRA_MODULE_TITLE = "EXTRA_MODULE_TITLE"
        const val EXTRA_LESSON_ID = "EXTRA_LESSON_ID"
        const val EXTRA_LESSON_TITLE = "EXTRA_LESSON_TITLE"
        const val EXTRA_QUIZ_ID = "EXTRA_QUIZ_ID"
        const val EXTRA_QUIZ_TITLE = "EXTRA_QUIZ_TITLE"
        const val EXTRA_CERTIFICATE_ID = "EXTRA_CERTIFICATE_ID"
        const val EXTRA_CERTIFICATE_TITLE = "EXTRA_CERTIFICATE_TITLE"
        const val EXTRA_CERTIFICATE_RESPONSE = "EXTRA_CERTIFICATE_RESPONSE"
        const val EXTRA_CERTIFICATE_LENGTH = "EXTRA_CERTIFICATE_LENGTH"
        const val EXTRA_CERTIFICATE_ITEM = "EXTRA_CERTIFICATE_ITEM"
        const val EXTRA_CERTIFICATE_REPO = "EXTRA_CERTIFICATE_REPO"
        const val EXTRA_GLOBAL = "EXTRA_GLOBAL"

        // If getting data from argument
        const val ARGUMENT_MODULE_ITEM = "ARGUMENT_MODULE_ITEM"
        const val ARGUMENT_MODULE_ID = "ARGUMENT_MODULE_ID"
        const val ARGUMENT_LESSON_ITEM = "ARGUMENT_LESSON_ITEM"
        const val ARGUMENT_LESSON_ID = "ARGUMENT_LESSON_ID"
        const val ARGUMENT_CERTIFICATE_ID = "ARGUMENT_CERTIFICATE_ID"
        const val ARGUMENT_CERTIFICATE_TITLE = "ARGUMENT_CERTIFICATE_TITLE"
        const val ARGUMENT_CERTIFICATE_ITEM = "ARGUMENT_CERTIFICATE_ITEM"
        const val ARGUMENT_QUIZ_ITEM = "ARGUMENT_QUIZ_ITEM"
        const val ARGUMENT_QUIZ_ID = "ARGUMENT_QUIZ_ID"
        const val ARGUMENT_LOGIN_ITEM = "ARGUMENT_LOGIN_ITEM"
        const val ARGUMENT_LOGIN_ID = "ARGUMENT_LOGIN_ID"
        const val ARGUMENT_LOGIN_CATEGORY = "ARGUMENT_LOGIN_CATEGORY"
        const val ARGUMENT_REGISTER_ITEM = "ARGUMENT_REGISTER_ITEM"
        const val ARGUMENT_REGISTER_ID = "ARGUMENT_REGISTER_ID"
        const val ARGUMENT_REGISTER_CATEGORY = "ARGUMENT_REGISTER_CATEGORY"
        const val ARGUMENT_REGISTERAUTH_ITEM = "ARGUMENT_REGISTERAUTH_ITEM"
        const val ARGUMENT_REGISTERAUTH_ID = "ARGUMENT_REGISTERAUTH_ID"
        const val ARGUMENT_REGISTERAUTH_CATEGORY = "ARGUMENT_REGISTERAUTH_CATEGORY"

        // If for getting tag
        const val TAG_CERTIFICATE_SERVICE = "TAG_CERTIFICATE_SERVICE"

        // If misc. uniquely named key
        const val UNIQUE_CERTIFICATE_WORK = "UNIQUE_CERTIFICATE_WORK"

        // If intent with package
        const val ACTION_OPEN_WHATSAPP = "{PACKAGE NAME}"

        // If getting data from shared preference
        const val PREF_GENERAL = "PREF_GENERAL"
        const val PREF_USER_ID = "PREF_USER_ID"
        const val PREF_LOGGED_IN = "PREF_LOGGED_IN"
        const val PREF_AUTH_TOKEN = "PREF_AUTH_TOKEN"
        const val PREF_REFRESH_TOKEN = "PREF_REFRESH_TOKEN"
        const val PREF_EMAIL = "PREF_EMAIL"
        const val PREF_USER_CODE = "PREF_USER_CODE"
        const val PREF_PASSWORD = "PREF_PASSWORD" // DON'T FORGET TO HASH THIS!!!!

        // If getting data from intent bundle
        const val BUNDLE_MODULES = "BUNDLE_MODULES"

        // If getting data from intent bundle
        const val BUNDLE_MOVIES = "BUNDLE_MOVIES"

        // General constanta
        const val NUMBER_DEFAULT = 0
        const val CURRENCY_VALUE_DEFAULT = 0.0
        const val WEBVIEW_TEXT_SIZE_DEFAULT = 15
        const val ENCRYPTION_ALGORITHM_NAME = "Blowfish"
        const val SHARED_PREFERENCE_NAME_DEFAULT = "GitsIndonesia"
        const val SNACKBAR_TIMER_SHOWING_DEFAULT = 3000
        const val GLIDE_FADE_ANIMATION_TIME_DEFAULT = 600
        const val APP_FOLDER_DEFAULT = "APP_FILE_DIRECTORY"
        const val MESSAGE_SUCCESS_IMAGE_SAVE = "Image saved successfully"
        const val MESSAGE_FAILED_IMAGE_SAVE = "Image failed to save"
        const val SDCARD_URI_PATH = "file://mnt/sdcard/"

        // Server side constanta
        const val SERVER_CODE_404 = 404
        const val SERVER_ERROR_MESSAGE_DEFAULT = "Data not found"
        const val BASE_IMAGE_URL_MOVIE_DB = "http://image.tmdb.org/t/p/w342"

        // Date format style
        // If you want convert to indonesia please using Locale("id", "ID") put into SimpleDateFormat
        const val DATE_TIME_GLOBAL = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" //
        const val DATE_TIME_STANDARD = "yyyy-MM-dd HH:mm:ss" // 2018-10-02 12:12:12
        const val DATE_ENGLISH_YYYY_MM_DD = "yyyy-MM-dd" // 2018-10-02
        const val DATE_ENGLISH_YYYY_MM_DD_CLEAR = "yyyy MM dd" // 2018 10 02
        const val DATE_LOCALE_DD_MM_YYYY = "dd-MM-yyyy" // 02-10-2018
        const val DATE_LOCALE_DD_MM_YYYY_CLEAR = "dd MM yyyy" // 02-10-2018
        const val TIME_GENERAL_HH_MM_SS = "HH:mm:ss" // 12:12:12
        const val TIME_GENERAL_HH_MM = "HH:mm" // 12:12
        const val DAY_WITH_DATE_TIME_ENGLISH = "EEE, MMM dd yyyy HH:mm" // Mon, Aug 12 2018 12:12
        const val DAY_WITH_DATE_TIME_LOCALE = "EEE, dd MMM yyyy HH:mm" // Sen, 12 Agt 2019 12:12
        const val DAY_WITH_DATE_TIME_ENGLISH_FULL = "EEEE, MMMM dd yyyy HH:mm" // Monday, August 12 2018 12:12
        const val DAY_WITH_DATE_TIME_LOCALE_FULL = "EEEE, dd MMMM yyyy HH:mm" // Senin, 12 Agustus 2018 12:12

        const val httpHeaderAuthorization = "Authorization"
        const val httpHeaderBearerTokenPrefix = "Bearer "
        const val httpHeaderRetryCount = "Retry-Count"
        const val OAUTH_RE_AUTH_RETRY_LIMIT = 1


        // This is the sample JWT from jwt.io: we present it as default so that
        // we always have a valid, decryptable JWT to prevent possible 500 errors
        // due to uncaught decryption exceptions on the server side.
        const val BOGUS_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    }

    object Resources {
        val DialogTheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.R.style.Theme_Material_Light_Dialog_Alert
        } else {
            AlertDialog.THEME_HOLO_LIGHT
        }
    }
}
