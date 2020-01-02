package com.noplugins.keepfit.coachplatform.activity.manager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ms.banner.BannerConfig
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerTeamBean
import com.noplugins.keepfit.coachplatform.holder.CustomViewHolder
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_team_detail.*
import java.util.HashMap

class TeamDetailActivity : BaseActivity() {
    var courseNum = ""
    override fun initBundle(parms: Bundle?) { if (parms != null) {

        courseNum = parms.getString("courseNum").toString()
        requestData()
    }
    }

    override fun initView() {
        setContentView(R.layout.activity_team_detail)

    }

    override fun doBusiness(mContext: Context?) {
//        back_btn.setOnClickListener {
//            finish()
//        }
    }

    private fun setting(code: ManagerTeamBean) {
//        if (code.courseList.courseName.length > 10) {
//            title_tv.text = code.courseList.courseName.substring(0, 10) + "..."
//        } else {
//            title_tv.text = code.courseList.courseName
//        }
        val urlStr = ArrayList<String>()
        urlStr.add(code.courseList.imgUrl)
        //简单使用
        banner
                .setBannerStyle(BannerConfig.NUM_INDICATOR)
                .setPages(urlStr, CustomViewHolder())
                .start()

        tv_class_duction.text = code.courseList.courseDes
        tv_class_mans.text = code.courseList.suitPerson
        tv_class_tips.text = code.courseList.tips


        Glide.with(this).load(code.courseList.imgUrl)
                .transform( RoundedCorners(20))
                .placeholder(R.drawable.logo_gray)
                .into(logo_image)



    }

    private fun requestData(){
        val params = HashMap<String, Any>()
        params["courseNum"] = courseNum
        subscription = Network.getInstance("课程管理详细", this)
            .courseDetail(params,
                        ProgressSubscriber("课程详情", object : SubscriberOnNextListener<Bean<ManagerTeamBean>> {
                            override fun onNext(result: Bean<ManagerTeamBean>) {
                                setting(result.data)
                            }
                            override fun onError(error: String) {
                                SuperCustomToast.getInstance(applicationContext)
                                        .show(error)
                            }
                        }, this, false)
                )
    }
}
