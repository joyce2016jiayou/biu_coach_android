package com.noplugins.keepfit.coachplatform

import android.content.Context
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.noplugins.keepfit.coachplatform.adapter.ContentPagerAdapterMy
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.base.MyApplication
import com.noplugins.keepfit.coachplatform.fragment.ScheduleFragment
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.jpush.TagAliasOperatorHelper
import com.noplugins.keepfit.coachplatform.util.MessageEvent
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.data.SharedPreferencesHelper
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.GsonSubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriberNew
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class MainActivity : BaseActivity(), View.OnClickListener {
    internal var bottom_iamge_views: MutableList<ImageView> = ArrayList()
    private var sp: SoundPool? = null//声明一个SoundPool
    private var music: Int = 0//定义一个整型用load（）；来设置suondID
    private val tabFragments = ArrayList<Fragment>()

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
        tabFragments.add(ScheduleFragment.getInstance("第二页"))
        tabFragments.add(ScheduleFragment.getInstance("第三页"))
        //初始化viewpager
        val contentAdapter = ContentPagerAdapterMy(supportFragmentManager, tabFragments)
        viewpager_content.setAdapter(contentAdapter)
        viewpager_content.setCurrentItem(0)

        MyApplication.addDestoryActivity(this, "MainActivity")

        //获取消息总数，设置消息总数
        //get_message_all()

        loginSuccess()
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
                home_name.setTextColor(resources.getColor(R.color.btn_text_color))
                movie_name.setTextColor(resources.getColor(R.color.contents_text))
                mine_name.setTextColor(resources.getColor(R.color.contents_text))
            }

            R.id.btn_movie -> {
                viewpager_content.currentItem = 2
                xianshi_three()
                home_name.setTextColor(resources.getColor(R.color.contents_text))
                movie_name.setTextColor(resources.getColor(R.color.btn_text_color))
                mine_name.setTextColor(resources.getColor(R.color.contents_text))
            }
            R.id.btn_mine -> {
                viewpager_content.currentItem = 3
                xianshi_four()
                home_name.setTextColor(resources.getColor(R.color.contents_text))
                movie_name.setTextColor(resources.getColor(R.color.contents_text))
                mine_name.setTextColor(resources.getColor(R.color.btn_text_color))
            }
        }
    }

    private fun xianshi_one() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_on)
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_off)
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_off)
        bottom_iamge_views.get(3).setImageResource(R.drawable.icon_user_off)


    }

    private fun xianshi_two() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_off)
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_on)
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_off)
        bottom_iamge_views.get(3).setImageResource(R.drawable.icon_user_off)
    }

    private fun xianshi_three() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_off)
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_off)
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_on)
        bottom_iamge_views.get(3).setImageResource(R.drawable.icon_user_off)
    }

    private fun xianshi_four() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_off)
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_off)
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_off)
        bottom_iamge_views.get(3).setImageResource(R.drawable.icon_user_on)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun upadate(messageEvent: MessageEvent) {
        if (messageEvent.message == "update_message_num") {//获取消息总数，设置消息总数
            //get_message_all()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(messageEvent: com.noplugins.keepfit.coachplatform.callback.MessageEvent) {

    }


//    private fun get_message_all() {
//        val params = HashMap<String, String>()
//        if ("" == SpUtils.getString(this!!, AppConstants.USER_NAME)) {
//
//        } else {
//            val user_id = SpUtils.getString(this!!, AppConstants.USER_NAME)
//            params["userNum"] = user_id//用户编号
//        }
//        val gson = Gson()
//        val json_params = gson.toJson(params)
//        val json = Gson().toJson(params)//要传递的json
//        val requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)
//        Log.e(TAG, "获取消息总数参数：$json_params")
//        subscription = Network.getInstance("获取消息总数", applicationContext)
//            .get_message_all(
//                requestBody,
//                ProgressSubscriberNew(
//                    MaxMessageEntity::class.java,
//                    object : GsonSubscriberOnNextListener<MaxMessageEntity> {
//                        override fun on_post_entity(maxMessageEntity: MaxMessageEntity, get_message_id: String) {
//                            Log.e(TAG, "获取消息总数成功：" + maxMessageEntity.getNoRead())
//                            //设置消息总数
//                            if (maxMessageEntity.getNoRead() > 0) {
//                                message_view.visibility = View.VISIBLE
//                                if (maxMessageEntity.getNoRead() > 99) {
//                                    message_num_tv.text = "99+"
//                                } else {
//                                    message_num_tv.text = maxMessageEntity.getNoRead().toString()
//                                }
//                            } else {
//                                message_view.visibility = View.GONE
//                            }
//
//                        }
//                    },
//                    object : SubscriberOnNextListener<Bean<Any>> {
//                        override fun onNext(result: Bean<Any>) {
//
//                        }
//
//                        override fun onError(error: String) {
//                            Logger.e(TAG, "获取获取场馆消息总数报错：$error")
//                            //Toast.makeText(getApplicationContext(), "获取审核状态失败！", Toast.LENGTH_SHORT).show();
//                        }
//                    },
//                    this,
//                    true
//                )
//            )
//    }


}
