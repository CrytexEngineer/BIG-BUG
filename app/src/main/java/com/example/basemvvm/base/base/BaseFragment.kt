package id.gits.gitsmvvmkotlin.base

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.example.basemvvm.R
import id.co.gits.gitsbase.BaseViewModel
import id.co.gits.gitsutils.extensions.showSnackbarDefault
import id.co.gits.gitsutils.extensions.showSnackbarWithCustomColor
import id.co.gits.gitsutils.extensions.showToast

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    lateinit var mParentVM: T
    private var mMessageType = MESSAGE_TYPE_SNACK

    override fun onViewCreated(paramView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(paramView, savedInstanceState)
        mParentVM.apply {
            eventGlobalMessage.observe(this@BaseFragment, Observer {
                if (it != null) {
                    when (mMessageType) {
                        MESSAGE_TYPE_SNACK_CUSTOM -> {
                            view?.showSnackbarWithCustomColor(
                                it,
                                R.color.colorAccent,
                                R.color.greyBackgroundDefault
                            )
                        }
                        MESSAGE_TYPE_SNACK -> {
                            view?.showSnackbarDefault(it)
                        }
                        else -> {
                            requireContext().showToast(it)
                        }
                    }
                }
            })
        }

        onCreateObserver(mParentVM)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setContentData()
        mMessageType = setMessageType()

    }

    override fun onDestroy() {
        super.onDestroy()
        onDestroyObserver(mParentVM)
    }

    abstract fun onCreateObserver(viewModel: T)
    abstract fun setContentData()
    abstract fun setMessageType(): String
    abstract fun onDestroyObserver(viewModel: T)

    companion object {
        const val MESSAGE_TYPE_TOAST = "TOAST_TYPE"
        const val MESSAGE_TYPE_SNACK = "SNACK_TYPE"
        const val MESSAGE_TYPE_SNACK_CUSTOM = "SNACK_CUSTOM_TYPE"
    }

}