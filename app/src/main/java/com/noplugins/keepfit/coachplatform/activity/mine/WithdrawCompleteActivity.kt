package com.noplugins.keepfit.coachplatform.activity.mine

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import kotlinx.android.synthetic.main.activity_withdraw_complete.*
import org.greenrobot.eventbus.EventBus

class WithdrawCompleteActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_withdraw_complete)
    }

    override fun doBusiness(mContext: Context?) {
        tv_complete.clickWithTrigger {
            killMe()
        }
    }

    override fun onBackPressed() {
        killMe()
    }

    private fun killMe(){
        finish()
    }
}
