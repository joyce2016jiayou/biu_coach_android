package com.noplugins.keepfit.coachplatform.activity.manager

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.PopUpAdapter
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerBean
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerTeamBean
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow
import com.noplugins.keepfit.coachplatform.util.ui.pop.SpinnerPopWindow
import com.noplugins.keepfit.coachplatform.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_teacher_add_or_edit.*
import java.util.HashMap

class TeacherAddOrEditActivity : BaseActivity() {

    private var skillType = -1
    private var courseNum = ""
    private var intentType = -1
    override fun initBundle(parms: Bundle?) {
        if (parms!=  null){
            val type = parms.getString("type")
            if (type == "edit"){
                val courseNum = parms.getString("courseNum")
                intentType = 1
                if (courseNum != null) {
                    requestData(courseNum)
                }
            }  else{
                title_tv.text = "新增私教课程"
            }
        }
    }

    override fun initView() {
        setContentView(R.layout.activity_teacher_add_or_edit)

        val listClass = resources.getStringArray(R.array.private_class_types).toMutableList()
        popWindow = SpinnerPopWindow(this,
            listClass,
            PopUpAdapter.OnItemClickListener { _, _, position ->
                tv_select_type.text = listClass[position]
                skillType = position + 1
                popWindow!!.dismiss()
            })
    }
    private fun showPopwindow(pop: SpinnerPopWindow<String>, view: TextView){
        pop.width = view.width
        pop.showAsDropDown(view)

    }
    private var popWindow: SpinnerPopWindow<String>? = null

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger {
            finish()
        }

        btn_submit.clickWithTrigger {
            //提交
            submit()


        }
        tv_select_type.clickWithTrigger {
            showPopwindow(popWindow!!,tv_select_type)
        }
    }

    private fun submit(){
        if(edit_class_name.text.toString() == ""){
            SuperCustomToast.getInstance(this)
                .show("课程名称不能为空！")
            return
        }
        if(skillType == -1){
            SuperCustomToast.getInstance(this)
                .show("课程类型不能为空！")
            return
        }
        if (edit_price.text.toString() == "" || edit_price.text.toString().toDouble()< 200){
            SuperCustomToast.getInstance(this)
                .show("价格不能为空且不能低于200！")
            return
        }

        if(edit_jieshao.text.toString() == ""){
            SuperCustomToast.getInstance(this)
                .show("课程介绍不能为空！")
            return
        }
        if(edit_shihe.text.toString() == ""){
            SuperCustomToast.getInstance(this)
                .show("适合人群不能为空！")
            return
        }
        if(edit_zhuyi.text.toString() == ""){
            SuperCustomToast.getInstance(this)
                .show("注意事项不能为空！")
            return
        }

        if (intentType == 1){
            updateCourse()
        } else {
            toQueren(btn_submit)
        }

    }

    private fun toQueren(view1: TextView) {
        val popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.dialog_to_submit)
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
        val type = view.findViewById<TextView>(R.id.tv_class_type)
        type.text = tv_select_type.text
        val name = view.findViewById<TextView>(R.id.tv_class_name)
        name.text = edit_class_name.text.toString()
        val price = view.findViewById<TextView>(R.id.tv_class_price)
        price.text = "¥${edit_price.text}"
        cancel.setOnClickListener {
            popupWindow.dismiss()
        }
        sure.setOnClickListener {
            popupWindow.dismiss()
            //去申请
            requestAddInfo()
        }
    }


    private fun requestAddInfo(){
        /**
         * "gen_teacher_num":"GEN23456",
        "price":200,
        "course_name":"一起做运动",
        "class_type":3,
        "course_des":"一起运动介绍",
        "tips":"注意注意",
        "suit_person":"所有人"，
        "course_type":2
         */
        val params = HashMap<String, Any>()
        params["gen_teacher_num"] = SpUtils.getString(applicationContext,AppConstants.USER_NAME)
        params["price"] = edit_price.text.toString()
        params["course_name"] = edit_class_name.text.toString()
        params["class_type"] = skillType
        params["course_des"] = edit_jieshao.text.toString()
        params["suit_person"] = edit_shihe.text.toString()
        params["tips"] = edit_jieshao.text.toString()
        params["course_type"] = 2
        subscription = Network.getInstance("添加课程", this)
            .addTeacherCourse(params,
                ProgressSubscriber("添加课程", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
                        finish()
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }

    private fun updateCourse(){
        val params = HashMap<String, Any>()
        params["course_num"] = courseNum
        params["price"] = edit_price.text.toString()
        params["course_name"] = edit_class_name.text.toString()
        params["class_type"] = skillType
        params["course_des"] = edit_jieshao.text.toString()
        params["tips"] = edit_shihe.text.toString()
        params["edit_zhuyi"] = edit_jieshao.text.toString()
        subscription = Network.getInstance("修改课程", this)
            .updateCourse(params,
                ProgressSubscriber("修改课程", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
                        val mIntent = Intent()//没有任何参数（意图），只是用来传递数据
                        mIntent.putExtra("isUpdate", true)
                        setResult(RESULT_OK, mIntent)
                        finish()
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }

    private fun requestData(courseNum:String){
        val params = HashMap<String, Any>()
        params["courseNum"] = courseNum
        subscription = Network.getInstance("私教课程管理", this)
            .findCourseDetail(params,
                ProgressSubscriber("私教课程管理", object : SubscriberOnNextListener<Bean<ManagerBean.CourseListBean>> {
                    override fun onNext(result: Bean<ManagerBean.CourseListBean>) {
                        setting(result.data)
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }
    private fun setting(managerTeamBean: ManagerBean.CourseListBean){
        title_tv.text =  managerTeamBean.courseName
        courseNum = managerTeamBean.courseNum
        edit_class_name.setText(managerTeamBean.courseName)
        tv_select_type.text =classType(managerTeamBean.classType)
        skillType = managerTeamBean.classType
        edit_price.setText(""+managerTeamBean.price)
        edit_jieshao.setText(""+managerTeamBean.courseDes)
        edit_shihe.setText(""+managerTeamBean.suitPerson)
        edit_zhuyi.setText(""+managerTeamBean.tips)
    }


    private fun classType(classType: Int): String {
        val listClass = resources.getStringArray(R.array.private_class_types)
        return listClass[classType - 1]
    }


}
