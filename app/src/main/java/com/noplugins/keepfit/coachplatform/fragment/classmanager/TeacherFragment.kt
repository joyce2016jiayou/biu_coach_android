package com.noplugins.keepfit.coachplatform.fragment.classmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_manager_teacher.*
import androidx.viewpager.widget.ViewPager
import com.noplugins.keepfit.coachplatform.activity.manager.ClassShouquanActivity
import com.noplugins.keepfit.coachplatform.adapter.TabItemAdapter
import com.noplugins.keepfit.coachplatform.fragment.classmanager.teacher.SJDownFragment
import com.noplugins.keepfit.coachplatform.fragment.classmanager.teacher.SJUpFragment
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow
import java.util.ArrayList


class TeacherFragment : BaseFragment() {
    companion object {
        fun newInstance(title: String): TeacherFragment {
            val fragment = TeacherFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }
    private val mFragments = ArrayList<Fragment>()

    private var newView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_manager_teacher, container, false)
        }
        return newView
    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()
        initFragment()
        rbOnClick()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toShouquan(rb_1)

    }

    //私教判断有无场馆
    override fun onFragmentVisibleChange(isVisible: Boolean) {
        super.onFragmentVisibleChange(isVisible)
//        toShouquan(rb_1)
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

        iv_teacher_add.clickWithTrigger {
            //如果登记没完成 则不能添加
            if (true){
                toLoading(rb_1)
            }
        }

    }

    private fun initFragment(){
        mFragments.add(SJUpFragment.newInstance("已上架"))
        mFragments.add(SJDownFragment.newInstance("已下架"))

        val myAdapter = TabItemAdapter(childFragmentManager, mFragments)// 初始化adapter
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
        }
    }


    private fun toShouquan(view1: TextView) {
        if (context == null){
            return
        }
        val popupWindow = CommonPopupWindow.Builder(context)
            .setView(R.layout.dialog_to_shouquan)
            .setBackGroundLevel(0.5f)//0.5f
            .setAnimationStyle(R.style.main_menu_animstyle)
            .setWidthAndHeight(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(view1)

        /**设置逻辑 */
        val view = popupWindow.contentView
        val cancel = view.findViewById<LinearLayout>(R.id.cancel_layout)
        val sure = view.findViewById<LinearLayout>(R.id.shenqin_layout)

        cancel.setOnClickListener {
            popupWindow.dismiss()
        }
        sure.setOnClickListener {
            popupWindow.dismiss()
            //去申请
            val toInfo = Intent(activity, ClassShouquanActivity::class.java)
            startActivity(toInfo)

        }
    }

    private fun toLoading(view1: TextView) {
        if (context == null){
            return
        }
        val popupWindow = CommonPopupWindow.Builder(context)
            .setView(R.layout.dialog_to_loading)
            .setBackGroundLevel(0.5f)//0.5f
            .setAnimationStyle(R.style.main_menu_animstyle)
            .setWidthAndHeight(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(view1)

        /**设置逻辑 */
        val view = popupWindow.contentView
        val sure = view.findViewById<LinearLayout>(R.id.sure_layout)

        sure.setOnClickListener {
            popupWindow.dismiss()
        }
    }

}