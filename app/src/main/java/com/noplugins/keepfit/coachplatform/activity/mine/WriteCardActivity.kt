package com.noplugins.keepfit.coachplatform.activity.mine

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import kotlinx.android.synthetic.main.activity_write_card.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class WriteCardActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_write_card)
        EventBus.getDefault().register(this)
    }

    /**
     * eventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEvent(data: String) {
        if (data == "添加银行卡成功"){
            this.finish()
        }
    }


    override fun doBusiness(mContext: Context?) {
        tv_binding.clickWithTrigger {
            val intent = Intent(this,AuthenticationActivity::class.java)
            startActivity(intent)
        }
        back_btn.clickWithTrigger {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
