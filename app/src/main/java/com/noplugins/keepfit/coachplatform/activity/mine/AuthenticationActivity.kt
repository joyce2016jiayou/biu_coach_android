package com.noplugins.keepfit.coachplatform.activity.mine

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import kotlinx.android.synthetic.main.activity_authentication.*
import org.greenrobot.eventbus.EventBus

class AuthenticationActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_authentication)
    }

    override fun doBusiness(mContext: Context?) {
        btn_complete.clickWithTrigger {
            EventBus.getDefault().post("添加银行卡成功")
            this.finish()
        }
        back_btn.clickWithTrigger {
            finish()
        }
    }

}
