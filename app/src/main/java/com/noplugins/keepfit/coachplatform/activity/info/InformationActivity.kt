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
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.huantansheng.easyphotos.EasyPhotos
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.base.MyApplication
import com.noplugins.keepfit.coachplatform.bean.InformationBean
import com.noplugins.keepfit.coachplatform.bean.QiNiuToken
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
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow
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
    private var progress_upload: ProgressUtil? = null
    private var icon_image_path =""
    private var icon_net_path =""
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
        getToken()//获取七牛云token
        requestData()
    }

    private fun getToken() {
        val params = HashMap<String, Any>()
        val subscription = Network.getInstance("获取七牛token", this)
            .get_qiniu_token(
                params,
                ProgressSubscriber("获取七牛token", object : SubscriberOnNextListener<Bean<QiNiuToken>> {
                    override fun onNext(result: Bean<QiNiuToken>) {
                        uptoken = result.data.token
                    }

                    override fun onError(error: String) {

                    }
                }, this, false)
            )

    }

    override fun doBusiness(mContext: Context?) {

        back_btn.clickWithTrigger {
            finish()
        }

        iv_logo.clickWithTrigger {
            logoDialog(iv_logo)
        }

        tv_save.clickWithTrigger {
            updateData()
        }
    }

    private fun setting(info: InformationBean) {
        Glide.with(this)
            .load(info.logoUrl)
            .into(iv_logo)
        tv_user_name.text = info.name
        tv_sex.text = if (info.sex == 1) {
            "男"
        } else {
            "女"
        }
        tv_city.text = info.city
        edit_shihe.setText(info.tips)
        tv_birthday.text = "${info.age}"
    }

    private fun requestData() {
        val params = HashMap<String, Any>()
        params["userNum"] = SpUtils.getString(this, AppConstants.USER_NAME)

        subscription = Network.getInstance("个人资料", this)
            .personalData(
                params,
                ProgressSubscriber("个人资料", object : SubscriberOnNextListener<Bean<InformationBean>> {
                    override fun onNext(result: Bean<InformationBean>) {
                        setting(result.data)
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }

    private fun logoDialog(view1: View) {
        val popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.dialog_to_submit)
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
        val pazhao = view.findViewById<TextView>(R.id.tv_paizhao)
        val xiangce = view.findViewById<TextView>(R.id.tv_xiangce)
        pazhao.clickWithTrigger {
            EasyPhotos.createCamera(this)
                .setFileProviderAuthority("com.noplugins.keepfit.userplatform.fileprovider")
                .setPuzzleMenu(false)
                .setOriginalMenu(false, true, null)
                .start(102)
            popupWindow!!.dismiss()
        }
        xiangce.clickWithTrigger {
            EasyPhotos.createAlbum(
                this,
                true, GlideEngine.getInstance()
            )
                .setFileProviderAuthority("com.noplugins.keepfit.userplatform.fileprovider")
                .setPuzzleMenu(false)
                .setCount(1)
                .setOriginalMenu(false, true, null)
                .start(102)
            popupWindow!!.dismiss()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {

        } else {
            //添加icon,上传icon
            if (data == null) return
            val resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS)
            if (resultPaths!!.size > 0) {
//                icon_image_path = resultPaths[0]
                val icon_iamge_file = File(resultPaths[0])
                withLs(icon_iamge_file)
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
    }

    private fun withLs(photos: File) {
        Luban.with(this)
            .load(photos)
            .ignoreBy(100)
            .setTargetDir(getPath())
            .setFocusAlpha(false)
            .setCompressListener(object : OnCompressListener {
                override fun onSuccess(file: File?) {
                    icon_image_path = file!!.path
                    uploadQiniuColud()
                    Log.d("Luban", "luban压缩 成功！原图:${photos.path}")
                    Log.d("Luban", "luban压缩 成功！imgurl:$icon_image_path")
                    Glide.with(this@InformationActivity).load(file).into(iv_logo)
                    uploadQiniuColud()

                }

                override fun onError(e: Throwable?) {
                    icon_image_path = photos.path
                    uploadQiniuColud()
                    Log.d("Luban", "luban压缩 失败！原图:${photos.path}")
                    Log.d("Luban", "luban压缩 失败！imgurl:$icon_image_path")
                    Glide.with(this@InformationActivity).load(photos).into(iv_logo)
                    uploadQiniuColud()
                }

                override fun onStart() {
                }

            })
            .launch()
    }

    private fun getPath(): String {
        val path = "${Environment.getExternalStorageDirectory()}/Luban/image/"
        val file = File(path)
        if (file.mkdirs()) {
            return path
        }
        return path
    }

    private fun uploadQiniuColud() {
        /**七牛云**/
        progress_upload = ProgressUtil()
        uploadManager!!.put(
            icon_image_path, qiniu_key, uptoken,
            { key, info, response ->
                //res包含hash、key等信息，具体字段取决于上传策略的设置
                if (info.isOK) {
                    Log.e("qiniu", "Upload Success")
                    icon_net_path = key
                } else {
                    Log.e("qiniu", "Upload Fail")
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            },
            UploadOptions(null, "test-type", true, null, null)
        )
    }


    private fun updateData() {
        val params = HashMap<String, Any>()
        params["userNum"] = SpUtils.getString(this, AppConstants.USER_NAME)
        params["logoKey"] = icon_net_path
        params["tips"] = edit_shihe.text.toString()

        subscription = Network.getInstance("个人资料", this)
            .updateCoachHome(
                params,
                ProgressSubscriber("个人资料", object : SubscriberOnNextListener<Bean<String>> {
                    override fun onNext(result: Bean<String>) {
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }
}

