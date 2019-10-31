package com.noplugins.keepfit.coachplatform.fragment.cg

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.activity.manager.ChaungguanDetailActivity
import com.noplugins.keepfit.coachplatform.adapter.ShoukeCgAdapter
import com.noplugins.keepfit.coachplatform.base.BaseFragment
import com.noplugins.keepfit.coachplatform.bean.ChangguanBean
import com.noplugins.keepfit.coachplatform.bean.manager.CgListBean
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.fragment_manager_teacher_1.*
import org.greenrobot.eventbus.EventBus
import java.util.HashMap

class YaoqinFragment : BaseFragment()  {
    companion object {
        fun newInstance(title: String): YaoqinFragment {
            val fragment = YaoqinFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }
    var  datas:MutableList<ChangguanBean> = ArrayList()
    lateinit var adapterManager : ShoukeCgAdapter
    var newView: View? = null
    var page = 1

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
        adapterManager = ShoukeCgAdapter(datas)
        val view = LayoutInflater.from(context).inflate(R.layout.enpty_view, rv_list, false)
        adapterManager.emptyView = view
        rv_list.adapter = adapterManager

        adapterManager.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.rl_detail -> {
                    //跳转到详情页 需要携带状态
                    val toInfo = Intent(activity, ChaungguanDetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putInt("type",3)
                    bundle.putString("cgNum",datas[position].gymAreaNum)
                    toInfo.putExtras(bundle)
                    startActivity(toInfo)
                }
                R.id.tv_jieshou -> {
                    if (datas[position].gymBindingNum == ""){
                        return@setOnItemChildClickListener
                    }
                    agreeBinding(datas[position].gymBindingNum,1,position)
                }
                R.id.tv_jujue -> {
                    if (datas[position].gymBindingNum == ""){
                        return@setOnItemChildClickListener
                    }
                    agreeBinding(datas[position].gymBindingNum,2,position)
                }

            }
        }

        refresh_layout.setEnableLoadMore(false)
        refresh_layout.setEnableRefresh(false)
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
        params["teacherNum"] = SpUtils.getString(activity, AppConstants.USER_NAME)
        params["status"] = 3
        params["longitude"] = SpUtils.getString(activity,AppConstants.LON)
        params["latitude"] = SpUtils.getString(activity,AppConstants.LAT)
        val subscription = Network.getInstance("场馆列表", activity)
            .myBindingArea(
                params,
                ProgressSubscriber("场馆列表", object : SubscriberOnNextListener<Bean<List<ChangguanBean>>> {
                    override fun onNext(result: Bean<List<ChangguanBean>>) {
//                        setting(result.data.areaList)
                        if (page == 1){
                            datas.clear()
                            datas.addAll(result.data)
                        } else{
                            datas.addAll(result.data)
                        }
                        adapterManager.notifyDataSetChanged()
                    }

                    override fun onError(error: String) {

                    }
                }, activity, false)
            )

    }
//

    private fun agreeBinding(bindingNum:String,status:Int,position:Int){
        val params = HashMap<String, Any>()
        params["bindingNum"] = bindingNum
        params["status"] = status
        val subscription = Network.getInstance("接受/拒绝", activity)
            .agreeBindingArea(
                params,
                ProgressSubscriber("接受/拒绝", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
//                        setting(result.data.areaList)
                        Toast.makeText(activity,"操作成功！", Toast.LENGTH_SHORT).show()

                        if (result.code == 0){
                            datas.removeAt(position)//删除数据源,移除集合中当前下标的数据
                            adapterManager.notifyItemRemoved(position)//刷新被删除的地方
                            adapterManager.notifyItemRangeChanged(position, adapterManager.itemCount) //刷新被删除数据，以及其后面的数据

                            when (status) {
                                1 -> {
                                    EventBus.getDefault().post("接受邀请")
                                }

                                2 -> {
                                    EventBus.getDefault().post("拒绝邀请")
                                }
                            }
                        }
                        requestData()
                    }

                    override fun onError(error: String) {

                    }
                }, activity, false)
            )
    }

}