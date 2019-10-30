package com.noplugins.keepfit.coachplatform.activity.manager

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerBean
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerTeamBean
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.global.withTrigger
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_teacher_info.*
import java.util.HashMap

class TeacherInfoActivity : BaseActivity() {
    var courseNum = ""
    var type = -1
    override fun initBundle(parms: Bundle?) {
        if (parms != null) {
            courseNum = parms.getString("courseNum").toString()
            requestData(courseNum)
        }
    }

    override fun initView() {
        setContentView(R.layout.activity_teacher_info)
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger {
            finish()
        }
        tv_info_upOrDown.clickWithTrigger {
            //上架 or 下架
            if (type == -1){
                SuperCustomToast.getInstance(this)
                    .show("数据有误！")
                return@clickWithTrigger
            }
            if (type == 0){
                //已下架
                upAway(1)
            } else{
                upAway(0)
            }

        }

        tv_info_edit.clickWithTrigger {
            //编辑界面
            val toEdit = Intent(this, TeacherAddOrEditActivity::class.java)
            val bundle = Bundle()
            bundle.putString("type","edit")
            bundle.putString("courseNum",courseNum)
            toEdit.putExtras(bundle)
            startActivity(toEdit)

        }

    }

    private fun upAway(type:Int){
        val params = HashMap<String, Any>()
//        params["teacherNum"] = SpUtils.getString(activity, AppConstants.USER_NAME)
        params["courseNum"] = courseNum
        params["putaway"] = type
        val subscription = Network.getInstance("上架/下架 操作", this)
            .putaway(params,
                ProgressSubscriber("上架/下架 操作", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
                        //上架成功！
                        requestData(courseNum)
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }

    private fun requestData(courseNum:String){
        val params = HashMap<String, Any>()
        params["courseNum"] = courseNum
        params["custUserNum"] = SpUtils.getString(applicationContext, AppConstants.USER_NAME)
        subscription = Network.getInstance("私教课程管理", this)
            .findCourseDetail(params,
                ProgressSubscriber("私教课程管理", object : SubscriberOnNextListener<Bean<ManagerBean.CourseListBean >> {
                    override fun onNext(result: Bean<ManagerBean.CourseListBean >) {
                        setting(result.data)
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }
    private fun setting(managerTeamBean: ManagerBean.CourseListBean){
        title_tv.text =  managerTeamBean.courseName

        type = managerTeamBean.putaway
        if (managerTeamBean.putaway == 0){
            //下架
            tv_info_upOrDown.text = "上架"
            tv_type.text = "已下架"
        } else {
            tv_info_upOrDown.text = "下架"
            tv_type.text = "已上架"
        }

        edit_class_name.text = managerTeamBean.courseName
        tv_select_type.text =classType(managerTeamBean.classType)
        edit_price.text = "¥"+managerTeamBean.price
        edit_jieshao.text = ""+managerTeamBean.courseDes
        edit_shihe.text = ""+managerTeamBean.suitPerson
        edit_zhuyi.text = ""+managerTeamBean.tips
        tv_create_date.text = "创建时间："+managerTeamBean.createDate
    }


    private fun classType(classType: Int): String {
        val listClass = resources.getStringArray(R.array.private_class_types)
        return listClass[classType - 1]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data1: Intent?) {
        super.onActivityResult(requestCode, resultCode, data1)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //接受回来对数据的处理
                val isUpdate = data1!!.getBooleanExtra("isUpdate",false)
                if(isUpdate){
                    requestData(courseNum)
                }

            }
        }
    }


}
