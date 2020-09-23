package com.ninos.mvp.base

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ninos.mvp.common.Constants

/**
 * @author Ninos
 *
 * 基础Adapter类
 * RecyclerView使用的Adapter
 * 对常用数据绑定及操作进行封装
 * 可对adapter添加header布局和footer布局，不会覆盖上拉刷新和下拉加载，应对不规则布局列表
 *
 * 注意！！！
 * footer和header都不在adapter内进行控件绑定和绑定数据
 * 只负责footer和header的展示
 */
abstract class BaseAdapter<VH : RecyclerView.ViewHolder, E, P : BasePresenter<*>> :
    RecyclerView.Adapter<VH> {
    /**
     * 点击事件对象，可为空
     */
    var mOnItemClickListener: OnItemClickListener<E>? = null

    /**
     * 数据列表
     */
    var data: ArrayList<E> = ArrayList()

    /**
     * 上下文对象
     */
    var context: Context

    /**
     * presenter对象
     */
    lateinit var presenter: P

    /**
     * 头布局对象，可为空
     */
    private var header: View? = null

    /**
     * 脚布局对象，可为空
     */
    private var footer: View? = null

    /**
     * 构造方法
     *
     * @param context Context对象
     */
    constructor(context: Context) {
        this.context = context
    }

    /**
     * 构造方法
     *
     * presenter对象可在adapter内部调用接口
     *
     * @param context Context对象
     * @param P presenter对象
     */
    constructor(context: Context, p: P) {
        this.context = context
        this.presenter = p
    }

    /**
     * 构造方法
     *
     * 列表第一个item为特殊布局时用到
     *
     * @param context Context对象
     * @param header 头布局view
     */
    constructor(context: Context, header: View) {
        this.context = context
        this.header = header
    }

    /**
     * 构造方法
     *
     * presenter对象可在adapter内部调用接口
     * 列表第一个item为特殊布局时用到
     *
     * @param context Context对象
     * @param P presenter对象
     * @param header 头布局view
     */
    constructor(context: Context, p: P, header: View) {
        this.context = context
        this.header = header
        this.presenter = p
    }

    /**
     * 构造方法
     *
     * 当isFooter为true是传入的view当成footer布局
     * 当isFooter为false是传入的view当成header布局
     *
     * @param context Context对象
     * @param view 布局View
     * @param isFooter 判断传入的View是否是footer
     */
    constructor(context: Context, view: View, isFooter: Boolean) {
        this.context = context
        if (isFooter)
            footer = view
        else
            header = view
    }

    /**
     * 构造方法
     *
     * presenter对象可在adapter内部调用接口
     * 当isFooter为true是传入的view当成footer布局
     * 当isFooter为false是传入的view当成header布局
     *
     * @param context Context对象
     * @param P presenter对象
     * @param view 布局View
     * @param isFooter 判断传入的View是否是footer
     */
    constructor(context: Context, p: P, view: View, isFooter: Boolean) {
        this.context = context
        this.presenter = p
        if (isFooter)
            footer = view
        else
            header = view
    }

    /**
     * 构造方法
     *
     * presenter对象可在adapter内部调用接口
     * 当footer和header同时存在时的构造方法
     *
     * @param context Context对象
     * @param P presenter对象
     * @param header 头布局view
     * @param footer 脚布局view
     */
    constructor(context: Context, p: P, header: View, footer: View) {
        this.context = context
        this.presenter = p
        this.header = header
        this.footer = footer
    }

    /**
     * 用于判断itemView的类型，是否是header或者footer
     *
     * @param position item view的position
     *
     * @return 返回ViewType值，具体值看常量注释
     */
    override fun getItemViewType(position: Int): Int {
        if (header != null && position == Constants.VIEW_TYPE_HEADER) {
            return Constants.VIEW_TYPE_HEADER
        }
        if (footer != null && position == itemCount - 1) {
            return Constants.VIEW_TYPE_FOOTER
        }
        return Constants.VIEW_TYPE_NORMAL
    }

    /**
     * 绑定布局资源id
     *
     * @return ItemView的布局资源id
     */
    abstract fun provideItemLayoutId(): Int

    /**
     * 创建ViewHolder实例
     *
     * 由onCreateViewHolder进行调用，根据不同viewtype进行创建
     *
     * @param parent 父节点，ViewGroup
     * *
     * @param viewType type类型值
     * *
     * @param view view对象
     * *
     * @return 创建ViewHolder的实例
     */
    abstract fun createVH(parent: ViewGroup, viewType: Int, view: View): VH

    /**
     * 绑定数据
     * 给出item对应的数据在data内的position
     * 将数据取出，用holder对控件进行赋值
     *
     * @param holder ViewHolder对象
     * *
     * @param position 数据列表内数据的position
     */
    abstract fun bindData(holder: VH, position: Int)

    /**
     * 创建ViewHolder实例
     *
     * 根据不同viewtype进行传参，统一调用createVH方法
     *
     * @param parent 父节点，ViewGroup
     * *
     * @param viewType type类型值
     * *
     * @return 创建ViewHolder的实例
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return when (viewType) {
            Constants.VIEW_TYPE_HEADER ->
                createVH(parent, viewType, header!!)
            Constants.VIEW_TYPE_FOOTER ->
                createVH(parent, viewType, footer!!)
            else -> {
                val view = LayoutInflater.from(context).inflate(provideItemLayoutId(), null)
                createVH(parent, viewType, view)
            }
        }
    }

    /**
     * 处理数据绑定方法
     *
     * @param holder ViewHolder对象，调用布局内控件
     * @param position 数据position，用于提取数据列表内对应数据实体类
     */
    override fun onBindViewHolder(holder: VH, position: Int) {
        /**
         * 判断viewtype不为header和footer中的任何一种
         * 不为header和footer进行绑定
         */
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

    /**
     * 获取列表的item数
     *
     * 返回的数值包含header和footer所占的item数
     *
     * @return item数量
     */
    override fun getItemCount(): Int =
        if (header != null)
            if (footer != null) data.size + 2 else data.size + 1
        else
            if (footer != null) data.size + 1 else data.size


    /**
     * 列表Item的点击事件
     *
     * @param listener OnItemClickListener对象，具体看OnItemClickListener的注释
     */
    fun setOnItemClickListener(listener: OnItemClickListener<E>) {
        this.mOnItemClickListener = listener
    }

    /**
     * 添加数据
     *
     * clear原有数据，add新数据
     * 一般用于分页数据刷新，加载第一页数据用
     *
     * @param datas 数据列表
     */
    fun addDatas(datas: List<E>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }

    /**
     * 添加数据
     *
     * 保留原有数据，add新数据
     * 一般用于分页数据第一页之后的数据添加
     *
     * @param data 数据列表
     */
    fun addMore(datas: ArrayList<E>) {
        this.data.addAll(datas)
        this.notifyItemRangeChanged(this.data.size, datas.size)
    }

    /**
     * 此处为adapter的增删改，setHasFixedSize设置为true时，调用此处增删改
     * RecyclerView可以避免重新计算大小
     */

    /**
     * 在列表最后添加一条item
     *
     * @param item 数据item
     */
    fun addLastData(item: E) {
        data.add(item)
        notifyItemRangeInserted(data.size - 1, 1)
    }

    /**
     * 在列表下标为0的位置添加一条item
     *
     * @param item 数据item
     */
    fun addFirstData(item: E) {
        data.add(0, item)
        notifyItemRangeInserted(0, 1)
    }

    /**
     * 为列表指定下标的位置添加一条item
     *
     * @param postion 列表下标
     * @param item 数据item
     */
    fun addData(postion: Int, item: E) {
        data.add(postion, item)
        notifyItemRangeInserted(postion, 1)
    }

    /**
     * 移除列表下标为0的数据和item
     */
    fun removeFirstData() {
        data.removeAt(0)
        notifyItemRangeRemoved(0, 1)
    }

    /**
     * 移除列表最后一条数据和item
     */
    fun removeLastData() {
        data.removeAt(data.size - 1)
        notifyItemRangeRemoved(data.size - 1, 1)
    }

    /**
     * 移除列表指定下标的数据和item
     */
    fun removeData(postion: Int) {
        data.removeAt(postion)
        notifyItemRangeRemoved(postion, 1)
    }

    /**
     * 改变列表存在的item和数据的内容
     */
    fun changedData(postion: Int, item: E) {
        data[postion] = item
        this.notifyItemRangeChanged(postion, 1)
    }

    /**
     * 设置footer的view布局
     *
     * @param footer view布局文件
     */
    fun setFooter(footer: View) {
        this.footer = footer
    }

    /**
     * item点击事件
     *
     * @param E POJO实体类，用于传递item数据
     */
    interface OnItemClickListener<E> {
        /**
         * item点击事件方法
         *
         * @param view 点击的item view对象
         * @param position 点击的item的postion，注意此position并非data数据列表的position
         * @param item 实体类数据对象，用于数据传递
         */
        fun onItemClick(view: View, position: Int, item: E)
    }

    /**
     * 双列item的间距
     *
     * @param spaces item间距
     * @param span 列数，此为双列Decoration，取值在1和2中取
     */
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
            /**
             * span为1时不考虑间距问题，直接return
             */
            if (span == Constants.SPAN_COUNT_ONE)
                return
            /**
             * span为2时，第一列取模必定为0，第一列所有item右边距减半，第二列所有item左边距减半
             * 具体设置需要根据是否有header进行判断,取模公式需要变
             */
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