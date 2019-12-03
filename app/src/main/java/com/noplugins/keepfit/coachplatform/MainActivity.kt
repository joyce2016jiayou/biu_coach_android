package com.noplugins.keepfit.coachplatform

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.SoundPool
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.allenliu.versionchecklib.callback.OnCancelListener
import com.allenliu.versionchecklib.v2.AllenVersionChecker
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder
import com.allenliu.versionchecklib.v2.builder.UIData
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener
import com.google.gson.Gson
import com.noplugins.keepfit.coachplatform.adapter.ContentPagerAdapterMy
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.base.MyApplication
import com.noplugins.keepfit.coachplatform.bean.MaxMessageEntity
import com.noplugins.keepfit.coachplatform.bean.VersionEntity
import com.noplugins.keepfit.coachplatform.fragment.MessageFragment
import com.noplugins.keepfit.coachplatform.fragment.MineFragment
import com.noplugins.keepfit.coachplatform.fragment.ScheduleFragment
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.jpush.TagAliasOperatorHelper
import com.noplugins.keepfit.coachplatform.util.MessageEvent
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.VersionUtils
import com.noplugins.keepfit.coachplatform.util.data.SharedPreferencesHelper
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.GsonSubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriberNew
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.BaseDialog
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.system.exitProcess

