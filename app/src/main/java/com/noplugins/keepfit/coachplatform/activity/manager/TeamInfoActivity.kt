package com.noplugins.keepfit.coachplatform.activity.manager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.TeamDetail9ImgAdapter
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.activity_team_info.*

class TeamInfoActivity : BaseActivity() {

    override fun initBundle(parms: Bundle?) {
        if (parms != null) {
            when (parms.getInt("type")) {
                1 -> {
                    tv_class_type.text = "已上架"
                }
                2 -> {
                    tv_class_type.text = "邀请中"
                    ll_caozuo.visibility = View.VISIBLE
                }
                3 -> {
                    tv_class_type.text = "邀请失败/已下架"
                    fl_chongxin.visibility = View.VISIBLE
                    tv_jujue.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun initView() {
        setContentView(R.layout.activity_team_info)
        setting()
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger {
            finish()
        }
        jujue.clickWithTrigger {
            toJujue(title_tv)
        }

        jieshou.clickWithTrigger {

        }

    }

    private fun setting() {
        val list = resources.getStringArray(R.array.team_img_url).toMutableList()
        initAdapter(list)
        title_tv.text = "标题"
        tv_cg_name.text = "场馆名称"
        edit_class_room.text = "房间名"
        edit_class_name.text = "名称"
    }

    private fun initAdapter(data: List<String>) {
        //,GridLayoutManager.HORIZONTAL,false
        val layoutManager = GridLayoutManager(this, 3)
        //调整RecyclerView的排列方向
        rv_class_img.layoutManager = layoutManager
        val adapter = TeamDetail9ImgAdapter(data)
        rv_class_img.adapter = adapter
    }


    private fun toJujue(view1: TextView) {
        val popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.dialog_to_jujue)
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
        val sure = view.findViewById<LinearLayout>(R.id.sure_layout)

        cancel.setOnClickListener {
            popupWindow.dismiss()
        }
        sure.setOnClickListener {
            popupWindow.dismiss()
            //去申请

        }
    }
}
