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
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.WeijieshuTypeAdapter.*
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.BaseUtils
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.activity_chaungguan_detail.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class ChaungguanDetailActivity : BaseActivity(), EasyPermissions.PermissionCallbacks {

    private val latitude = ""
    private val longitude = ""

    private var listItem = -1
    private var cgNum = ""
    override fun initBundle(parms: Bundle?) {
        if (parms!=null){
            listItem = parms.getInt("listItem")
            cgNum = parms.getString("cgNum").toString()
        }
    }

    override fun initView() {
        val decor = window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        setContentView(R.layout.activity_chaungguan_detail)
    }

    override fun doBusiness(mContext: Context?) {
        tv_location.clickWithTrigger {
            toMap(tv_location)
        }
        tv_toPhone.clickWithTrigger {
            toPhone(tv_toPhone)
        }
        btn_submit.clickWithTrigger {
            val mIntent=Intent()//没有任何参数（意图），只是用来传递数据
            mIntent.putExtra("item",listItem)
            setResult(RESULT_OK,mIntent)
            finish()
        }

        back_btn.clickWithTrigger {
            finish()
        }


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
