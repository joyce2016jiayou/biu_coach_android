package com.noplugins.keepfit.coachplatform.activity.mine

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.activity.CheckStatusActivity
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.bean.CheckResultBean
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.activity_instructor_type.*
import java.util.HashMap

class InstructorTypeActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_instructor_type)
        tv_apply.text = "申请团课教练"
        requsetData()
     }

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger {
            finish()
        }
        tv_apply.clickWithTrigger {
            //申请操作 跳转到教练入住
            val intent = Intent(this@InstructorTypeActivity, CheckStatusActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("fragment_type",2)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }
    }


    private fun setting(bean:CheckResultBean){
        when(bean.teacherType){
            1->{
                iv_class_type.setImageResource(R.drawable.class_2)
                tv_class_type.text = "团课教练"
                tv_apply.visibility = View.VISIBLE
                tv_apply.text ="申请私人教练"
            }
            2->{
                iv_class_type.setImageResource(R.drawable.class_1)
                tv_class_type.text = "私人教练"
                tv_apply.visibility = View.VISIBLE
                tv_apply.text ="申请团课教练"
            }
            3->{
                iv_class_type.setImageResource(R.drawable.class_2)
                tv_class_type.text = "团课教练"
                to_dier.visibility = View.VISIBLE
                iv_class_type_2.setImageResource(R.drawable.class_1)
                tv_class_type_2.text = "私人教练"
                tv_apply.visibility = View.GONE
            }
        }

        when(bean.checkType){
            1 -> {
                //
                tv_apply.visibility = View.VISIBLE
                tv_apply.text ="团课申请中"
            }
            2 -> {
                tv_apply.visibility = View.VISIBLE
                tv_apply.text ="私教申请中"
            }
        }
    }
    private fun requsetData() {
        val params = HashMap<String, Any>()
        params["userNum"] = SpUtils.getString(this, AppConstants.USER_NAME)

        subscription = Network.getInstance("教练类型管理", this)
            .getCheckResult(
                params,
                ProgressSubscriber("教练类型管理", object : SubscriberOnNextListener<Bean<CheckResultBean>> {
                    override fun onNext(result: Bean<CheckResultBean>) {
                        //申请成功
                        setting(result.data)
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }
}
