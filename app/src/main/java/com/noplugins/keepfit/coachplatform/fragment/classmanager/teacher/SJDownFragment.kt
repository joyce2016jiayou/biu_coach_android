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
import com.noplugins.keepfit.coachplatform.activity.manager.TeacherAddOrEditActivity
import com.noplugins.keepfit.coachplatform.activity.manager.TeacherInfoActivity
import com.noplugins.keepfit.coachplatform.adapter.ManagerTeacherAdapter
import com.noplugins.keepfit.coachplatform.base.BaseFragment
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerBean
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerTeacherBean
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.fragment_manager_teacher_1.*
import java.util.HashMap

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
    var  datas:MutableList<ManagerBean.CourseListBean> = ArrayList()
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
//        requestData()
    }

    override fun onFragmentVisibleChange(isVisible: Boolean) {
        super.onFragmentVisibleChange(isVisible)
        if (isVisible){
            requestData()
        }
    }

    private fun initAdapter(){
        rv_list.layoutManager = LinearLayoutManager(context)
        adapterManager = ManagerTeacherAdapter(datas)
        val view = LayoutInflater.from(context).inflate(R.layout.enpty_view, rv_list, false)
        adapterManager.emptyView = view
        rv_list.adapter = adapterManager

        adapterManager.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.tv_edit -> {
                    //编辑
                    val toEdit = Intent(activity, TeacherAddOrEditActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("type","edit")
                    bundle.putString("courseNum",datas[position].courseNum)
                    toEdit.putExtras(bundle)
                    startActivity(toEdit)
                }
                R.id.rl_jump -> {
                    //跳转到详情
                    val toInfo = Intent(activity, TeacherInfoActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("courseNum",datas[position].courseNum)
                    toInfo.putExtras(bundle)
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
        val params = HashMap<String, Any>()
//        params["teacherNum"] = SpUtils.getString(activity, AppConstants.USER_NAME)
        params["teacherNum"] = "GEN23456"
        params["courseType"] = 2
        params["type"] = 2
        val subscription = Network.getInstance("课程管理", activity)
            .courseManager(params,
                ProgressSubscriber("课程管理", object : SubscriberOnNextListener<Bean<ManagerBean>> {
                    override fun onNext(result: Bean<ManagerBean>) {
                        datas.clear()
                        datas.addAll(result.data.courseList)
                        adapterManager.notifyDataSetChanged()
                    }

                    override fun onError(error: String) {


                    }
                }, activity, false)
            )
    }


    private fun upAway(){
        val params = HashMap<String, Any>()
//        params["teacherNum"] = SpUtils.getString(activity, AppConstants.USER_NAME)
        params["teacherNum"] = "GEN23456"
        params["courseType"] = 2
        params["type"] = 2
        val subscription = Network.getInstance("上架操作", activity)
            .putaway(params,
                ProgressSubscriber("下架操作", object : SubscriberOnNextListener<Bean<String>> {
                    override fun onNext(result: Bean<String>) {

                    }

                    override fun onError(error: String) {


                    }
                }, activity, false)
            )
    }
}