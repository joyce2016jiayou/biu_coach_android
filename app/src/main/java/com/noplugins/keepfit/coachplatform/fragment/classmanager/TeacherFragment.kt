package com.noplugins.keepfit.coachplatform.fragment.classmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.ManagerTeacherAdapter
import com.noplugins.keepfit.coachplatform.adapter.ManagerTeamClassAdapter
import com.noplugins.keepfit.coachplatform.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_manager_teacher.*

class TeacherFragment : BaseFragment() {
    companion object {
        fun newInstance(title: String): TeacherFragment {
            val fragment = TeacherFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }
    var  datas:MutableList<String> = ArrayList()
    lateinit var adapterManager : ManagerTeacherAdapter

    var newView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_manager_teacher, container, false)
        }
        return newView
    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()
        initAdapter()
        requestData()
    }

    private fun initAdapter(){
        rv_list.layoutManager = LinearLayoutManager(context)
        adapterManager = ManagerTeacherAdapter(datas)
        rv_list.adapter = adapterManager

        refresh_layout.setOnRefreshListener {
            //下拉刷新
            refresh_layout.finishRefresh(2000/*,false*/)
        }
        refresh_layout.setOnLoadMoreListener {
            //上拉加载
            refresh_layout.finishLoadMore(2000/*,false*/)
        }

    }

    private fun requestData(){
        for (i in 0..5){
            datas.add("")
        }
        adapterManager.notifyDataSetChanged()
    }
}