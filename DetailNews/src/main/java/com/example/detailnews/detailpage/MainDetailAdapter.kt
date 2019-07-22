package com.example.detailnews.detailpage


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.detailnews.R
import com.example.detailnews.databinding.ItemMainDetailBinding
import com.example.detailnews.databinding.ItemMainDetailLoadmoreBinding


class MainDetailAdapter(var mData: List<MainDetailModel>, val mViewModel: MainDetailViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var showProgress = true

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoadMoreItem) {
            holder.bind(!(mViewModel.isLastPage.get() ?: false))
        } else if (holder is MainDetailItem) {
            holder.bind(mData[position], mViewModel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_main_detail_loadmore) {
            val binding = ItemMainDetailLoadmoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadMoreItem(binding)
        } else {
            val binding = ItemMainDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MainDetailItem(binding)
        }
    }

    override fun getItemCount(): Int {
        return if (mData.isEmpty()) 0 else mData.size + (if (showProgress) 1 else 0)
    }

    override fun getItemViewType(position: Int): Int {
        return if (showProgress) {
            if (mData.size == position) R.layout.item_main_detail_loadmore else R.layout.item_main_detail
        } else {
            R.layout.item_main_detail
        }
    }


    fun showProgress(show: Boolean) {
        showProgress = show
        notifyItemChanged(mData.size)
    }


    fun replaceData(data: List<MainDetailModel>) {
        /**
         * ketika data kosong maka notify adapter
         */
        if (mData.isEmpty()) {
            mData = data
            notifyDataSetChanged()
        } else {

            /**
             * ketika data sudah ada maka notifyitemrangeinserted adapter
             * menghindari layar blink
             */
            if (data.size > mData.size) {
                val lastPosition = (MainDetailViewModel.PAGE_SIZE * mViewModel.mPagePosition) - 1
                mData = data
                notifyItemRangeInserted(lastPosition, mData.size - 1)
            }
            mViewModel.mPagePosition += 1
        }
    }


    class MainDetailItem(binding: ItemMainDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        val mBinding = binding;

        fun bind(data: MainDetailModel, viewModel: MainDetailViewModel) {
            mBinding.mData = data
            mBinding.mListener = object : MainDetailUserActionListener {

                override fun onClickItem(data: MainDetailModel) {
                    viewModel.eventClickItem.value = data
                }

            }
        }

    }


    class LoadMoreItem(private val mBinding: ItemMainDetailLoadmoreBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun bind(show: Boolean) {
            mBinding.show = show
        }

    }

}