package com.noplugins.keepfit.coachplatform.fragment.classmanager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.ManagerTeacherAdapter
import com.noplugins.keepfit.coachplatform.adapter.ManagerTeamClassAdapter
import com.noplugins.keepfit.coachplatform.adapter.TabItemAdapter
import com.noplugins.keepfit.coachplatform.base.BaseFragment
import com.noplugins.keepfit.coachplatform.fragment.classmanager.team.HistoryFragment
import com.noplugins.keepfit.coachplatform.fragment.classmanager.team.ShangjiaFragment
import com.noplugins.keepfit.coachplatform.fragment.classmanager.team.YaoqinFragment
import kotlinx.android.synthetic.main.fragment_manager_team.*
import java.util.ArrayList

class TeamClassFragment:BaseFragment() {
    companion object {
        fun newInstance(title: String): TeamClassFragment {
            val fragment = TeamClassFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    private val mFragments = ArrayList<Fragment>()

    var newView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_manager_team, container, false)
        }
        return newView
    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()
        initFragment()
        rbOnClick()
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
        mFragments.add(ShangjiaFragment.newInstance("已上架"))
        mFragments.add(YaoqinFragment.newInstance("邀请中"))
        mFragments.add(HistoryFragment.newInstance("历史"))

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
            2 ->rb_3.isChecked = true
        }
    }


}