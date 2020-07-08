package com.ninos.mvp.base

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ninos.mvp.common.Constants

/**
 * Created by ninos on 2019/1/8.
 */
abstract class BaseAdapter<VH : RecyclerView.ViewHolder, B, P : BasePresenter<*>> : RecyclerView.Adapter<VH> {
    var mOnItemClickListener: OnItemClickListener<B>? = null   //点击事件
    var data: ArrayList<B> = ArrayList()                    //数据data
    var inflater: LayoutInflater
    var context: Context
    lateinit var presenter: P

    private var header: View? = null
    private var footer: View? = null

    constructor(context: Context) {
        this.context = context
        inflater = LayoutInflater.from(context)
    }

    constructor(context: Context, p: P) {
        this.context = context
        inflater = LayoutInflater.from(context)
        this.presenter = p
    }

    constructor(context: Context, header: View) {
        this.context = context
        inflater = LayoutInflater.from(context)
        this.header = header
    }

    constructor(context: Context, p: P, header: View) {
        this.context = context
        inflater = LayoutInflater.from(context)
        this.header = header
        this.presenter = p
    }

    constructor(context: Context, view: View, isFooter: Boolean) {
        this.context = context
        inflater = LayoutInflater.from(context)
        if (isFooter)
            footer = view
        else
            header = view
    }

    constructor(context: Context, p: P, view: View, isFooter: Boolean) {
        this.context = context
        inflater = LayoutInflater.from(context)
        this.presenter = p
        if (isFooter)
            footer = view
        else
            header = view
    }

    constructor(context: Context, p: P, header: View, footer: View) {
        this.context = context
        inflater = LayoutInflater.from(context)
        this.presenter = p
        this.header = header
        this.footer = footer
    }

    override fun getItemViewType(position: Int): Int {
        if (header != null) {
            if (position == Constants.VIEW_TYPE_HEADER) return Constants.VIEW_TYPE_HEADER
        }
        if (footer != null) {
            if (position == itemCount - 1) return Constants.VIEW_TYPE_FOOTER
        }
        return Constants.VIEW_TYPE_NORMAL
    }

    /**
     * @return ItemView的LayoutId
     */
    abstract fun provideItemLayoutId(): Int

    /**
     * @param parent
     * *
     * @param viewType
     * *
     * @param view
     * *
     * @return 创建ViewHolder
     */
    abstract fun createVH(parent: ViewGroup, viewType: Int, view: View): VH

    /**
     * @param holder
     * *
     * @param position 绑定数据
     */
    abstract fun bindData(holder: VH, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return when (viewType) {
            Constants.VIEW_TYPE_HEADER -> {
                createVH(parent, viewType, header!!)
            }
            Constants.VIEW_TYPE_FOOTER -> {
                createVH(parent, viewType, footer!!)
            }
            else -> {
                val view = LayoutInflater.from(context).inflate(provideItemLayoutId(), null)
                createVH(parent, viewType, view)
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (holder.itemViewType != Constants.VIEW_TYPE_HEADER && holder.itemViewType != Constants.VIEW_TYPE_FOOTER) {
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener { view ->
                    mOnItemClickListener!!.onItemClick(
                        view,
                        if (header == null) position else position - 1,
                        data[if (header == null) position else position - 1]
                    )
                }
            } else {
                holder.itemView.setOnClickListener(null)
            }
            bindData(holder, if (header == null) position else position - 1)
        }
    }

    override fun getItemCount(): Int {
        return if (header != null) {
            if (footer != null) data.size + 2 else data.size + 1
        } else {
            if (footer != null) data.size + 1 else data.size
        }
    }

    /**
     * 设置监听者
     */
    fun setOnItemClickListener(listener: OnItemClickListener<B>) {
        this.mOnItemClickListener = listener
    }


    /**
     * 此处为adapter的增删改，setHasFixedSize设置为true时，调用此处增删改
     * RecyclerView可以避免重新计算大小
     */
    open fun addDatas(datas: List<B>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }

    open fun addLastData(item: B) {
        data.add(item)
        notifyItemRangeInserted(data.size - 1, 1)
    }

    open fun addFirstData(item: B) {
        data.add(0, item)
        notifyItemRangeInserted(0, 1)
    }

    open fun addData(postion: Int, item: B) {
        data.add(postion, item)
        notifyItemRangeInserted(postion, 1)
    }

    open fun removeFirstData() {
        data.removeAt(0)
        notifyItemRangeRemoved(0, 1)
    }

    open fun removeLastData() {
        data.removeAt(data.size - 1)
        notifyItemRangeRemoved(data.size - 1, 1)
    }

    open fun removeData(postion: Int) {
        data.removeAt(postion)
        notifyItemRangeRemoved(postion, 1)
    }

    open fun changedData(postion: Int, item: B) {
        data[postion] = item
        this.notifyItemRangeChanged(postion, 1)
    }

    /**
     * 添加更多
     * @param data
     */
    fun addMore(data: ArrayList<B>) {
        this.data.addAll(data)
        this.notifyItemRangeChanged(this.data.size, data.size)
    }

    fun setFooter(footer: View) {
        this.footer = footer
    }

    interface OnItemClickListener<M> {
        fun onItemClick(view: View, position: Int, item: M)
    }

    class TwoColumnsDecoration(spaces: Int, span: Int) : RecyclerView.ItemDecoration() {
        private var spaces = spaces
        private var span = span
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            var temp = parent.getChildAdapterPosition(view)
            if (span == Constants.SPAN_COUNT_ONE)
                return
            if (span == Constants.SPAN_COUNT_TWO)
                if (temp % 2 == 0) {
                    outRect.right = spaces / 2
                    outRect.bottom = spaces
                } else {
                    outRect.left = spaces / 2
                    outRect.bottom = spaces
                }
        }
    }
}