class MainActivity : BaseActivity(), View.OnClickListener {
    internal var bottom_iamge_views: MutableList<ImageView> = ArrayList()
    private var sp: SoundPool? = null//声明一个SoundPool
    private var music: Int = 0//定义一个整型用load（）；来设置suondID
    private val tabFragments = ArrayList<Fragment>()
    private lateinit var builder: DownloadBuilder
    private var is_qiangzhi_update = false
    private var update_url = ""
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_main)
        SpUtils.putString(applicationContext, AppConstants.LAT, "" + 31.230416)
        SpUtils.putString(applicationContext, AppConstants.LON, "" + 121.473701)

        bottom_iamge_views.add(home_img)
        bottom_iamge_views.add(movie_img)
        bottom_iamge_views.add(mine_img)

        btn_home.setOnClickListener(this)
        btn_movie.setOnClickListener(this)
        btn_mine.setOnClickListener(this)


    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)

    }


    override fun doBusiness(mContext: Context?) {
        //初始化页面
        tabFragments.add(ScheduleFragment.getInstance("第一页"))
        tabFragments.add(MessageFragment.newInstance("第二页"))
        tabFragments.add(MineFragment.Companion.newInstance("第三页"))
        //初始化viewpager
        val contentAdapter = ContentPagerAdapterMy(supportFragmentManager, tabFragments)
        viewpager_content.setAdapter(contentAdapter)
        viewpager_content.setCurrentItem(0)

        MyApplication.addDestoryActivity(this, "MainActivity")

        //获取消息总数，设置消息总数
        get_message_all()

        loginSuccess()


    }

    private fun update_app() {
        val params: MutableMap<String, Any> =
            HashMap()
        params["type"] = "coach"
        params["code"] = VersionUtils.getAppVersionCode(applicationContext)
        params["phoneType"] = "2"
        val subscription = Network.getInstance("升级版本", this)
            .update_version(
                params,
                ProgressSubscriber("升级版本", object : SubscriberOnNextListener<Bean<VersionEntity>> {
                    override fun onNext(result: Bean<VersionEntity>) {
                        update_url = result.data.url
                        //是否需要强制升级1强制升级 2不升级 3可升级可不升级
                        if (result.data.up === 1) {
                            is_qiangzhi_update = true
                            update_app_pop()
                        }else if(result.data.up === 3){
                            update_app_pop()
                            is_qiangzhi_update = false
                        }
                    }

                    override fun onError(error: String?) {}

                }, this, false)
            )
    }

    private fun update_app_pop() {
        builder = AllenVersionChecker
            .getInstance()
            .downloadOnly(crateUIData())
        builder.setCustomVersionDialogListener(createCustomDialogTwo()) //设置更新弹窗样式
        builder.setCustomDownloadingDialogListener(createCustomDownloadingDialog()) //设置下载样式
        builder.setForceRedownload(true) //强制重新下载apk（无论本地是否缓存）
        builder.setShowNotification(true) //显示下载通知栏
        builder.setShowDownloadingDialog(true) //显示下载中对话框
        builder.setShowDownloadFailDialog(true) //显示下载失败对话框
        builder.setDownloadAPKPath(Environment.getExternalStorageDirectory().toString() + "/noplugins/apkpath/") //自定义下载路径
        builder.setOnCancelListener(OnCancelListener {
            if (is_qiangzhi_update) {
                val intent = Intent()
                intent.action = "android.intent.action.MAIN"
                intent.addCategory("android.intent.category.HOME")
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@MainActivity, "已关闭更新", Toast.LENGTH_SHORT).show()
            }
        })
        builder.executeMission(this)
    }

    /**
     * 自定义下载中对话框，下载中会连续回调此方法 updateUI
     * 务必用库传回来的context 实例化你的dialog
     *
     * @return
     */
    private fun createCustomDownloadingDialog(): CustomDownloadingDialogListener? {
        return object : CustomDownloadingDialogListener {
            override fun getCustomDownloadingDialog(
                context: Context,
                progress: Int,
                versionBundle: UIData
            ): Dialog {
                return BaseDialog(context, R.style.BaseDialog, R.layout.custom_download_layout)
            }

            override fun updateUI(dialog: Dialog, progress: Int, versionBundle: UIData) {
                val tvProgress = dialog.findViewById<TextView>(R.id.tv_progress)
                val progressBar = dialog.findViewById<ProgressBar>(R.id.pb)
                progressBar.progress = progress
                tvProgress.text = getString(R.string.versionchecklib_progress, progress)
            }
        }
    }

    /**
     * 更新弹窗样式
     *
     * @return
     */
    private fun createCustomDialogTwo(): CustomVersionDialogListener? {
        return CustomVersionDialogListener { context: Context?, versionBundle: UIData ->
            val baseDialog = BaseDialog(context, R.style.BaseDialog, R.layout.custom_dialog_two_layout)
            val textView: TextView = baseDialog.findViewById(R.id.tv_msg)
            textView.text = versionBundle.content
            baseDialog.setCanceledOnTouchOutside(true)
            baseDialog
        }
    }

    /**
     * @return
     * @important 使用请求版本功能，可以在这里设置downloadUrl
     * 这里可以构造UI需要显示的数据
     * UIData 内部是一个Bundle
     */
    private fun crateUIData(): UIData? {
        val uiData = UIData.create()
        uiData.title = getString(R.string.update_title)
        uiData.downloadUrl = update_url
        if (is_qiangzhi_update) {
            uiData.content = getString(R.string.updatecontent2)
        } else {
            uiData.content = getString(R.string.updatecontent)
        }
        return uiData
    }

    private fun loginSuccess() {
        //如果没有缓存的别名，重新获取
        if ("" == SharedPreferencesHelper.get(this, AppConstants.IS_SET_ALIAS, "")) {
            //设置别名
            val tagAliasBean = TagAliasOperatorHelper.TagAliasBean()
            TagAliasOperatorHelper.sequence++
            //设置用户编号为别名
            if ("" == SpUtils.getString(applicationContext, AppConstants.USER_NAME)) {
                tagAliasBean.alias = "null_user_id"
            } else {
                val user_id = SpUtils.getString(applicationContext, AppConstants.USER_NAME)
                tagAliasBean.alias = user_id
            }
            tagAliasBean.isAliasAction = true
            tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET
            TagAliasOperatorHelper.getInstance().handleAction(
                applicationContext,
                TagAliasOperatorHelper.sequence, tagAliasBean
            )
            Log.e("设置的alias", "" + SharedPreferencesHelper.get(this, AppConstants.IS_SET_ALIAS, "").toString())

        } else {
            Log.e("已缓存alias", "" + SharedPreferencesHelper.get(this, AppConstants.IS_SET_ALIAS, "").toString())

        }

    }

    override fun onResume() {
        super.onResume()
        //更新app
        update_app()
        if (null != intent.extras) {
            val parms = intent.extras
            if (parms!!.getString("jpush_enter") == "jpush_enter1") {

                viewpager_content.currentItem = 2
                xianshi_three()
                //设置跳转到消息tab1
                val messageEvent = MessageEvent("jpush_main_enter1")
                EventBus.getDefault().postSticky(messageEvent)

            } else if (parms.getString("jpush_enter") == "jpush_enter2") {
                viewpager_content.currentItem = 2

                xianshi_three()
                //设置跳转到消息tab2
                val messageEvent = MessageEvent("jpush_main_enter2")
                EventBus.getDefault().postSticky(messageEvent)

            } else if (parms!!.getString("jpush_enter") == "jpush_enter3") {

                viewpager_content.currentItem = 2
                xianshi_three()

                //设置跳转到消息tab3
                val messageEvent = MessageEvent("jpush_main_enter3")
                EventBus.getDefault().postSticky(messageEvent)

            }
        }
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btn_home -> {
                viewpager_content.currentItem = 0
                xianshi_one()
                home_name.setTextColor(resources.getColor(R.color.color_181818))
                movie_name.setTextColor(resources.getColor(R.color.color_4A4A4A))
                mine_name.setTextColor(resources.getColor(R.color.color_4A4A4A))
            }

            R.id.btn_movie -> {
                viewpager_content.currentItem = 1
                xianshi_two()
                home_name.setTextColor(resources.getColor(R.color.color_4A4A4A))
                movie_name.setTextColor(resources.getColor(R.color.color_181818))
                mine_name.setTextColor(resources.getColor(R.color.color_4A4A4A))
            }
            R.id.btn_mine -> {
                viewpager_content.currentItem = 2
                xianshi_three()
                home_name.setTextColor(resources.getColor(R.color.color_4A4A4A))
                movie_name.setTextColor(resources.getColor(R.color.color_4A4A4A))
                mine_name.setTextColor(resources.getColor(R.color.color_181818))
            }
        }
    }

    private fun xianshi_one() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_on)
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_off)
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_off)


    }

    private fun xianshi_two() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_off)
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_on)
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_off)
    }

    private fun xianshi_three() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_off)
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_off)
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_on)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun upadate(messageEvent: MessageEvent) {
        if (messageEvent.message == "update_message_num") {//获取消息总数，设置消息总数
            get_message_all()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(messageEvent: com.noplugins.keepfit.coachplatform.callback.MessageEvent) {

    }


    private fun get_message_all() {
        val params = HashMap<String, Any>()
        params["userNum"] = SpUtils.getString(applicationContext, AppConstants.USER_NAME)
        subscription = Network.getInstance("获取消息总数", applicationContext)
            .messageTotalCount(
                params,
                ProgressSubscriber("读消息", object : SubscriberOnNextListener<Bean<MaxMessageEntity>> {
                    override fun onNext(result: Bean<MaxMessageEntity>) {
                        Log.e(TAG, "获取消息总数成功：" + result.data.noRead)
                        //设置消息总数
                        if (result.data.noRead > 0) {
                            message_view.visibility = View.VISIBLE
                            if (result.data.noRead > 99) {
                                message_num_tv.text = "99+"
                            } else {
                                message_num_tv.text = result.data.noRead.toString()
                            }
                        } else {
                            message_view.visibility = View.GONE
                        }
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }

    //退出时的时间
    private var mExitTime: Long = 0

    override fun onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(applicationContext, "再按一次退出", Toast.LENGTH_SHORT).show()
            mExitTime = System.currentTimeMillis()
        } else {
            finish()
            exitProcess(0)
        }

    }

}
