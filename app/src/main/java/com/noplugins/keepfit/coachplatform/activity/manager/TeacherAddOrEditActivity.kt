package com.noplugins.keepfit.coachplatform.activity.manager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.PopUpAdapter
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerTeamBean
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
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
    override fun initBundle(parms: Bundle?) {
        if (parms!=  null){
            val type = parms.getString("type")
            if (type!=null && type == "edit"){
                val courseNum = parms.getString("courseNum")
                if (courseNum != null) {
                    requestData(courseNum)
                }
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
        toQueren(btn_submit)
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
            submit()
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
    private fun setting(managerTeamBean: ManagerTeamBean){
        title_tv.text =  managerTeamBean.courseList.courseName

        edit_class_name.setText(managerTeamBean.courseList.courseName)
        tv_select_type.text =classType(managerTeamBean.courseList.classType)
        skillType = managerTeamBean.courseList.classType
        edit_price.setText(""+managerTeamBean.courseList.price)
        edit_jieshao.setText(""+managerTeamBean.courseList.courseDes)
        edit_shihe.setText(""+managerTeamBean.courseList.suitPerson)
        edit_zhuyi.setText(""+managerTeamBean.courseList.tips)
    }


    private fun classType(classType: Int): String {
        val listClass = resources.getStringArray(R.array.private_class_types)
        return listClass[classType - 1]
    }


}
