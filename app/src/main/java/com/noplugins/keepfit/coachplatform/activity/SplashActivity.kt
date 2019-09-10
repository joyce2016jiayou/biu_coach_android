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
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.util.SpUtils

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

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        if (!isTaskRoot) {
//            val intent = intent
//            val intentAction = intent.action
//            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction == Intent
//                    .ACTION_MAIN
//            ) {
//                finish()
//                return
//            }
//        }
//        // 判断是否是第一次开启应用
////        val isFirstOpen = SpUtils.getBoolean(this, AppConstants.FIRST_OPEN)
////        // 如果不是第一次启动app，则正常显示启动屏
//        setContentView(R.layout.activity_splash)
////        Handler().postDelayed(Runnable {  }, 2000)
////        // 如果是第一次启动，则先进入功能引导页
////        if (!isFirstOpen) {
////            val intent = Intent(this, WelcomeGuideActivity::class.java)
////            startActivity(intent)
////            finish()
////            return
////        }
//
//
//        Handler().postDelayed(Runnable { enterHomeActivity() }, 2000)
//    }

    private fun enterHomeActivity() {

        Log.d("AAAAAA", "" + SpUtils.getString(applicationContext, AppConstants.TOKEN))

        val intent = if (SpUtils.getString(applicationContext, AppConstants.TOKEN) == "") {
            Intent(this@SplashActivity, LoginActivity::class.java)
        } else {
            Intent(
                this@SplashActivity,
                MainActivity::class.java
            )
        }

        startActivity(intent)
        finish()
    }
}
