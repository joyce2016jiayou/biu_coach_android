package com.noplugins.keepfit.coachplatform.activity.mine

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nanchen.bankcardutil.BankInfoUtil
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import kotlinx.android.synthetic.main.activity_write_card.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class WriteCardActivity : BaseActivity() {
     private lateinit var card:BankInfoUtil
    private var cardNum = ""
    override fun initBundle(parms: Bundle?) {
        if (parms != null) {
            cardNum = parms.getString("backCard").toString()
            card = BankInfoUtil(cardNum)
            tv_bank_name.text = card.bankName
            tv_card_type.text = card.cardType
        }

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
        if (data == "添加银行卡成功") {
            this.finish()
        }
    }


    override fun doBusiness(mContext: Context?) {
        tv_binding.clickWithTrigger {
            val intent = Intent(this, AuthenticationActivity::class.java)
            val bundle = Bundle()
            bundle.putString("backCard",cardNum)
            bundle.putString("phone",et_phone.text.toString())
            intent.putExtras(bundle)
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
