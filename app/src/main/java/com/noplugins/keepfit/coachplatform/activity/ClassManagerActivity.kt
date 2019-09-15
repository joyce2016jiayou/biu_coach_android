package com.noplugins.keepfit.coachplatform.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.fragment.classmanager.TeacherFragment
import com.noplugins.keepfit.coachplatform.fragment.classmanager.TeamClassFragment
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import kotlinx.android.synthetic.main.activity_class_manager.*

class ClassManagerActivity : BaseActivity() {

    private lateinit var fragments: MutableList<Fragment>
    private var fragmentManager: FragmentManager? = null
    private var transition: FragmentTransaction? = null

    private var teacher: TeacherFragment? = null
    private var teamClass: TeamClassFragment? = null

    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_class_manager)

        fragmentManager = supportFragmentManager
        fragments = ArrayList()

        teamClass = TeamClassFragment.newInstance("团课")
        fragments.add(teamClass!!)
        hideOthersFragment(teamClass!!, true)

    }

    override fun doBusiness(mContext: Context?) {
        rl_team.clickWithTrigger {
            changeBtn(1)
            if (teamClass == null) {
                teamClass = TeamClassFragment.newInstance("团课")
                fragments.add(teamClass!!)
                hideOthersFragment(teamClass!!, true)
            } else {
                hideOthersFragment(teamClass!!, false)
            }
        }
        rl_private.clickWithTrigger {
            changeBtn(2)
            if (teacher == null) {
                teacher = TeacherFragment.newInstance("私教")
                fragments.add(teacher!!)
                hideOthersFragment(teacher!!, true)
            } else {
                hideOthersFragment(teacher!!, false)
            }
        }
    }

    private fun changeBtn(select:Int){
        when(select){
            1 -> {
                tv1.setTextColor(Color.parseColor("#00BABB"))
                lin1.visibility = View.VISIBLE
                tv2.setTextColor(Color.parseColor("#181818"))
                lin2.visibility = View.INVISIBLE
            }
            2 -> {
                tv2.setTextColor(Color.parseColor("#00BABB"))
                lin2.visibility = View.VISIBLE
                tv1.setTextColor(Color.parseColor("#181818"))
                lin1.visibility = View.INVISIBLE
            }
        }
    }

    /**
     * 动态显示Fragment
     *
     * @param showFragment 要增加的fragment
     * @param add          true：增加fragment；false：切换fragment
     */
    private fun hideOthersFragment(showFragment: Fragment, add: Boolean) {
        transition = fragmentManager!!.beginTransaction()
        if (add)
            transition!!.add(R.id.main_container_content, showFragment)
        fragments!!.forEach { fragment ->
            if (showFragment == fragment) {
                transition!!.show(fragment)
            } else {
                transition!!.hide(fragment)
            }
        }
        transition!!.commit()
    }

}
