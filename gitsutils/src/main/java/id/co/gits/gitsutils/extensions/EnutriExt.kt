package id.co.gits.gitsutils.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.TimePickerDialog
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Bundle
import android.support.annotation.MainThread
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import id.co.gits.gitsutils.BuildConfig
import id.co.gits.gitsutils.SingleLiveEvent
import okhttp3.RequestBody
import java.io.*


/**
 * Created by irfanirawansukirman on 26/01/18.
 */

/*
fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
        ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)

inline fun <reified T : ViewModel> AppCompatActivity.obtainViewModel() =
        ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(T::class.java)

fun <VM : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<VM>) =
        ViewModelProviders.of(requireActivity(), getInstance(requireActivity().application)).get(viewModelClass)

inline fun <reified VM : ViewModel> Fragment.obtainViewModel() =
        ViewModelProviders.of(requireActivity(), getInstance(requireActivity().application)).get(VM::class.java)
*/

/**
 * Use to show snackbar
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, snackbarText, timeLength).apply {
        view.apply {
            /*findViewById<TextView>(android.support.design.R.id.snackbar_text).setTextColor(ContextCompat.getColor(context, R.color.white))
            setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))*/
        }
        show()
    }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(lifecycleOwner: LifecycleOwner,
                       snackbarMessageLiveEvent: SingleLiveEvent<Int>, timeLength: Int) {
    snackbarMessageLiveEvent.observe(lifecycleOwner, Observer {
        it?.let { showSnackbar(context.getString(it), timeLength) }
    })
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.isInvisible(): Boolean {
    return visibility == View.INVISIBLE
}

fun View.isGone(): Boolean {
    return visibility == View.GONE
}


/**
 * Use to handle NPE
 */
fun String.withDefVal(v: View): Nothing {
    let { message ->
        Thread.setDefaultUncaughtExceptionHandler { _, e -> v.showSnackbar(e.message ?: message) }
        throw Throwable(message)
    }
}

/**
 * Use to handle NPE
 */
fun String?.withDefVal() = let { it ?: "" }

/**
 * Use to add default value for nullable Int
 */
fun Int?.withDefVal() = let { it ?: -1 }

/**
 * Use to add default value for nullable Boolean
 */
fun Boolean?.withDefVal() = let { it ?: false }

/**
 * Fragment ez argument
 * */
fun <T : Fragment> T.withArgs(
        argsBuilder: Bundle.() -> Unit): T =
        this.apply {
            arguments = Bundle().apply(argsBuilder)
        }

/**
 * Ez toast
 * */
var mToast: Toast? = null

fun AppCompatActivity.toast(msg: String) {
    mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    mToast?.show()
}

fun AppCompatActivity.toastSpammable(msg: String) {
    if (mToast != null) mToast?.cancel()
    mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    mToast?.show()
}

fun Fragment.toast(msg: String) {
    (activity as AppCompatActivity).toast(msg)
}

fun Fragment.toastSpammable(msg: String) {
    (activity as AppCompatActivity).toastSpammable(msg)
}

/**
 * Quiz Feature extention
 * =================================================================================================
 * */
fun TextView.setAnswerTextBold(isSelected: Boolean) {

    val fontPathReg = "fonts/GOTHIC.TTF"
    val fontPathBold = "fonts/GOTHICB.TTF"
    val tfReg = Typeface.createFromAsset(context.assets, fontPathReg)
    val tfBold = Typeface.createFromAsset(context.assets, fontPathBold)

    typeface = if (isSelected) tfBold else tfReg
}

fun Button.setAnswerButtonBgr(isSelected: Boolean) {
/*
    if (isSelected) {
        setBackgroundResource(R.drawable.button_bg_round_red)
        setTextColor(resources.getColor(R.color.white))
    } else {
        setBackgroundResource(R.drawable.button_bg_round_gray)
        setTextColor(resources.getColor(R.color.black))
    }
*/
}

fun String.checkExpired(): Boolean = let {
    //    val inFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
//    val outFormatter = DateTimeFormat.forPattern("yyyy-MM-dd")
//    val expiredDate = inFormatter.parseDateTime(it)
//            .plusHours(23)
//            .plusMinutes(59)
//            .plusSeconds(59)
//    val currentDate = DateTime()
    return /*currentDate.isAfter(expiredDate)*/ false
}


fun String.countMostChar(): Char = with(this) {
    var result = mutableMapOf<Char, Int>()
    var max = 0
    var mostChar: Char = ' '

    // ini buat temp, yang ngasih char [a] = totalnya berapa
    forEachIndexed { _, alphabet ->
        result[alphabet] = (result[alphabet] ?: 0) + 1
    }

    // ini buat ngasih max / dari char di atas mana yang paling banyak
    result.forEach { (key, value) ->
        if (max < value) {
            max = value
            mostChar = key
        }
        println("$key = $value")
    }

    // tampilinnya
    return mostChar
}

fun String.isPalindrome(): Boolean = let {
    return it.reversed() == it
}

object Dummy {
    data class History(
            var title: String,
            var desc: String,
            var timestamp: String,
            var image: String? = null
    )

    data class AccountData(
            var user_code: String = "123456",
            var password: String = "1945-08-17"
    )

    data class HomeData(
            var image: String? = null,
            var category: String,
            var title: String,
            var timestamp: String
    )
}

/** BottomNavigationView disable shifting mode*/
@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftingMode() {
    val menuView = getChildAt(0) as BottomNavigationMenuView
    try {
        val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
        shiftingMode.isAccessible = true
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.isAccessible = false
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView
            /*item.setShiftingMode(false)*/
            item.setChecked(item.itemData.isChecked)
        }
    } catch (e: NoSuchFieldException) {
        Log.e("disableShiftingMode", "disableShiftingMode: Unable to get shift mode field")
    } catch (e: IllegalAccessException) {
        Log.e("disableShiftingMode", "disableShiftingMode: Unable to change value of shift mode")
    }
}

