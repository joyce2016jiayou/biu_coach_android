package com.noplugins.keepfit.coachplatform.activity.manager

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.TeamDetail9ImgAdapter
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerTeamBean
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.GlideRoundTransform
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.data.DateHelper
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.activity_team_info.*
import java.util.HashMap

class TeamInfoActivity : BaseActivity() {
    var courseNum = ""
    override fun initBundle(parms: Bundle?) {
        if (parms != null) {

            courseNum = parms.getString("courseNum").toString()
            requestData(courseNum)
        }


    }

    override fun initView() {
        setContentView(R.layout.activity_team_info)
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger {
            finish()
        }
        jujue.clickWithTrigger {
            toJujue(title_tv)
        }

        jieshou.clickWithTrigger {
            agreeCourse(1,"")
        }

    }

    private fun requestData(courseNum:String){
        val params = HashMap<String, Any>()
        params["courseNum"] = courseNum
        subscription = Network.getInstance("课程管理", this)
            .courseDetail(params,
                ProgressSubscriber("课程管理", object : SubscriberOnNextListener<Bean<ManagerTeamBean>> {
                    override fun onNext(result: Bean<ManagerTeamBean>) {
                        setting(result.data)
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }

    @SuppressLint("SetTextI18n")
    private fun setting(managerTeamBean: ManagerTeamBean) {
        if (managerTeamBean.pic!=null){
            initAdapter(managerTeamBean.pic)
        }
        tv_class_type.text = statusType(managerTeamBean.courseList.status)
        if (managerTeamBean.courseList.status == 3){
            ll_caozuo.visibility = View.VISIBLE
        }
        if (managerTeamBean.courseList.status == 2){
            tv_jujue.visibility = View.VISIBLE
            tv_jujue.text = "拒绝理由："
        }

        title_tv.text = managerTeamBean.courseList.courseName
        tv_cg_name.text = managerTeamBean.courseList.areaName
        edit_class_room.text = roomType(managerTeamBean.courseList.type.toInt())+
                "|"+managerTeamBean.courseList.applyNum
        edit_class_name.text = managerTeamBean.courseList.courseName
        tv_select_type.text =classType(managerTeamBean.courseList.classType)
        tv_team_length.text = "${managerTeamBean.courseList.min}min"
        edit_price.text = "¥"+managerTeamBean.courseList.price
        edit_cycle.text = ""+managerTeamBean.courseList.loopCycle+"周"

        val startHour = DateHelper.getDateByLong(managerTeamBean.courseList.startTime)
        val startDay  = DateHelper.getDateDayByLong(managerTeamBean.courseList.startTime)
        val endHour = DateHelper.getDateByLong(managerTeamBean.courseList.endTime)
        edit_date.text = "$startDay $startHour-$endHour"
        edit_jieshao.text = ""+managerTeamBean.courseList.courseDes
        edit_shihe.text = ""+managerTeamBean.courseList.suitPerson
        edit_zhuyi.text = ""+managerTeamBean.courseList.tips

        Glide.with(this)
            .load(managerTeamBean.courseList.imgUrl)
            .transform(CenterCrop(this), GlideRoundTransform(this,8))
            .into(iv_team_logo)
    }

    private fun roomType(roomType: Int): String {
        val listClass = resources.getStringArray(R.array.team_class_room)
        return listClass[roomType - 1]
    }

    private fun classType(classType: Int): String {
        val listClass = resources.getStringArray(R.array.team_class_types)
        return listClass[classType - 1]
    }

    private fun statusType(type: Int): String {
        val listClass = arrayOf("邀请成功", "邀请失败", "邀请中", "已过期")
        return listClass[type - 1]
    }


    private fun initAdapter(data: List<String>) {
        //,GridLayoutManager.HORIZONTAL,false
        val layoutManager = GridLayoutManager(this, 3)
        //调整RecyclerView的排列方向
        rv_class_img.layoutManager = layoutManager
        val adapter = TeamDetail9ImgAdapter(data)
        rv_class_img.adapter = adapter
    }


    private fun toJujue(view1: TextView) {
        val popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.dialog_to_jujue)
            .setBackGroundLevel(0.5f)//0.5f
            .setAnimationStyle(R.style.main_menu_animstyle)
            .setWidthAndHeight(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(view1)

        /**设置逻辑 */
        val view = popupWindow.contentView
        val cancel = view.findViewById<LinearLayout>(R.id.cancel_layout)
        val sure = view.findViewById<LinearLayout>(R.id.sure_layout)
        val edit = view.findViewById<EditText>(R.id.et_content)
        cancel.setOnClickListener {
            popupWindow.dismiss()
        }
        sure.setOnClickListener {
            popupWindow.dismiss()
            //去申请
            agreeCourse(0,edit.text.toString())

        }
    }

    private fun agreeCourse(type:Int,str:String){
        val params = HashMap<String, Any>()
//        params["teacherNum"] = SpUtils.getString(activity, AppConstants.USER_NAME)
        params["teacherNum"] = SpUtils.getString(applicationContext, AppConstants.USER_NAME)
        params["courseNum"] = courseNum
        params["agree"] = type
        if (type == 0){
            params["refuse"] = str
        }
        val subscription = Network.getInstance("团课同意/拒绝", this)
            .agreeCourse(params,
                ProgressSubscriber("团课同意/拒绝", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
                        requestData(courseNum)
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }
}
