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
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.global.withTrigger
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.activity_wallet.*

class WalletActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_wallet)
    }

    override fun doBusiness(mContext: Context?) {
        tv_pay_mx.clickWithTrigger(1000) {
            //跳转到明细
            val intent = Intent(this, BillDetailActivity::class.java)
            startActivity(intent)
        }
        back_btn.clickWithTrigger {
            finish()
        }
        btn_tixian.clickWithTrigger(1000) {
            //跳转到提现
//            toQueren(btn_tixian)
            val intent = Intent(this,WithdrawActivity::class.java)
            startActivity(intent)
        }
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
}