/**
 * Log ext
 * */
fun logError(tag: String, msg: String) {
    if (isDebug) Log.e(tag, msg)
}

fun logInfo(tag: String, msg: String) {
    if (isDebug) Log.i(tag, msg)
}

fun logDebug(tag: String, msg: String) {
    if (isDebug) Log.d(tag, msg)
}

fun loggerVerbose(msg: String) {
    // TODO: Use default logging, to reduce apk using 3rdparty libraries
    if (isDebug) Log.v("", msg)
}

fun loggerDebug(msg: String) {
    if (isDebug) Log.d("", msg)
}

fun loggerInfo(msg: String) {
    if (isDebug) Log.i("", msg)
}

fun loggerError(msg: String) {
    if (isDebug) Log.e("", msg)
}

fun loggerWarning(msg: String) {
    if (isDebug) Log.w("", msg)
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)

val Int.sp: Int
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity * 0.5f).toInt()

val Float.sp: Float
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity * 0.5f)

fun getFirebaseToken(): String {
    var refreshedToken = ""
//    FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
//        refreshedToken = instanceIdResult.token
//        // Do whatever you want with your token now
//        // i.e. store it on SharedPreferences or DB
//        // or directly send it to server
//    }

    return refreshedToken
}

/**
 * BottomSheet expand/collapse
 * */
fun <T : View> BottomSheetBehavior<T>.collapse() {
    state = BottomSheetBehavior.STATE_COLLAPSED
}

fun <T : View> BottomSheetBehavior<T>.expand() {
    state = BottomSheetBehavior.STATE_EXPANDED
}

fun <T : View> BottomSheetBehavior<T>.isCollapsed(): Boolean {
    return state == BottomSheetBehavior.STATE_COLLAPSED
}

fun <T : View> BottomSheetBehavior<T>.isExpanded(): Boolean {
    return state == BottomSheetBehavior.STATE_EXPANDED
}

/** Recyclerview's item decoration custom */
fun Fragment.myDividerItemDecoration(isDividerColorGrey: Boolean = false): DividerItemDecoration {
    val decoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)

    /*if (!isDividerColorGrey)
    decoration.setDrawable(resources.getDrawable(id.co.gits.gitsutils.R.drawable.divider_v2))*/

    return decoration
}

fun AppCompatActivity.myDividerItemDecoration(isDividerColorGrey: Boolean = false): DividerItemDecoration {
    val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)

    /*if (!isDividerColorGrey)
    decoration.setDrawable(resources.getDrawable(R.drawable.divider_v2))*/

    return decoration
}

/** BottomSheet's com.example.basemvvm.base.util */
fun AppCompatActivity.makeBottomSheet(bottomSheet: View): BottomSheetBehavior<View> {
    val bts = BottomSheetBehavior.from(bottomSheet)
    bts.isHideable = false

    return bts
}

fun TabLayout.disableTabClick() {
    val tabStrip = this.getChildAt(0) as LinearLayout
    for (i in 0 until tabStrip.childCount) {
        tabStrip.getChildAt(i).setOnTouchListener { _, _ -> true }
    }
}

fun AppCompatActivity.hideSoftKeyboard() {
    val inputMethodManager: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
}

@MainThread
fun <T> MutableLiveData<T>.call() {
    value = null
}

inline fun <reified T> Activity.switchToPage(args: Bundle? = null) {
    startActivity(Intent(this, T::class.java).apply { args?.let { putExtras(args) } })
}

inline fun <reified T> Activity.toPageFromBeginning() {
    startActivity(Intent(this, T::class.java))
    finishAffinity()
}

inline fun <reified T> Context.toPageFromBeginning() {
    startActivity(Intent(this, T::class.java).apply {
        flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
    })
    if (this is Activity) finishAffinity()
}

inline fun <reified T : Activity> Activity.navigate(dest: T? = null) {
    if (dest == null) finish()
    else switchToPage<T>()
}

fun hideKeyboard(getActivity: Activity?) {
    getActivity?.apply {
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
    }
}

fun Context.showTimePicker(listener: TimePickerDialog.OnTimeSetListener) {
    TimePickerDialog(this, listener, 8, 0, true).apply {
        setTitle("Select Time")
        show()
    }
}

fun Int.leadNumberWithZero(): String {
    return String.format("%02d", this)
}

fun <T : Serializable> deepCopy(obj: T?): T? {
    if (obj == null) return null
    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)
    oos.writeObject(obj)
    oos.close()
    val bais = ByteArrayInputStream(baos.toByteArray())
    val ois = ObjectInputStream(bais)
    @Suppress("unchecked_cast")
    return ois.readObject() as T
}

fun String?.safeToInt(): Int = this?.toIntOrNull() ?: 0

val Boolean?.isOpen: Int
    get() = if (this != null && this) 0 else 1

val Map<String, RequestBody?>.isEmpty: Boolean
    get() = this.all { it.value == null }

val isDebug = BuildConfig.DEBUG

val dummyBirthday = "1945-08-17"