package com.noplugins.keepfit.coachplatform.activity.info

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.activity.LoginActivity
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.ActivityCollectorUtil
import com.noplugins.keepfit.coachplatform.util.MD5Utils
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.data.PwdCheckUtil
import com.noplugins.keepfit.coachplatform.util.data.StringsHelper
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_setting_pwd.*
import java.util.*

class SettingPwdActivity : BaseActivity() {
    private var messageId = ""
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_setting_pwd)
        edit_phone.setText(SpUtils.getString(applicationContext,AppConstants.PHONE))
        edit_phone.isFocusable = false
        edit_phone.isFocusableInTouchMode = false
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
//            if (!PwdCheckUtil.isLetterDigit(edit_new_password.text.toString())) {
//                Toast.makeText(applicationContext, "密码不符合规则！", Toast.LENGTH_SHORT).show()
//                return@clickWithTrigger
//            }
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
        val params = HashMap<String, Any>()
        params["phone"] = edit_phone.text.toString()
        params["messageId"] = messageId
        params["code"] = edit_yzm.text.toString()
        params["password"] = edit_new_password.text.toString()
        subscription = Network.getInstance("设置密码", this)
            .forgetPassword(params,
                ProgressSubscriber("设置密码", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
                        toLogin()
                    }

                    override fun onError(error: String) {
                        SuperCustomToast.getInstance(applicationContext)
                            .show(error)
                    }
                }, this, false)
            )
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

    private fun send() {
        val params = HashMap<String, Any>()
        params["phone"] = edit_phone.text.toString()
        params["sign"] = "MES${MD5Utils.stringToMD5(edit_phone.text.toString())}"
        params["time"] = System.currentTimeMillis()
        subscription = Network.getInstance("获取验证码", this)
            .get_yanzhengma(params,
                ProgressSubscriber("获取验证码", object : SubscriberOnNextListener<Bean<String>> {
                    override fun onNext(result: Bean<String>) {
                        messageId = result.data
                    }

                    override fun onError(error: String) {

                    }
                }, this, false)
            )
    }

    private var timer: CountDownTimer = object : CountDownTimer(60000, 1000) {
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
