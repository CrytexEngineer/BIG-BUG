package com.example.gitsnews.mainpage.mvvm

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.basemvvm.base.data.model.Article
import com.example.gitsnews.BR
import com.example.gitsnews.R
import com.example.gitsnews.databinding.ItemMainpageHomeBinding
import com.example.gitsnews.databinding.ItemMainpageLoadmoreBinding
import id.co.gits.gitsutils.SingleLiveEvent


/**
 * Created by irfanirawansukirman on 26/01/18.
 */
class HomeAdapter(
    private val articleItemClickListener: HomeUserActionListener, var data: List<Article>,
    val mViewModel: HomeViewModel
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var showProgress = true
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoadMoreItemHolder) {
            Log.d("TAG", "LoadMoreHolder Checker")
            holder.bind(!(mViewModel.isLastPage.get() ?: false))
        } else if (holder is HomeItemHolder) {
            holder.bindItem(data[position], articleItemClickListener, mViewModel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_mainpage_loadmore) {
            val binding = ItemMainpageLoadmoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            Log.d("TAG", "Create Holder Load More")
            LoadMoreItemHolder(binding)
        } else {
            val binding = ItemMainpageHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HomeItemHolder(binding)
        }
    }

    /**    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
    (holder as HomeItemHolder).apply {
    mainItemBinding.apply {
    setVariable(BR.item, null)
    setVariable(BR.userActionListener, null)
    executePendingBindings()
    }
    }

    super.onViewRecycled(holder)
    }**/

    override fun getItemViewType(position: Int): Int {
        return if (showProgress) {
            Log.d("TAG", "show load progress")
            if (data.size == position) R.layout.item_mainpage_loadmore else R.layout.item_mainpage_home
        } else {
            R.layout.item_mainpage_home
        }
    }

    fun showProgress(show: Boolean) {
        showProgress = show
        notifyItemChanged(data.size)
    }

    override fun getItemCount(): Int {
        return if (data.isEmpty()) 0 else data.size + (if (showProgress) 1 else 0)
    }


    fun replaceData(data: ArrayList<Article>) {
        /**
         * ketika data kosong maka notify adapter
         */

        if (this.data.isEmpty()) {
            this.data = data!!
            notifyDataSetChanged()
            Log.d("TAG", "INIT")
        } else {

            /**
             * ketika data sudah ada maka notifyitemrangeinserted adapter
             * menghindari layar blink
             */

            Log.d("DATA", data.size.toString() + "VS" + this.data.size.toString())
            if (data.size > this.data.size) {
                val lastPosition = (HomeViewModel.PAGE_SIZE * mViewModel.mPagePosition) - 1
                this.data = data!!
                notifyItemRangeInserted(lastPosition, this.data.size - 1)
                Log.d("DATA", "INSERT")
            }
            mViewModel.mPagePosition += 1
            Log.d("TAG", "position" + mViewModel.mPagePosition.toString())
        }
    }


    class HomeItemHolder(val mainItemBinding: ItemMainpageHomeBinding) :
        RecyclerView.ViewHolder(mainItemBinding.root) {


        fun bindItem(article: Article, userActionListener: HomeUserActionListener, viewModel: HomeViewModel) {
            mainItemBinding.apply {
                setVariable(BR.item, article)
                setVariable(BR.userActionListener, userActionListener)
                executePendingBindings()
            }
        }


    }

    class LoadMoreItemHolder(private val mBinding: ItemMainpageLoadmoreBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(show: Boolean) {
            mBinding.show = show
        }

    }
}

