package com.noplugins.keepfit.coachplatform.activity.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.activity.LoginActivity
import com.noplugins.keepfit.coachplatform.activity.mine.InstructorTypeActivity
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.ActivityCollectorUtil
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_setting)
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger(1000) {
            finish()
        }
        rl_mine_info.clickWithTrigger(1000) {
            val intent = Intent(this,InformationActivity::class.java)
            startActivity(intent)

        }
        rl_account.clickWithTrigger(1000) {
            val intent = Intent(this,AccountSecurityActivity::class.java)
            startActivity(intent)

        }
        rl_about.clickWithTrigger(1000) {
            val intent = Intent(this,AboutActivity::class.java)
            startActivity(intent)

        }
        rl_risk.clickWithTrigger(1000) {

        }
        rl_xieyi.clickWithTrigger(1000) {

        }
        rl_quit.clickWithTrigger(1000) {
            toQuit(rl_quit)
//            toPhone()
        }
        rl_teacher_manager.clickWithTrigger {
            val intent = Intent(this, InstructorTypeActivity::class.java)
            startActivity(intent)
        }

    }


    private fun toQuit(view1: View) {
        val popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.dialog_to_quit)
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
            val intent = Intent(this, LoginActivity::class.java)
            //退出
            SpUtils.putString(applicationContext, AppConstants.TOKEN,"")
            SpUtils.putString(applicationContext,AppConstants.PHONE,"")
            SpUtils.putString(applicationContext,AppConstants.USER_NAME,"")

            startActivity(intent)
            ActivityCollectorUtil.finishAllActivity()
        }
    }


}
