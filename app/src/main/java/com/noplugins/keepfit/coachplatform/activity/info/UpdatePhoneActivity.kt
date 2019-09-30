package com.noplugins.keepfit.coachplatform.activity.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.data.StringsHelper
import kotlinx.android.synthetic.main.activity_update_phone.*

class UpdatePhoneActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_update_phone)
        tv_now_phone.text = "目前的手机号:"+SpUtils.getString(applicationContext, AppConstants.PHONE)
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger {
            finish()
        }
        btn_next.clickWithTrigger{
            if (edit_phone.text.toString() == "") {
                Toast.makeText(applicationContext, "电话号码不能为空！", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }
            if (!StringsHelper.isMobileNO(edit_phone.text.toString())) {
                Toast.makeText(applicationContext, "电话号码格式不正确！", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }
            val intent = Intent(this,VerificationPhoneActivity::class.java)
            intent.putExtra("newPhone",edit_phone.text.toString().trim())
            intent.putExtra("update",true)
            startActivity(intent)
        }
    }
}
