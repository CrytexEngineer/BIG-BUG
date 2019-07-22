package com.example.gitsnews.mainpage.mvvm


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basemvvm.base.data.model.Article
import com.example.gitsnews.R
import com.example.gitsnews.databinding.FragmentHomeBinding
import id.co.gits.gitsutils.extensions.verticalListStyle
import id.gits.gitsmvvmkotlin.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import radhika.yusuf.id.mvvmkotlin.utils.chocohelper.ChocoBinding
import radhika.yusuf.id.mvvmkotlin.utils.chocohelper.ChocoChips
import radhika.yusuf.id.mvvmkotlin.utils.chocohelper.ChocoViewModel


class HomeFragment : BaseFragment<HomeViewModel>(), HomeUserActionListener {
    @ChocoBinding(R.layout.fragment_home)
    lateinit var mViewDataBinding: FragmentHomeBinding
    @ChocoViewModel
    lateinit var mViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {
        ChocoChips.inject<FragmentHomeBinding, HomeViewModel, HomeUserActionListener>(this)
        return mViewDataBinding.root
    }


    override fun onCreateObserver(viewModel: HomeViewModel) {
        mViewModel.apply {

            movieListLive.observe(this@HomeFragment, Observer {
                if(it!=null){
                  HomeViewModel.setListDataMain(recycler_main,it)
                }
            })

            showProgress.observe(this@HomeFragment, Observer {
                if (it != null) {
                    mViewDataBinding.swipeMain.isRefreshing = it
                    Log.d("TAG", "Show Load More")
                }
            })


            eventHideLoadmore.observe(this@HomeFragment, Observer {
                if (it != null) {
                    Log.d("TAG", "Hide Load More")
                    (recycler_main.adapter as HomeAdapter?)?.showProgress(it)
                }
            })
        }
    }


    private fun setupArticleList() {
        recycler_main.adapter = mViewModel.movieListLive.value?.let { HomeAdapter(this@HomeFragment, it,mViewModel) }
        recycler_main.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        Log.d("TAG", recycler_main.adapter.toString())


        recycler_main.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                recyclerView?.let { super.onScrolled(it, dx, dy) }
                /**
                 * variable untuk mendeteksi item terbawah
                 */
                val layoutManager = mViewDataBinding.recyclerMain.layoutManager
                val visibleItemCount = layoutManager?.childCount
                val totalItemCount = layoutManager?.itemCount
                val firstVisibleItemPosition =
                    (mViewDataBinding.recyclerMain.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()


                /**
                 * isLastPage => apakah dia last page?, jika last page jangan memanggil API kembali
                 * isRequesting =>  apakah dia sedang request API? jika ya maka jgan memanggil API kembali
                 *                  karena jika ada double request data menjadi double
                 */
                if (mViewModel.isLastPage.get() != true && !(mViewModel.isRequesting.get() ?: false)) {
                    Log.d("TAG", "Last Page Checker")
                    if (visibleItemCount != null) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount!!
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= HomeViewModel.PAGE_SIZE
                        ) {

                            mViewModel.isRequesting.set(true)
                            Log.d("TAG", "setRequest True")
                            /**
                             * sedikit di delay pemanggilan datanya, agar user tahu
                             * bahwa terdapat pagination
                             */
                            Handler().postDelayed({
                                mViewModel.loadData()
                            }, 1000)
                        }
                    }
                }
            }
        })

        mViewDataBinding.swipeMain.setOnRefreshListener {
            mViewModel.refresh()
        }
    }

    override fun setContentData() {
        setupArticleList()
        mViewModel.start()
        Log.d("TAG","Recycler View init")

    }

    override fun setMessageType(): String = MESSAGE_TYPE_SNACK

    override fun onDestroyObserver(viewModel: HomeViewModel) {

    }

    companion object {
        fun newInstance() = HomeFragment().apply {

        }

    }

}


fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(
        this, HomeVMFactory.getInstance(
            requireActivity()
                .application
        )
    ).get(viewModelClass)