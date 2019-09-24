package com.noplugins.keepfit.coachplatform.activity.mine

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.activity_withdraw.*

class WithdrawActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_withdraw)
        //超过1000元可以提现
        val ss = SpannableString("超过1000元可以提现")//定义hint的值
        val ass = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        et_withdraw_money.hint = SpannedString(ss)
    }

    override fun doBusiness(mContext: Context?) {
        rl_money_details.clickWithTrigger {
            toSelectCard(rl_money_details)
        }
    }


    private fun toSelectCard(view1: View) {
        val popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.selext_card_pop)
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
        val addCard = view.findViewById<LinearLayout>(R.id.ll_add_card)
        addCard.setOnClickListener {
            popupWindow.dismiss()
        }
    }
}