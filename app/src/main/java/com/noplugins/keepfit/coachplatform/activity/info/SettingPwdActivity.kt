package com.noplugins.keepfit.coachplatform.activity.info

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.google.gson.Gson
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.data.PwdCheckUtil
import com.noplugins.keepfit.coachplatform.util.data.StringsHelper
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.GsonSubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriberNew
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_setting_pwd.*
import okhttp3.RequestBody
import java.util.*

class SettingPwdActivity : BaseActivity() {
    private var messageId = ""
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_setting_pwd)
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger {
            finish()
        }
        btn_pwd_update.clickWithTrigger {
            if (edit_phone.text.toString() == "") {
                Toast.makeText(applicationContext, "电话号码不能为空！", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }
            if (!StringsHelper.isMobileNO(edit_phone.text.toString())) {
                Toast.makeText(applicationContext, "电话号码格式不正确！", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }
            if (edit_yzm.text.toString() == "") {
                Toast.makeText(applicationContext, "验证码不能空！", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }
            if (edit_new_password.text.toString() == "") {
                Toast.makeText(applicationContext, "密码不能为空！", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }
            if (!PwdCheckUtil.isLetterDigit(edit_new_password.text.toString())) {
                Toast.makeText(applicationContext, "密码不符合规则！", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }
            if (edit_new_password1.text.toString() == "") {
                Toast.makeText(applicationContext, "确认密码不能为空！", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }
            if (edit_new_password1.text.toString() != edit_new_password.text.toString()) {
                Toast.makeText(applicationContext, "两次输入的密码不一致！", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }
            settingPwd()
        }

        tv_send.clickWithTrigger {
            if (edit_phone.text.toString() == "") {
                Toast.makeText(applicationContext, "电话号码不能为空！", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }
            if (!StringsHelper.isMobileNO(edit_phone.text.toString())) {
                Toast.makeText(applicationContext, "电话号码格式不正确！", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }
            tv_send.isEnabled = false//设置不可点击，等待60秒过后可以点击
            timer.start()
            //获取验证码接口
            send()
        }
    }


    private fun settingPwd() {
        val params = HashMap<String, String>()
        params["phone"] = edit_phone.text.toString()
        params["messageId"] = messageId
        params["verifyCode"] = edit_yzm.text.toString()
        params["password"] = edit_new_password.text.toString()
        val json = Gson().toJson(params)
        val requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

//        val subscription = Network.getInstance("修改密码", this)
//            .settingPassword(
//                null,
//                ProgressSubscriber<String>(
//                    "修改密码",
//                    object : SubscriberOnNextListener<Bean<String>> {
//                        override fun onNext(result: Bean<String>) {
//
//                        }
//
//                        override fun onError(error: String) {
//
//                        }
//                    },
//                    this,
//                    true
//                )
//            )
    }

    private fun send() {
        val params = HashMap<String, String>()
        params["phone"] = edit_phone.text.toString()
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
//                        Toast.makeText(applicationContext, "接收验证码失败！", Toast.LENGTH_SHORT).show()
//                    }
//                }, this, true)
//            )
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
