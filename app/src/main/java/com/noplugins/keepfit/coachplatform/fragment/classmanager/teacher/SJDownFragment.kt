package com.noplugins.keepfit.coachplatform.fragment.classmanager.teacher

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.activity.manager.TeacherInfoActivity
import com.noplugins.keepfit.coachplatform.adapter.ManagerTeacherAdapter
import com.noplugins.keepfit.coachplatform.base.BaseFragment
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerTeacherBean
import kotlinx.android.synthetic.main.fragment_manager_teacher_1.*

class SJDownFragment : BaseFragment()  {
    companion object {
        fun newInstance(title: String): SJDownFragment {
            val fragment = SJDownFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }
    var  datas:MutableList<ManagerTeacherBean> = ArrayList()
    lateinit var adapterManager : ManagerTeacherAdapter
    var newView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_manager_teacher_1, container, false)
        }
        return newView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        requestData()
    }

    private fun initAdapter(){
        rv_list.layoutManager = LinearLayoutManager(context)
        adapterManager = ManagerTeacherAdapter(datas)
        rv_list.adapter = adapterManager

        adapterManager.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.tv_edit -> {
                    //todo 编辑
                }
                R.id.rl_jump -> {
                    //todo 跳转到详情
                    val toInfo = Intent(activity, TeacherInfoActivity::class.java)
                    startActivity(toInfo)
                }
                R.id.tv_up_down->{
                    //todo 上架
                }
            }
        }

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
        datas.clear()
        for (i in 0..3){
            val teacherBean = ManagerTeacherBean()
            teacherBean.type = 2
            datas.add(teacherBean)
        }
        adapterManager.notifyDataSetChanged()
    }
}