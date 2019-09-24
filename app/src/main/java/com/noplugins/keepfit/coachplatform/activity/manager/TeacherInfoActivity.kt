package com.noplugins.keepfit.coachplatform.activity.manager

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerTeamBean
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.global.withTrigger
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
                ProgressSubscriber("上架/下架 操作", object : SubscriberOnNextListener<Bean<String>> {
                    override fun onNext(result: Bean<String>) {
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

        type = managerTeamBean.courseList.putaway
        if (managerTeamBean.courseList.putaway == 0){
            //下架
            tv_info_upOrDown.text = "上架"
            tv_type.text = "已下架"
        } else {
            tv_info_upOrDown.text = "下架"
            tv_type.text = "已上架"
        }

        edit_class_name.text = managerTeamBean.courseList.courseName
        tv_select_type.text =classType(managerTeamBean.courseList.classType)
        edit_price.text = "¥"+managerTeamBean.courseList.price
        edit_jieshao.text = ""+managerTeamBean.courseList.courseDes
        edit_shihe.text = ""+managerTeamBean.courseList.suitPerson
        edit_zhuyi.text = ""+managerTeamBean.courseList.tips
        tv_create_date.text = "创建时间："+managerTeamBean.courseList.createDate
    }


    private fun classType(classType: Int): String {
        val listClass = resources.getStringArray(R.array.private_class_types)
        return listClass[classType - 1]
    }


}
