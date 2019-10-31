package com.noplugins.keepfit.coachplatform.activity.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.ms.banner.BannerConfig
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.ChangguanDetailFeaturesAdapter
import com.noplugins.keepfit.coachplatform.adapter.WeijieshuTypeAdapter.*
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.bean.manager.CgDetailBean
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.holder.CustomViewHolder
import com.noplugins.keepfit.coachplatform.util.BaseUtils
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.TypeUtil
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow
import com.noplugins.keepfit.coachplatform.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_chaungguan_detail.*
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.HashMap

class ChaungguanDetailActivity : BaseActivity(), EasyPermissions.PermissionCallbacks {

    private val latitude = ""
    private val longitude = ""

    private var listItem = -1
    private var cgNum = ""
    private var type = -1
    override fun initBundle(parms: Bundle?) {
        if (parms != null) {
            listItem = parms.getInt("listItem",-1)

            if (listItem == -1){
                btn_submit.visibility = View.GONE
            }
            cgNum = parms.getString("cgNum").toString()
            type = parms.getInt("type", -1)
            if (type == 1) {
                btn_submit.text = "解 绑"
                btn_submit.visibility = View.VISIBLE
            }
            agreeCourse()
        }
    }

    override fun initView() {
        val decor = window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        setContentView(R.layout.activity_chaungguan_detail)
        initAdapter()
    }

    override fun doBusiness(mContext: Context?) {
        tv_location.clickWithTrigger {
            toMap(tv_location)
        }
        tv_toPhone.clickWithTrigger {
            toPhone(tv_toPhone)
        }
        btn_submit.clickWithTrigger {
            if (type == -1) {
                val mIntent = Intent()//没有任何参数（意图），只是用来传递数据
                mIntent.putExtra("item", listItem)
                setResult(RESULT_OK, mIntent)
                finish()
                return@clickWithTrigger
            }

            //解绑操作
            toUnBinding(btn_submit)


        }

        back_btn.clickWithTrigger {
            finish()
        }


    }

    var data: ArrayList<String> = ArrayList()
    var adapter: ChangguanDetailFeaturesAdapter? = null
    private fun initAdapter() {
        rv_features.layoutManager = GridLayoutManager(this, 4)
        adapter = ChangguanDetailFeaturesAdapter(data)
        rv_features.adapter = adapter
    }

    private fun setting(code: CgDetailBean) {
        //简单使用
        banner
            .setBannerStyle(BannerConfig.NUM_INDICATOR)
            .setPages(code.picUrl, CustomViewHolder())
            .start()

        tv_chuangguan_name.text = code.areaDetail.areaName
        tv_type_size.text = "${TypeUtil.cgTypeToStr(code.areaDetail.type)} | ${code.areaDetail.area}平方米"
        tv_work_time.text = "营业时间 ${code.areaDetail.businessStart}-${code.areaDetail.businessEnd}"
        tv_fenshu.text = "${code.areaDetail.finalGradle}分"
        tv_location.text = "${code.areaDetail.address}"

        val arrList = code.areaDetail.facility.split(",").toList()
        data.addAll(arrList)
        adapter!!.notifyDataSetChanged()

        tv_teacher_tip.text = "暂无"
    }

    private fun agreeCourse() {
        val params = HashMap<String, Any>()
//        params["teacherNum"] = SpUtils.getString(activity, AppConstants.USER_NAME)
        params["areaNum"] = cgNum
        val subscription = Network.getInstance("场馆详情", this)
            .bindingAreaListDetail(
                params,
                ProgressSubscriber("场馆详情", object : SubscriberOnNextListener<Bean<CgDetailBean>> {
                    override fun onNext(result: Bean<CgDetailBean>) {
                        setting(result.data)
                    }

                    override fun onError(error: String) {
                        SuperCustomToast.getInstance(applicationContext)
                            .show(error)
                    }
                }, this, false)
            )
    }

    private fun toMap(view1: TextView) {
        val popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.dialog_to_map)
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
        val cancel = view.findViewById<TextView>(R.id.cancel_layout)

        cancel.setOnClickListener {
            popupWindow.dismiss()
        }

