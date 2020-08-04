package com.ninos.mvp.page.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ninos.mvp.R
import com.ninos.mvp.base.BaseAdapter
import com.ninos.mvp.presenter.MainPresenter

/**
 * @author Ninos
 */
class MainAdapter(context: Context) :
    BaseAdapter<MainAdapter.VHolder, String, MainPresenter>(context) {
    override fun provideItemLayoutId() = R.layout.adapter_main

    override fun createVH(parent: ViewGroup, viewType: Int, view: View): VHolder = VHolder(view)

    override fun bindData(holder: VHolder, position: Int) {
        var item = data[position]
        holder.tvTitle.text = item
    }

    inner class VHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView = view.findViewById(R.id.tv_title)
    }
}