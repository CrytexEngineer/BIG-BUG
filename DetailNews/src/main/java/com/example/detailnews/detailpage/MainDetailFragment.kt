package com.example.detailnews.detailpage


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.arch.lifecycle.Observer
import android.widget.Toast;
import android.support.v7.widget.RecyclerView
import com.example.detailnews.databinding.FragmentMainDetailBinding


class MainDetailFragment : Fragment(), MainDetailUserActionListener {

    lateinit var mViewDataBinding: FragmentMainDetailBinding
    lateinit var mViewModel: MainDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mViewDataBinding = FragmentMainDetailBinding.inflate(inflater, container!!, false)
        mViewModel = obtainViewModel()

        mViewDataBinding.mViewModel = mViewModel.apply {

            showProgress.observe(this@MainDetailFragment, Observer {
                if (it != null) {
                    mViewDataBinding.swipeRefreshPaginate.isRefreshing = it
                }
            })

            eventClickItem.observe(this@MainDetailFragment, Observer {
                if (it != null) {
                    onClickItem(it)
                }
            })

            eventHideLoadmore.observe(this@MainDetailFragment, Observer {
                if (it != null) {
                    (mViewDataBinding.recyclerPaginate.adapter as MainDetailAdapter).showProgress(!it)
                }
            })

        }

        mViewDataBinding.mListener = this@MainDetailFragment

        return mViewDataBinding.root

    }


    override fun onClickItem(data: MainDetailModel) {
        // TODO if you have a toast extension, you can replace this
        Toast.makeText(activity, data.title, Toast.LENGTH_SHORT).show()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListData()
        mViewDataBinding.mViewModel?.start()
    }


    // TODO add MainDetailViewModel to ViewModelFactory & if template have an error, please reimport obtainViewModel
    fun obtainViewModel(): MainDetailViewModel = (activity as MainDetailActivity).obtainViewModel()


    fun setupListData() {
        mViewDataBinding.recyclerPaginate.adapter = MainDetailAdapter(mViewModel.MainDetailList, mViewModel)
        mViewDataBinding.recyclerPaginate.layoutManager = LinearLayoutManager(context)
        mViewDataBinding.recyclerPaginate.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView != null) {
                    super.onScrolled(recyclerView, dx, dy)
                }
                /**
                 * variable untuk mendeteksi item terbawah
                 */
                val layoutManager = mViewDataBinding.recyclerPaginate.layoutManager
                val visibleItemCount = layoutManager?.childCount
                val totalItemCount = layoutManager?.itemCount
                val firstVisibleItemPosition =
                    (mViewDataBinding.recyclerPaginate.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()


                /**
                 * isLastPage => apakah dia last page?, jika last page jangan memanggil API kembali
                 * isRequesting =>  apakah dia sedang request API? jika ya maka jgan memanggil API kembali
                 *                  karena jika ada double request data menjadi double
                 */
                if (mViewModel.isLastPage.get() != true && !(mViewModel.isRequesting.get() ?: false)) {
                    if (visibleItemCount != null) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount!!
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= MainDetailViewModel.PAGE_SIZE
                        ) {

                            mViewModel.isRequesting.set(true)

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
        mViewDataBinding.swipeRefreshPaginate.setOnRefreshListener {
            mViewModel.refresh()
        }
    }


    companion object {
        fun newInstance() = MainDetailFragment().apply {

        }

    }

}
