package com.noplugins.keepfit.coachplatform.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.activity.manager.ClassShouquanActivity
import com.noplugins.keepfit.coachplatform.adapter.TabItemAdapter
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.fragment.cg.BindingFragment
import com.noplugins.keepfit.coachplatform.fragment.cg.JujueFragment
import com.noplugins.keepfit.coachplatform.fragment.cg.SqAndYaoqinFragment
import com.noplugins.keepfit.coachplatform.fragment.classmanager.team.HistoryFragment
import com.noplugins.keepfit.coachplatform.fragment.classmanager.team.ShangjiaFragment
import com.noplugins.keepfit.coachplatform.fragment.classmanager.team.YaoqinFragment
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import kotlinx.android.synthetic.main.activity_shouke_cg.*
import java.util.ArrayList

class ShoukeCgActivity : BaseActivity() {

    private val mFragments = ArrayList<Fragment>()
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_shouke_cg)
        initFragment()
    }

    override fun doBusiness(mContext: Context?) {
        rbOnClick()
        tv_complete.clickWithTrigger {
            val intent = Intent(this,ClassShouquanActivity::class.java)
            startActivity(intent)
        }
        back_btn.clickWithTrigger {
            finish()
        }
    }

    private fun rbOnClick(){
        rb_1.setOnClickListener {
            Log.d("单选","点击了1")
            view_pager.currentItem = 0
        }
        rb_2.setOnClickListener {
            Log.d("单选","点击了2")
            view_pager.currentItem = 1
        }
        rb_3.setOnClickListener {
            Log.d("单选","点击了3")
            view_pager.currentItem = 2
        }
    }

    private fun initFragment(){
        mFragments.add(BindingFragment.newInstance("已绑定"))
        mFragments.add(SqAndYaoqinFragment.newInstance("申请中/邀请中"))
        mFragments.add(JujueFragment.newInstance("已拒绝"))

        val myAdapter = TabItemAdapter(supportFragmentManager, mFragments)// 初始化adapter
        view_pager.adapter = myAdapter // 设置adapter
        view_pager.currentItem = 0
        setTabTextColorAndImageView(0)// 更改text的颜色还有图片
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPs: Int) {}

            override fun onPageSelected(position: Int) {
                setTabTextColorAndImageView(position)// 更改text的颜色还有图片
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun setTabTextColorAndImageView(position: Int) {
        when(position){
            0 ->rb_1.isChecked = true
            1 ->rb_2.isChecked = true
            2 ->rb_3.isChecked = true
        }
    }


}
