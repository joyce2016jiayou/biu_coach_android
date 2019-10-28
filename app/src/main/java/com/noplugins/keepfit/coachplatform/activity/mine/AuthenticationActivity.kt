package com.noplugins.keepfit.coachplatform.activity.mine

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.widget.Toast
import com.nanchen.bankcardutil.BankInfoUtil
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.data.StringsHelper
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_authentication.*
import org.greenrobot.eventbus.EventBus
import java.util.HashMap

class AuthenticationActivity : BaseActivity() {
    private lateinit var card:BankInfoUtil
    private var cardNum = ""
    private var messageId = ""
    private var phone = ""
    override fun initBundle(parms: Bundle?) {
        if (parms != null) {
            cardNum = parms.getString("backCard").toString()
            phone = parms.getString("phone").toString()
            card = BankInfoUtil(cardNum)
//            tv_bank_name.text = card.bankName
//            tv_card_type.text = card.cardType
            tv_phone.text = "请输入 $phone 收到的短信验证码"
        }

    }

    override fun initView() {
        setContentView(R.layout.activity_authentication)
    }

    override fun doBusiness(mContext: Context?) {
        btn_complete.clickWithTrigger {
            binding()
        }
        back_btn.clickWithTrigger {
            finish()
        }

        tv_send.clickWithTrigger {
            tv_send.isEnabled = false//设置不可点击，等待60秒过后可以点击
            timer.start()
            //获取验证码接口
            Get_YanZhengMa()
        }
    }

    private fun Get_YanZhengMa() {
        val params = HashMap<String, Any>()
        params["phone"] = phone
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

    private fun binding(){
        val params = HashMap<String, Any>()
        params["coachNum"] = SpUtils.getString(this,AppConstants.USER_NAME)
//        params["coachNum"] ="CUS19091292977313"

        params["userName"] = SpUtils.getString(this,AppConstants.NAME)
        params["phone"] = phone
        params["messageId"] = messageId
        params["code"] = edit_yzm.text.toString()
        params["cardNum"] = cardNum
        params["cardName"] = card.bankName
        subscription = Network.getInstance("绑定银行卡", this)
            .bindingCoachBank(params,
                ProgressSubscriber("绑定银行卡", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
                        EventBus.getDefault().post("添加银行卡成功")
                        SuperCustomToast.getInstance(this@AuthenticationActivity)
                            .show("绑定银行卡成功！")
                        finish()
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }

    internal var timer: CountDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            tv_send.setTextColor(Color.parseColor("#7B7B7B"))
            tv_send.text = "重新发送(" + millisUntilFinished / 1000 + "s)"

        }

        override fun onFinish() {
            tv_send.setTextColor(Color.parseColor("#292C31"))
            tv_send.text = "重新发送"
            tv_send.isEnabled = true
        }
    }


}
