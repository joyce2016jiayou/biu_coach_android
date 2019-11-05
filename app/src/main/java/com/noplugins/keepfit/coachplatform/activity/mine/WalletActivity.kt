package com.noplugins.keepfit.coachplatform.activity.mine

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.activity.info.VerificationPhoneActivity
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.bean.WalletBean
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.global.withTrigger
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.activity_wallet.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.HashMap

class WalletActivity : BaseActivity() {
    private var walletNum = ""
    private var finalCanWithdraw = 0.0
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_wallet)
        EventBus.getDefault().register(this)
        requestData()
    }

    override fun doBusiness(mContext: Context?) {
        tv_pay_mx.clickWithTrigger(1000) {
            //跳转到明细
            val intent = Intent(this, BillDetailActivity::class.java)
            intent.putExtra("walletNum",walletNum)
            startActivity(intent)
        }
        back_btn.clickWithTrigger {
            finish()
        }
        btn_tixian.clickWithTrigger(1000) {
            if (SpUtils.getInt(applicationContext,AppConstants.IS_TX) == 0){
                val intent = Intent(this, WithdrawActivity::class.java)
                val bundle = Bundle()
                bundle.putDouble("finalCanWithdraw",finalCanWithdraw)
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                //跳转到提现
                toQueren(btn_tixian)
            }
        }
     }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun upadate(messageEvent:String ) {
        if ("提现了金额" == messageEvent){
            requestData()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    private fun toQueren(view1: TextView) {
        val popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.dialog_to_setting)
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
        cancel.setOnClickListener {
            popupWindow.dismiss()
        }
        sure.setOnClickListener {
            popupWindow.dismiss()
            //去申请
            toSetting()
        }
    }

    private fun toSetting(){
        val intent = Intent(this,VerificationPhoneActivity::class.java)
        startActivity(intent)
    }

    private fun setting(bean:WalletBean){
        tv_balance.text = "¥ ${bean.finalBalance}"
        tv_sum_sr.text = "¥ ${bean.finalIncome}"
        tv_day_sr.text = "¥ ${bean.finaltodayIncome}"
        tv_month_sr.text = "¥ ${bean.finalmonthIncome}"
        tv_sum_withdraw.text = "¥ ${bean.finalWithdraw}"
        tv_now_withdraw.text = "¥ ${bean.finalCanWithdraw}"
        walletNum = bean.walletNum
        finalCanWithdraw = bean.finalCanWithdraw
    }

    //myBalance
    private fun requestData() {
        val params = HashMap<String, Any>()
        params["userNum"] = SpUtils.getString(this, AppConstants.USER_NAME)
//        params["userNum"] = "CUS19091292977313"
        val subscription = Network.getInstance("我的钱包", this)
            .myBalance(
                params,
                ProgressSubscriber("我的钱包", object : SubscriberOnNextListener<Bean<WalletBean>> {
                    override fun onNext(result: Bean<WalletBean>) {
                        setting(result.data)

                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }
}