        val ivBaidu = view.findViewById<LinearLayout>(R.id.iv_baidu)
        val ivGaode = view.findViewById<LinearLayout>(R.id.iv_gaode)
        val ivQq = view.findViewById<LinearLayout>(R.id.iv_qq)

        ivBaidu.clickWithTrigger {
            if (!BaseUtils.isAvilible(this, "com.baidu.BaiduMap")) {
                Toast.makeText(this, "请先安装百度地图客户端", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }

            val intent = Intent()
            intent.data = Uri.parse("baidumap://map/direction?destination=${tv_location.text}&mode=driving")
//            intent.data = Uri.parse("baidumap://map/navi?query=${tv_location.text}&src=$packageName")
            startActivity(intent)
        }

        ivGaode.clickWithTrigger {
            if (!BaseUtils.isAvilible(this, "com.autonavi.minimap")) {
                Toast.makeText(this, "请先安装高德地图客户端", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }

            val intent = Intent()
            val stringBuffer = StringBuffer("androidamap://route?sourceApplication=").append(packageName)

            stringBuffer
                .append("&dlat=").append(latitude)
                .append("&dlon=").append(longitude)
                .append("&dname=").append(tv_location.text)
                .append("&dev=").append(0)
                .append("&t=").append(0)
            intent.data = Uri.parse(stringBuffer.toString())
            startActivity(intent)
        }

        ivQq.clickWithTrigger {
            if (!BaseUtils.isAvilible(this, "com.tencent.map")) {
                Toast.makeText(this, "请先安装腾讯地图客户端", Toast.LENGTH_SHORT).show()
                return@clickWithTrigger
            }
            val stringBuffer = StringBuffer("qqmap://map/routeplan?type=drive")
                .append("&tocoord=$latitude").append(",")
                .append(longitude).append("&to=${tv_location.text}")
            intent.data = Uri.parse(stringBuffer.toString())
            startActivity(intent)
        }
    }

    private fun quxiaoBinding() {
        val params = HashMap<String, Any>()
        params["teacherNum"] = SpUtils.getString(this, AppConstants.USER_NAME)
        params["areaNum"] = cgNum
        val subscription = Network.getInstance("场馆列表", this)
            .deleteMyBindingArea(
                params,
                ProgressSubscriber("场馆列表", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
//                        setting(result.data.areaList)
//                        agreeCourse()
                        EventBus.getDefault().post("接受邀请")
                        finish()
                    }

                    override fun onError(error: String) {
                        SuperCustomToast.getInstance(applicationContext)
                            .show(error)
                    }
                }, this, false)
            )
    }

    private fun toUnBinding(view1: TextView) {
        val popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.dialog_to_unbinding)
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
            quxiaoBinding()
        }
    }

    private fun toPhone(view1: TextView) {
        val popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.dialog_to_phone)
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
            initSimple()

        }
    }

    fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        return EasyPermissions.hasPermissions(context!!, *permissions)
    }

    @AfterPermissionGranted(PERMISSION_STORAGE_CODE)
    fun initSimple() {
        if (hasStoragePermission(this)) {
            //有权限
            callPhone("110")
        } else {
            //申请权限
            EasyPermissions.requestPermissions(
                this,
                PERMISSION_STORAGE_MSG,
                PERMISSION_STORAGE_CODE,
                *PERMISSION_STORAGE
            )
        }
    }

    /**
     * 是否有电话权限
     *
     * @param context
     * @return
     */
    fun hasStoragePermission(context: Context?): Boolean {
        return hasPermissions(context, *PERMISSION_STORAGE)
    }


    @SuppressLint("MissingPermission")
    fun callPhone(phoneNum: String) {
        val _intent = Intent(Intent.ACTION_CALL)
        val data = Uri.parse("tel:$phoneNum")
        _intent.data = data
        startActivity(_intent)
    }

    /**
     * 权限设置页面回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {}

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 拒绝权限
     *
     * @param requestCode
     * @param perms
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this)
                .setTitle("提醒")
                .setRationale("需要电话权限才能联系客服哦")
                .build()
                .show()
        }
    }
}
