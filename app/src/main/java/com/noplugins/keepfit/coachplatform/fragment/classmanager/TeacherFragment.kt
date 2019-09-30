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
import com.noplugins.keepfit.coachplatform.activity.manager.TeacherAddOrEditActivity
import com.noplugins.keepfit.coachplatform.adapter.TabItemAdapter
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerBean
import com.noplugins.keepfit.coachplatform.fragment.classmanager.teacher.SJDownFragment
import com.noplugins.keepfit.coachplatform.fragment.classmanager.teacher.SJUpFragment
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow
import java.util.ArrayList
import java.util.HashMap


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

    private var code = 0


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
        code = 1
        requestData()

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
            code = 10
            requestData()
        }

    }

    private fun initFragment(){
        mFragments.add(SJUpFragment.newInstance("已上架"))
        mFragments.add(SJDownFragment.newInstance("已下架"))

        val myAdapter = TabItemAdapter(childFragmentManager, mFragments)// 初始化adapter
        view_pager.adapter = myAdapter // 设置adapter
        view_pager.currentItem = 0
        view_pager.offscreenPageLimit = 2
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



    private fun requestData(){
        val params = HashMap<String, Any>()
//        params["teacherNum"] = SpUtils.getString(activity, AppConstants.USER_NAME)
        params["teacherNum"] = SpUtils.getString(activity, AppConstants.USER_NAME)
        params["courseType"] = 2
        params["type"] = 1
        val subscription = Network.getInstance("课程管理", activity)
            .courseManager(params,
                ProgressSubscriber("课程管理", object : SubscriberOnNextListener<Bean<ManagerBean>> {
                    override fun onNext(result: Bean<ManagerBean>) {
                        if (code == 10){
                            val intent = Intent(activity,TeacherAddOrEditActivity::class.java)
                            startActivity(intent)
                        } else{
                            initFragment()
                        }
                    }

                    override fun onError(error: String) {
                        if (error == "-2"){
                            toShouquan(iv_teacher_add)
                        } else if(error == "-3"){
                            toLoading(iv_teacher_add)
                        }

                    }
                }, activity, false)
            )
    }
    private fun toShouquan(view1: View) {
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

    private fun toLoading(view1: View) {
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