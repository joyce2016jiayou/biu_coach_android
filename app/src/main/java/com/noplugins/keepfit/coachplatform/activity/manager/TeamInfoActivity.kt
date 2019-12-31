package com.noplugins.keepfit.coachplatform.activity.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.TeamDetail9ImgAdapter
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerTeamBean
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.PublicPopControl
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.BaseUtils
import com.noplugins.keepfit.coachplatform.util.GlideRoundTransform
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.data.DateHelper
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow
import com.noplugins.keepfit.coachplatform.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_team_info.*
import org.greenrobot.eventbus.EventBus
import java.util.HashMap

class TeamInfoActivity : BaseActivity() {
    var courseNum = ""
    var status = -1
    var type = -1
    var isEdit = -1

    var teacherNum = ""
    var statusMsg = ""
    override fun initBundle(parms: Bundle?) {
        if (parms != null) {
            courseNum = parms.getString("courseNum").toString()
            statusMsg = parms.getString("statusMsg").toString()
            status = parms.getInt("status", -1)
            type = parms.getInt("type", -1)
            isEdit = parms.getInt("isEdit", -1)
            requestData(courseNum)
        }


    }

    override fun initView() {
        setContentLayout(R.layout.activity_team_info)
        isShowTitle(false)
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger {
            finish()
        }
        jujue.clickWithTrigger {
            toJujue(jujue)
        }

        jieshou.clickWithTrigger {
            toJieshou()
        }

        label_detail.setOnClickListener {
            if (BaseUtils.isFastClick()) {
                val intent = Intent(this, TeamDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putString("courseNum", courseNum)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

        tv_team_cancel.setOnClickListener {

            Log.d("tag", "weish ?")
            if (BaseUtils.isFastClick()) {
                if (type == 4) {
                    //取消邀请
                    toCancel()
                } else {
                    //去编辑
//                    val intent = Intent(this, ClassItemEditActivity::class.java)
//                    val bundle = Bundle()
//                    bundle.putString("courseNum", courseNum)
//                    intent.putExtras(bundle)
//                    startActivity(intent)
                }

            }
        }

    }

    private fun toCancel(){
        PublicPopControl.alert_dialog_center(this) { view, popup ->
            val content = view.findViewById<TextView>(R.id.pop_content)
            val title = view.findViewById<TextView>(R.id.pop_title)
            content.setText("确定取消申请?")
            title.setText("取消申请")
            view.findViewById<LinearLayout>(R.id.cancel_btn)
                .setOnClickListener {
                    popup.dismiss()
                }
            view.findViewById<LinearLayout>(R.id.sure_btn)
                .setOnClickListener {  //去申请
                    popup.dismiss()
                    requestCancel()}
        }
    }

    private fun requestCancel() {
        val params = HashMap<String, Any>()
        params["teacherNum"] = SpUtils.getString(applicationContext, AppConstants.USER_NAME)
//        params["teacherNum"] = "GEN23456"
        params["gymInviteNum"] = courseNum
        val subscription = Network.getInstance("团课取消申请", this)
            .cancelInviteByTeacher(
                params,
                ProgressSubscriber("团课取消申请", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
                        //上架成功！
                        if (result.code == 0){
                            EventBus.getDefault().post(AppConstants.TEAM_YQ_REFUSE)
                            finish()
                        }

                        SuperCustomToast.getInstance(this@TeamInfoActivity)
                            .show(result.message)
                    }

                    override fun onError(error: String) {
                        SuperCustomToast.getInstance(this@TeamInfoActivity)
                            .show(error)
                    }
                }, this, false)
            )
    }

    private fun requestData(courseNum:String){
        val params = HashMap<String, Any>()
        params["courseNum"] = courseNum
        subscription = Network.getInstance("课程管理详细", this)
            .courseDetail(params,
                ProgressSubscriber("课程管理详细", object : SubscriberOnNextListener<Bean<ManagerTeamBean>> {
                    override fun onNext(result: Bean<ManagerTeamBean>) {
                        setting(result.data)
                    }
                    override fun onError(error: String) {
                        SuperCustomToast.getInstance(applicationContext)
                            .show(error)
                    }
                }, this, false)
            )
    }

    @SuppressLint("SetTextI18n")
    private fun setting(managerTeamBean: ManagerTeamBean) {
        if (managerTeamBean.pic!=null){
            initAdapter(managerTeamBean.pic)
        }
        tv_class_type.text = statusMsg
        if (type == 2){
            ll_caozuo.visibility = View.VISIBLE
        }
//        if (status == 2){
//            tv_jujue.visibility = View.VISIBLE
//            tv_jujue.text = "拒绝理由："
//        }
        if (type == 4) {
            fl_cancel_edit.visibility = View.VISIBLE
            tv_team_cancel.text = "取消课程"
        }

        if (type == 3 && isEdit == 1) {
            fl_cancel_edit.visibility = View.VISIBLE
            tv_team_cancel.text = "编辑"
        }

        if (managerTeamBean.courseList.courseName.length > 10) {

            title_tv.setText("${managerTeamBean.courseList.courseName.substring(0, 10)}...")
        } else {
            title_tv.setText(managerTeamBean.courseList.courseName)
        }
//        title_tv.text = managerTeamBean.courseList.courseName
        if (managerTeamBean.courseList.areaName == null){
            tv_cg_name.visibility = View.GONE
        } else {
            tv_cg_name.text = managerTeamBean.courseList.areaName
        }

        if (managerTeamBean.courseList.type != null){
            edit_class_room.text = roomType(managerTeamBean.courseList.type.toInt())+
                    "|"+managerTeamBean.courseList.maxNum+"人"
        }
        edit_class_name.text = managerTeamBean.courseList.courseName
        if (managerTeamBean.courseList.classType!=0){
            tv_select_type.text = classType(managerTeamBean.courseList.classType)
        }

        if (managerTeamBean.courseList.target!=0){

            tv_xunlian_goal.text = goalType(managerTeamBean.courseList.target)
        }
        if (managerTeamBean.courseList.difficulty != 0){
            tv_xunlian_difficulty.text = difficultyType(managerTeamBean.courseList.difficulty)
        }

        tv_team_length.text = "${(managerTeamBean.courseList.endTime - managerTeamBean.courseList.startTime) / 1000 / 60}min"
        edit_price.text = "¥" + managerTeamBean.courseList.finalPrice

        if (managerTeamBean.courseList.isLoop){
            edit_cycle.text = ""+managerTeamBean.courseList.loopCycle+"周"
        } else {
            edit_cycle.text = "单次"
        }


        val startHour = DateHelper.getDateByLong(managerTeamBean.courseList.startTime)
        val startDay  = DateHelper.getDateDayByLong(managerTeamBean.courseList.startTime)
        val endHour = DateHelper.getDateByLong(managerTeamBean.courseList.endTime)
        edit_date.text = "$startDay $startHour-$endHour"
//        edit_jieshao.text = ""+managerTeamBean.courseList.courseDes
//        edit_shihe.text = ""+managerTeamBean.courseList.suitPerson
//        edit_zhuyi.text = ""+managerTeamBean.courseList.tips
//
//        Glide.with(this)
//            .load(managerTeamBean.courseList.imgUrl)
//            .transform(CenterCrop(this), GlideRoundTransform(this,8))
//            .into(iv_team_logo)
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
        val listClass = arrayOf("邀请成功", "邀请失败", "邀请中", "已过期","邀请失败","邀请失败","邀请失败")
        return listClass[type - 1]
    }

    private fun difficultyType(difficulty: Int): String {
        val listClass = resources.getStringArray(R.array.nandu_types)
        return listClass[difficulty - 1]
    }

    private fun goalType(goal: Int): String {
        val listClass = resources.getStringArray(R.array.target_types)
        return listClass[goal - 1]
    }



    private fun initAdapter(data: List<String>) {
        //,GridLayoutManager.HORIZONTAL,false
//        val layoutManager = GridLayoutManager(this, 3)
        //调整RecyclerView的排列方向
        rv_item.layoutManager = LinearLayoutManager(this)
        val adapter = TeamDetail9ImgAdapter(data)
        rv_item.adapter = adapter
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

    private fun toJieshou(){
        PublicPopControl.alert_dialog_center(this) { view, popup ->
            val content = view.findViewById<TextView>(R.id.pop_content)
            val title = view.findViewById<TextView>(R.id.pop_title)
            content.setText("接受后请准时前往场馆进行授课，确认接受团课邀请？")
            title.setText("接受团课")
            val sure = view.findViewById<TextView>(R.id.sure_tv)
            sure.text = "确定接受"
            view.findViewById<LinearLayout>(R.id.cancel_btn)
                .setOnClickListener {
                    popup.dismiss()
                }
            view.findViewById<LinearLayout>(R.id.sure_btn)
                .setOnClickListener {  //去申请
                    popup.dismiss()
                    agreeCourse(1,"")
                }
        }
    }

    private fun toErrorJieshou(){
        PublicPopControl.alert_dialog_center(this) { view, popup ->
            val content = view.findViewById<TextView>(R.id.pop_content)
            val title = view.findViewById<TextView>(R.id.pop_title)
            content.setText("2019-09-12 13:00-14:00 已有课程，暂时无法接受该邀请。")
            title.setText("接受团课")
            view.findViewById<LinearLayout>(R.id.cancel_btn).visibility = View.GONE

            val sure = view.findViewById<TextView>(R.id.sure_tv)
            sure.text = "好的"
            view.findViewById<LinearLayout>(R.id.sure_btn)
                .setOnClickListener {  //去申请
                    popup.dismiss()
                }
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
        subscription = Network.getInstance("团课同意/拒绝", this)
            .agreeCourse(params,
                ProgressSubscriber("团课同意/拒绝", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
//                        requestData(courseNum)
                        if (type == 1){
                            EventBus.getDefault().post(AppConstants.TEAM_YQ_AGREE)
                        } else {
                            EventBus.getDefault().post(AppConstants.TEAM_YQ_REFUSE)
                        }
                        finish()
                    }

                    override fun onError(error: String) {
                        SuperCustomToast.getInstance(applicationContext)
                            .show(error)
                    }
                }, this, false)
            )
    }
}
