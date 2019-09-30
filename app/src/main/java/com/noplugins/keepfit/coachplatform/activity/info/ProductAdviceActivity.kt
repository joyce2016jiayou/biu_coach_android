package com.noplugins.keepfit.coachplatform.activity.info

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.google.gson.Gson
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.data.SharedPreferencesHelper
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.activity_product_advice.*
import okhttp3.RequestBody

import java.util.HashMap

class ProductAdviceActivity : BaseActivity() {


    private var type = -1

    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentLayout(R.layout.activity_product_advice)
    }

    override fun doBusiness(mContext: Context) {
        cb_product_suggest.setOnClickListener(View.OnClickListener {
            if (cb_product_suggest.isChecked()) {
                type = 1
                cb_fault_feedback.setChecked(false)
                cb_other.setChecked(false)
                edit_content.setHint("对现有功能不满意？有新的想法？快点告诉我们吧！")
            } else {
                type = 0
            }
        })
        cb_fault_feedback.setOnClickListener(View.OnClickListener {
            if (cb_fault_feedback.isChecked()) {
                type = 2
                cb_product_suggest.setChecked(false)
                cb_other.setChecked(false)
                edit_content.setHint("请详细描述故障")
            } else {
                type = 0
            }
        })
        cb_other.setOnClickListener(View.OnClickListener {
            if (cb_other.isChecked()) {
                type = 3
                cb_fault_feedback.setChecked(false)
                cb_product_suggest.setChecked(false)
                edit_content.setHint("")
            } else {
                type = 0
            }
        })


        back_btn.setOnClickListener(View.OnClickListener { finish() })

        add_class_teacher_btn.setOnClickListener(View.OnClickListener {
            //
            if (type == 0) {
                Toast.makeText(applicationContext, "请选择反馈类型", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            submit()
        })
    }

    private fun submit() {
        //
        val params = HashMap<String, Any>()
        params["coachNum"] = SpUtils.getString(this,AppConstants.USER_NAME)
        params["feedbackType"] = "" + type
        params["feedbackDes"] = edit_content.text.toString()

        subscription = Network.getInstance("反馈", this)
            .feedBackData(params,
                ProgressSubscriber("反馈", object : SubscriberOnNextListener<Bean<String>> {
                    override fun onNext(result: Bean<String>) {
                        finish()
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )

    }
}
