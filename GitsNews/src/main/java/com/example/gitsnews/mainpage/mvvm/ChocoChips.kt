@file:Suppress("UNCHECKED_CAST")

package radhika.yusuf.id.mvvmkotlin.utils.chocohelper

import android.arch.lifecycle.ViewModel
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import com.example.gitsnews.mainpage.mvvm.obtainViewModel
import id.co.gits.gitsbase.BaseViewModel
import id.gits.gitsmvvmkotlin.base.BaseFragment
import id.gits.gitsmvvmkotlin.base.BaseUserActionListener
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import id.gits.gitsmvvmkotlin.data.source.GitsRepository
import id.gits.gitsmvvmkotlin.data.source.local.GitsLocalDataSource
import id.gits.gitsmvvmkotlin.data.source.remote.GitsRemoteDataSource

/**
 * Made with ❤ by Radhika Yusuf
 */

object ChocoChips {

    private var localDataSource: GitsDataSource? = null
    private var remoteDataSource: GitsDataSource? = null

    @JvmStatic
    fun inject(vm: BaseViewModel) {
        val myClass = vm::class.java
        for (field in myClass.superclass?.declaredFields ?: emptyArray()) {
            val annotation = field.getAnnotation(ChocoRepository::class.java)
            if (annotation != null) {
                if (localDataSource == null && remoteDataSource == null) {
                    localDataSource = GitsLocalDataSource(vm.getApplication())
                    remoteDataSource = GitsRemoteDataSource
                }

                field.isAccessible = true
                field.set(vm, GitsRepository(remoteDataSource!!, localDataSource!!))
                field.isAccessible = false
            }
        }
    }

    @JvmStatic
    fun <T : ViewDataBinding, VM : BaseViewModel, L : BaseUserActionListener> inject(fragment: BaseFragment<VM>) {
        var mBinding: ViewDataBinding? = null
        var mVM: ViewModel? = null

        val myClass = fragment::class.java
        for (field in myClass.declaredFields) {
            val annotation = field.getAnnotation(ChocoBinding::class.java)
            if (annotation != null) {
                mBinding = DataBindingUtil.inflate<T>(
                    LayoutInflater.from(fragment.requireActivity()),
                    annotation.layout,
                    null,
                    false
                )
                field.isAccessible = true
                field.set(fragment, mBinding)
                field.isAccessible = false
            }

            val vmAnnotation = field.getAnnotation(ChocoViewModel::class.java)
            if (vmAnnotation != null) {
                if (ViewModel::class.java.isAssignableFrom(field.type)) {
                    mVM = fragment.obtainViewModel(field.type as Class<VM>)
                    field.isAccessible = true
                    field.set(fragment, mVM)
                    field.isAccessible = false
                    fragment.mParentVM = mVM
                }
            }
        }

        if (mBinding != null && mVM != null) {
            val classBinding = mBinding::class.java
            for (bindingField in classBinding.declaredFields) {
                if (ViewModel::class.java.isAssignableFrom(bindingField.type)) {
                    bindingField.isAccessible = true
                    bindingField.set(mBinding, mVM)
                    bindingField.isAccessible = false
                } else if (BaseUserActionListener::class.java.isAssignableFrom(bindingField.type) && BaseUserActionListener::class.java.isAssignableFrom(
                        fragment::class.java
                    )
                ) {
                    bindingField.isAccessible = true
                    bindingField.set(mBinding, fragment as L)
                    bindingField.isAccessible = false
                }
            }
        }
    }

}