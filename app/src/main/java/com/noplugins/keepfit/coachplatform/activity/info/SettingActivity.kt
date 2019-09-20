package com.noplugins.keepfit.coachplatform.activity.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
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

//            toPhone()
        }

    }


}
