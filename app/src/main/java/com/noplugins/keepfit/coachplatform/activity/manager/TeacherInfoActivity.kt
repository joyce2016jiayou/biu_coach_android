package com.noplugins.keepfit.coachplatform.activity.manager

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.global.withTrigger
import kotlinx.android.synthetic.main.activity_teacher_info.*

class TeacherInfoActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_teacher_info)
        setting()
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger {
            finish()
        }
        tv_info_upOrDown.clickWithTrigger {
            //todo 上架 or 下架
        }

        tv_info_edit.clickWithTrigger {
            //todo 编辑界面
            val toEdit = Intent(this, TeacherAddOrEditActivity::class.java)
            startActivity(toEdit)

        }

    }

    private fun setting(){
        title_tv.text = ""
        tv_type.text = ""
        tv_info_upOrDown.text = ""
        tv_create_date.text = "创建时间："
    }

}
