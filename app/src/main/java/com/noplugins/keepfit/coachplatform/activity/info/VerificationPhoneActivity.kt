package com.noplugins.keepfit.coachplatform.activity.info

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.activity.LoginActivity
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.ActivityCollectorUtil
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.GsonSubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriberNew
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_verification_phone.*
import okhttp3.RequestBody
import java.util.*

class VerificationPhoneActivity : BaseActivity() {
    var messageId = ""
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_verification_phone)
//        tv_now_phone.text = SpUtils.getString(applicationContext, AppConstants.PHONE)
        tv_phone.text = intent.getStringExtra("newPhone")
    }

    override fun doBusiness(mContext: Context?) {
        tv_send.setOnClickListener {
            tv_send.isEnabled = false//设置不可点击，等待60秒过后可以点击
            timer.start()
            //获取验证码接口
            send()
        }
        btn_ToLogin.clickWithTrigger {
            val intent = Intent(this,UpdatePasswordActivity::class.java)
            startActivity(intent)
            finish()
//            if(tv_btn_text.text == ""){
//                //去验证
//
//            }
//            updatePhone()
        }
    }

    private fun send() {
        val params = HashMap<String, Any>()
        params["phone"] = tv_phone.text.toString()
        val json = Gson().toJson(params)
        val requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

//        subscription = Network.getInstance("接收验证码", applicationContext)
//            .get_yanzhengma(
//                requestBody,
//                ProgressSubscriberNew(String::class.java, object : GsonSubscriberOnNextListener<String> {
//                    override fun on_post_entity(code: String, get_message_id: String) {
//                        Toast.makeText(applicationContext, "发送成功", Toast.LENGTH_SHORT).show()
//                        messageId = code
//                    }
//                }, object : SubscriberOnNextListener<Bean<Any>> {
//                    override fun onNext(result: Bean<Any>) {
//
//                    }
//
//                    override fun onError(error: String) {
//                        Logger.e(TAG, "接收验证码报错：$error")
//                        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
//                    }
//                }, this, true)
//            )
    }
    private fun updatePhone() {
        if (tv_send.text.toString() == "") {
            Toast.makeText(applicationContext, "验证码不能为空！", Toast.LENGTH_SHORT).show()
            return
        }
        val params = HashMap<String, Any>()
        params["phone"] = tv_phone.text.toString()
        params["messageId"] = messageId
        params["verifyCode"] = edit_yzm.text.toString()
        params["userNum"] = SpUtils.getString(applicationContext,AppConstants.USER_NAME)
        val json = Gson().toJson(params)
        val requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

//        subscription = Network.getInstance("修改手机号", this)
//            .modificationPhone(
//                requestBody,
//                ProgressSubscriber<String>(
//                    "修改手机号",
//                    object : SubscriberOnNextListener<Bean<String>> {
//                        override fun onNext(result: Bean<String>) {
//                            toLogin()
//                        }
//
//                        override fun onError(error: String) {
//                            Log.e(TAG, "修改失败：" +error)
//                            Toast.makeText(applicationContext,error, Toast.LENGTH_SHORT).show()
//                        }
//                    },
//                    this,
//                    true
//                )
//            )
    }


    private fun toLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        //退出
        SpUtils.putString(applicationContext, AppConstants.TOKEN, "")
        SpUtils.putString(applicationContext, AppConstants.PHONE, "")
        SpUtils.putString(applicationContext, AppConstants.USER_NAME, "")
        startActivity(intent)
        ActivityCollectorUtil.finishAllActivity()

    }
    internal var timer: CountDownTimer = object : CountDownTimer(60000, 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            tv_send.setTextColor(Color.parseColor("#292C31"))
            tv_send.text = "已发送(${millisUntilFinished / 1000})"

        }

        override fun onFinish() {
            tv_send.setTextColor(Color.parseColor("#FFBA02"))
            tv_send.text = "重新获取"
            tv_send.isEnabled = true
        }
    }
}
