package com.noplugins.keepfit.coachplatform.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.activity.info.InformationActivity
import com.noplugins.keepfit.coachplatform.activity.info.SettingActivity
import com.noplugins.keepfit.coachplatform.adapter.TeacherTagAdapter
import com.noplugins.keepfit.coachplatform.base.BaseFragment
import com.noplugins.keepfit.coachplatform.bean.TagEntity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.global.withTrigger
import kotlinx.android.synthetic.main.fragment_mine.*

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
        }
        return newView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setting()
        onClick()
    }

    private fun setting() {
        val mstrings: List<TagEntity> = ArrayList()
        val tagAdapter = TeacherTagAdapter(activity, mstrings)
        grid_view.adapter = tagAdapter

        tv_user_Name.text = ""
        tv_score.text = ""
        tv_teacher_tips.text = ""

        tv_service_time.text = ""
        tv_team_num.text = ""
        tv_teacher_num.text = ""
        tv_month_money.text = ""
        tv_team_money.text = ""
        tv_price_money.text = ""
    }

    private fun onClick(){
        iv_logo.clickWithTrigger(1000) {
            //跳转个人信息
        }
        ll_qianbao.clickWithTrigger(1000){

        }
        ll_class_manager.clickWithTrigger(1000){

        }
        ll_class_cg.clickWithTrigger(1000){

        }
        ll_calss_time.clickWithTrigger(1000){

        }
        ll_issue_feed.clickWithTrigger(1000){

        }
        ll_setting.clickWithTrigger(1000){
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }

    }

}