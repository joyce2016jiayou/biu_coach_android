package com.noplugins.keepfit.coachplatform.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.noplugins.keepfit.coachplatform.MainActivity
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.bean.TeacherStatusBean
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import java.util.HashMap

class SplashActivity : BaseActivity() {
    override fun initView() {
        if (!isTaskRoot) {
            val intent = intent
            val intentAction = intent.action
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction == Intent
                    .ACTION_MAIN
            ) {
                finish()
                return
            }
        }
        setContentLayout(R.layout.activity_splash)
        ButterKnife.bind(this)
        isShowTitle(false)
    }

    override fun doBusiness(mContext: Context?) {
        Handler().postDelayed(Runnable { enterHomeActivity() }, 2000)

    }

    override fun initBundle(parms: Bundle?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isTaskRoot) {
            val intent = intent
            val intentAction = intent.action
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction == Intent
                    .ACTION_MAIN
            ) {
                finish()
                return
            }
        }
        // 判断是否是第一次开启应用
//        val isFirstOpen = SpUtils.getBoolean(this, AppConstants.FIRST_OPEN)
//        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_splash)
//        Handler().postDelayed(Runnable {  }, 2000)
//        // 如果是第一次启动，则先进入功能引导页
//        if (!isFirstOpen) {
//            val intent = Intent(this, WelcomeGuideActivity::class.java)
//            startActivity(intent)
//            finish()
//            return
//        }


        Handler().postDelayed(Runnable { enterHomeActivity() }, 2000)
    }

    private fun enterHomeActivity() {

        Log.d("AAAAAA", "" + SpUtils.getString(applicationContext, AppConstants.TOKEN))

        if (SpUtils.getString(applicationContext, AppConstants.TOKEN) == "") {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            //获取教练状态
            get_teacher_status()
        }


    }

    private fun get_teacher_status() {
        val params = HashMap<String, Any>()
        params["userNum"] = SpUtils.getString(applicationContext, AppConstants.SELECT_TEACHER_NUMBER)
        val subscription = Network.getInstance("获取教练状态", this)
            .get_teacher_status(
                params,
                ProgressSubscriber("获取教练状态", object : SubscriberOnNextListener<Bean<TeacherStatusBean>> {
                    override fun onNext(result: Bean<TeacherStatusBean>) {
                        set_status(result)

                    }

                    override fun onError(error: String) {
                        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }, this, false)
            )
    }

    private fun set_status(result: Bean<TeacherStatusBean>) {
        //teacherType 1团课 2私教 3都有
        //pType 私教 1 通过2拒绝3审核中
        //lType 团课 1 通过2拒绝3审核中
        // sign 是否签约上架 1 是 0 否
        if (result.data.teacherType == -1) {//目前没有身份
            val intent = Intent(this@SplashActivity, SelectRoleActivity::class.java)
            startActivity(intent)
            finish()
        } else if (result.data.teacherType == 1) {//团课
            if (result.data.lType == 1) {
                if (result.data.sign == 1) {//已签约
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {//未签约
                    if (result.data.lType == 1) {//通过的话就直接签约
                        if (result.data.sign == 1) {//已签约
                            val intent = Intent(this@SplashActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val intent = Intent(this@SplashActivity, CheckStatusActivity::class.java)
                            val bundle = Bundle()
                            bundle.putInt("into_index", 3)
                            intent.putExtras(bundle)
                            startActivity(intent)
                            finish()
                        }

                    } else if (result.data.lType == 3) {//审核中
                        val intent = Intent(this@SplashActivity, CheckStatusActivity::class.java)
                        val bundle = Bundle()
                        bundle.putInt("into_index", 2)
                        bundle.putInt("status", 1)//审核中
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    } else if (result.data.lType == 2) {//拒绝
                        val intent = Intent(this@SplashActivity, CheckStatusActivity::class.java)
                        val bundle = Bundle()
                        bundle.putInt("into_index", 2)
                        bundle.putInt("status", -1)//拒绝
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this@SplashActivity, CheckStatusActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

        } else if (result.data.teacherType == 2) {//私教
            if (result.data.pType == 1) {
                if (result.data.sign == 1) {//已签约
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {//未签约
                    if (result.data.pType == 1) {//通过的话
                        //再次判断是否签约
                        if (result.data.sign == 1) {//已签约
                            val intent = Intent(this@SplashActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {//未签约
                            val intent = Intent(this@SplashActivity, CheckStatusActivity::class.java)
                            val bundle = Bundle()
                            bundle.putInt("into_index", 3)
                            intent.putExtras(bundle)
                            startActivity(intent)
                            finish()
                        }

                    } else if (result.data.pType == 3) {//审核中
                        val intent = Intent(this@SplashActivity, CheckStatusActivity::class.java)
                        val bundle = Bundle()
                        bundle.putInt("into_index", 2)
                        bundle.putInt("status", 1)//审核中
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    } else if (result.data.pType == 2) {//拒绝
                        val intent = Intent(this@SplashActivity, CheckStatusActivity::class.java)
                        val bundle = Bundle()
                        bundle.putInt("into_index", 2)
                        bundle.putInt("status", -1)//拒绝
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    } else {//没有私教身份
                        val intent = Intent(this@SplashActivity, CheckStatusActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        } else {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}
