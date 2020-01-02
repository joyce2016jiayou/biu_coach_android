package com.noplugins.keepfit.coachplatform.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.activity.ClassManagerActivity
import com.noplugins.keepfit.coachplatform.activity.ShoukeCgActivity
import com.noplugins.keepfit.coachplatform.activity.TeacherTimeActivity
import com.noplugins.keepfit.coachplatform.activity.info.InformationActivity
import com.noplugins.keepfit.coachplatform.activity.info.ProductAdviceActivity
import com.noplugins.keepfit.coachplatform.activity.info.SettingActivity
import com.noplugins.keepfit.coachplatform.activity.mine.WalletActivity
import com.noplugins.keepfit.coachplatform.adapter.TeacherFunctionAdapter
import com.noplugins.keepfit.coachplatform.adapter.TeacherTagAdapter
import com.noplugins.keepfit.coachplatform.base.BaseFragment
import com.noplugins.keepfit.coachplatform.bean.MineBean
import com.noplugins.keepfit.coachplatform.bean.MineFunctionBean
import com.noplugins.keepfit.coachplatform.bean.TagEntity
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.global.withTrigger
import com.noplugins.keepfit.coachplatform.util.BaseUtils
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.HashMap

class MineFragment : BaseFragment() {

    companion object {
        fun newInstance(title: String): MineFragment {
            val fragment = MineFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    var newView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_mine, container, false)
            EventBus.getDefault().register(this)
        }
        return newView
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEvent(data1: String) {
        if (data1 == "修改了资料"){
            requestData()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onClick()
    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()
        requestData()
    }



    private fun setting(min: MineBean) {
        SpUtils.putString(activity, AppConstants.TEACHER_TYPE, ""+min.teacherType)
        SpUtils.putString(activity, AppConstants.NAME, min.realname)
        if (min.lableList!=null){
            val tagAdapter = TeacherTagAdapter(activity, min.lableList)
            grid_view.adapter = tagAdapter
        }

        val fuctionBean: MutableList<MineFunctionBean> = ArrayList()
        val min1 = MineFunctionBean("钱包", R.drawable.mine_qb)
        val min2 = MineFunctionBean("课程管理", R.drawable.mine_kc)

//        if (min.teacherType == 2) {
//
//        }
        val min3 = MineFunctionBean("授课场馆", R.drawable.mine_cgyy)
        fuctionBean.add(min3)
        val min4 = MineFunctionBean("授课时间", R.drawable.mine_sksj)
        val min5 = MineFunctionBean("问题反馈", R.drawable.mine_wtfc)
        val min6 = MineFunctionBean("设置", R.drawable.setting)
        fuctionBean.add(min1)
        fuctionBean.add(min2)

        fuctionBean.add(min4)
        fuctionBean.add(min5)
        fuctionBean.add(min6)
        val functionAdapter = TeacherFunctionAdapter(activity, fuctionBean)
        gv_function.adapter = functionAdapter

        gv_function.setOnItemClickListener { parent, view, position, id ->
            if (BaseUtils.isFastClick()) {
                when (fuctionBean[position].name) {
                    "钱包" -> {
                        val intent = Intent(activity, WalletActivity::class.java)
                        startActivity(intent)
                    }
                    "课程管理" -> {
                        val intent = Intent(activity, ClassManagerActivity::class.java)
                        startActivity(intent)
                    }
                    "授课场馆" -> {
                        if (SpUtils.getString(activity,AppConstants.TEACHER_TYPE) == "1"){
                            SuperCustomToast.getInstance(activity)
                                .show("暂无私教身份")
                            return@setOnItemClickListener
                        }
                        val intent = Intent(activity, ShoukeCgActivity::class.java)
                        startActivity(intent)
                    }
                    "授课时间" -> {
                        val intent = Intent(activity, TeacherTimeActivity::class.java)
                        startActivity(intent)
                    }
                    "问题反馈" -> {
                        val intent = Intent(activity, ProductAdviceActivity::class.java)
                        startActivity(intent)
                    }
                    "设置" -> {
                        val intent = Intent(activity, SettingActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
        tv_user_Name.text = min.realname
        tv_score.text = "${min.finalGrade}分"
        tv_teacher_tips.text = min.tips

        tv_service_time.text = "${min.serviceDur}h"
        tv_team_num.text = "${min.classTotal}"
        tv_teacher_num.text = "${min.privateTotal}"
        SpUtils.putString(activity,AppConstants.LOGO,min.logoUrl)
        Glide.with(this)
            .load(min.logoUrl)
            .into(iv_logo)

        tv_month_money.text = ""
        tv_team_money.text = ""
        tv_price_money.text = ""
    }

    private fun requestData() {
        val params = HashMap<String, Any>()
//        params["teacherNum"] = SpUtils.getString(activity,AppConstants.USER_NAME)
        params["userNum"] = SpUtils.getString(activity, AppConstants.USER_NAME)

        val subscription = Network.getInstance("场馆列表", activity)
            .coachUserHome(
                params,
                ProgressSubscriber("场馆列表", object : SubscriberOnNextListener<Bean<MineBean>> {
                    override fun onNext(result: Bean<MineBean>) {
                        setting(result.data)
                    }

                    override fun onError(error: String) {

                    }
                }, activity, false)
            )

    }

    private fun onClick() {
        iv_logo.clickWithTrigger(1000) {
            //跳转个人信息
            val intent = Intent(activity, InformationActivity::class.java)
            startActivity(intent)
        }
//        ll_qianbao.clickWithTrigger(1000) {
//
//        }
//        ll_class_manager.clickWithTrigger(1000) {
//
//        }
//        ll_class_cg.clickWithTrigger(1000) {
//
//        }
//        ll_calss_time.clickWithTrigger(1000) {
//
//        }
//        ll_issue_feed.clickWithTrigger(1000) {
//
//        }
//        ll_setting.clickWithTrigger(1000) {
//            val intent = Intent(activity, SettingActivity::class.java)
//            startActivity(intent)
//        }

    }

}