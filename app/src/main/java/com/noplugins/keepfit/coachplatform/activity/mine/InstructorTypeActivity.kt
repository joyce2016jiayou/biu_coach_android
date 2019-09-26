package com.noplugins.keepfit.coachplatform.activity.mine

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import kotlinx.android.synthetic.main.activity_instructor_type.*

class InstructorTypeActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_instructor_type)
        tv_apply.text = "申请团课教练"
     }

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger {
            finish()
        }
        tv_apply.clickWithTrigger {
            //todo 申请操作
        }
    }
}
