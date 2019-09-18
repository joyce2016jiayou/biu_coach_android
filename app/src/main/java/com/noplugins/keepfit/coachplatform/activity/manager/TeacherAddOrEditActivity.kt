package com.noplugins.keepfit.coachplatform.activity.manager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.PopUpAdapter
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.ui.pop.SpinnerPopWindow
import kotlinx.android.synthetic.main.activity_teacher_add_or_edit.*

class TeacherAddOrEditActivity : BaseActivity() {

    private var skillType = -1
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_teacher_add_or_edit)

        val listClass = resources.getStringArray(R.array.private_class_types).toMutableList()
        popWindow = SpinnerPopWindow(this,
            listClass,
            PopUpAdapter.OnItemClickListener { _, _, position ->
                tv_select_type.text = listClass[position]
                skillType = position + 1
                popWindow!!.dismiss()
            })
    }
    private fun showPopwindow(pop: SpinnerPopWindow<String>, view: TextView){
        pop.width = view.width
        pop.showAsDropDown(view)

    }
    private var popWindow: SpinnerPopWindow<String>? = null

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger {
            finish()
        }

        btn_submit.clickWithTrigger {
            //提交
            submit()

        }
        tv_select_type.clickWithTrigger {
            showPopwindow(popWindow!!,tv_select_type)
        }
    }

    private fun submit(){
        if(edit_class_name.text.toString() == ""){
            return
        }
        if (edit_price.text.toString() == "" || edit_price.text.toString().toDouble()< 200){
            return
        }

        if(edit_jieshao.text.toString() == ""){
            return
        }
        if(edit_shihe.text.toString() == ""){
            return
        }
        if(edit_zhuyi.text.toString() == ""){
            return
        }
    }

}
