package com.noplugins.keepfit.coachplatform.activity.info

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.huantansheng.easyphotos.EasyPhotos
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.base.MyApplication
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.GlideEngine
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.TokenNetwork
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.GsonSubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriberNew
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.ProgressUtil
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import com.qiniu.android.storage.UploadOptions
import com.ycuwq.datepicker.date.DatePickerDialogFragment
import kotlinx.android.synthetic.main.activity_information.*
import okhttp3.RequestBody
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class InformationActivity : BaseActivity() {

    /**
     * 七牛云
     */
    //指定upToken, 强烈建议从服务端提供get请求获取
    private var uptoken = "xxxxxxxxx:xxxxxxx:xxxxxxxxxx"
    private var sdf: SimpleDateFormat? = null
    private var qiniu_key: String? = null
    private var uploadManager: UploadManager? = null

    private var icon_image_path: String? = null

    private var progress_upload: ProgressUtil? = null

    private var icon_net_path = ""//需要有一个默认的头像
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_information)
        Glide.with(this).load(SpUtils.getString(applicationContext, AppConstants.LOGO))
            .into(iv_logo)
        /**七牛云**/
        uploadManager = MyApplication.uploadManager
        sdf = SimpleDateFormat("yyyyMMddHHmmss")
        qiniu_key = "icon_" + sdf!!.format(Date())
//        getToken()
//        getInfoData()
    }

    override fun doBusiness(mContext: Context?) {

        back_btn.clickWithTrigger {
            finish()
        }

        iv_logo.clickWithTrigger {
            //            logoDialog()
        }
        rl_user_name.clickWithTrigger {
            //            val intent = Intent(this, InformationNameUpdateActivity::class.java)
//            intent.putExtra("nickname", name)
//            startActivity(intent)
        }
        rl_sex.clickWithTrigger {
            //            sexDialog()
        }

        rl_birthday.clickWithTrigger {
            val _day = DatePickerDialogFragment()
            _day.setOnDateChooseListener { _year, _month, _day ->
                var year = _year
                var month = if (_month > 9) {
                    "$_month"
                } else {
                    "0$_month"
                }

                var day = if (_day > 9) {
                    "$_day"
                } else {
                    "0$_day"
                }
                tv_birthday.setText("$_year-$month-$day")

//                if (tv_birthday.text.toString() != birthday) {
//                    updateInfo()
//                }
            }
            _day.show(supportFragmentManager, "DatePickerDialogFragment")
        }
    }


}

