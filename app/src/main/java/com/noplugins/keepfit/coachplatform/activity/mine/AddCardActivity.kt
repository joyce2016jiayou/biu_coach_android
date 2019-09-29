package com.noplugins.keepfit.coachplatform.activity.mine

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nanchen.bankcardutil.BankInfoUtil
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.BankUtils
import com.noplugins.keepfit.coachplatform.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_add_card.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AddCardActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_add_card)
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
        tv_next.clickWithTrigger {
            if(et_card.text.toString() == ""){
                SuperCustomToast.getInstance(this)
                    .show("银行卡号不能为空")
                return@clickWithTrigger
            }
            if (BankUtils.checkBankCard(et_card.text.toString())){
                val intent = Intent(this,WriteCardActivity::class.java)
                val bundle = Bundle()
                bundle.putString("backCard",et_card.text.toString())
                intent.putExtras(bundle)
                startActivity(intent)
            } else{
                SuperCustomToast.getInstance(this)
                    .show("请输入正确的银行卡")
            }

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
