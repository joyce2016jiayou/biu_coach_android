package com.noplugins.keepfit.coachplatform.activity.manager

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import cn.jpush.android.cache.Sp
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.services.geocoder.GeocodeQuery
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult
import com.google.gson.Gson
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.PopUpAdapter
import com.noplugins.keepfit.coachplatform.adapter.TeacherCgSelectAdapter
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.bean.AddressBean
import com.noplugins.keepfit.coachplatform.bean.BindingCgBean
import com.noplugins.keepfit.coachplatform.bean.BindingListBean
import com.noplugins.keepfit.coachplatform.bean.manager.CgListBean
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.BaseUtils
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.pop.SpinnerPopWindow
import com.noplugins.keepfit.coachplatform.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_class_shouquan.*
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.util.HashMap

class ClassShouquanActivity : BaseActivity(), AMapLocationListener {

    private var province = ""
    private var city = ""
    private var district = ""
    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.locationType//获取当前定位结果来源，如网络定位结果，详见定位类型表
                latitude = amapLocation.latitude//获取纬度
                longitude = amapLocation.longitude//获取经度
                //                        latLonPoint = new LatLonPoint(currentLat, currentLon);  // latlng形式的
                /*currentLatLng = new LatLng(currentLat, currentLon);*/   //latlng形式的
//                amapLocation.accuracy//获取精度信息

                Log.d("LogInfo","getCity():"+amapLocation.city)
                Log.d("LogInfo","district():"+amapLocation.district)
                tv_location.text = amapLocation.district
                val code = amapLocation.adCode.toString().substring(0,4)+"00"
                province = amapLocation.adCode.toString().substring(0,2)+"0000"
                city = code
                district = amapLocation.adCode

                Log.d("LogInfo","cityCode():"+amapLocation.adCode)
                Log.d("LogInfo", "city():$code")
                initAdapter()
                requsetData(code)
                agreeCourse()

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e(
                    "AmapError", "location Error, ErrCode:"
                            + amapLocation.errorCode + ", errInfo:"
                            + amapLocation.errorInfo
                )
                SuperCustomToast.getInstance(this)
                    .show("当前页面功能需要定位权限，请设置")
                initAdapter()
                agreeCourse()

            }
        }
    }

    lateinit var adapter: TeacherCgSelectAdapter
    private var data: MutableList<CgListBean.AreaListBean> = ArrayList()
    private var latitude = 0.0
    private var longitude = 0.0
    private var page = 1
    private var skillSelect = -1
    private var submitList:MutableList<BindingCgBean> = ArrayList()
    val bean = BindingListBean()

    //声明AMapLocationClient类对象
    internal var mLocationClient: AMapLocationClient? = null
    //声明AMapLocationClientOption对象
    var mLocationOption: AMapLocationClientOption? = null

    private val mPerms = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)

    private var selectAddress = ""
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_class_shouquan)
        requestPermission()
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger {
            finish()
        }
        queren_btn.clickWithTrigger {
            if (BaseUtils.isFastClick()){
                val gson = Gson().toJson(submitList)
                Log.d("tag",gson)
                if (submitList.size > 0){
                    submitData()
                } else {
                    SuperCustomToast.getInstance(applicationContext)
                        .show("请选择授课场馆")
                }
            }
        }
        iv_delete_edit.clickWithTrigger {
            edit_search.setText("")
        }

        edit_search.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                //获取焦点
                ll_sousuo.visibility = View.GONE
                iv_delete_edit.visibility = View.VISIBLE
                iv_search.visibility = View.VISIBLE
            } else {//失去焦点
                if (edit_search.text.toString() == "") {
                    ll_sousuo.visibility = View.VISIBLE
                    iv_delete_edit.visibility = View.GONE
                    iv_search.visibility = View.GONE
                }
            }
        }
        ll_content.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                currentFocus?.let {

                    val mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    mInputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                    edit_search.clearFocus()
                    return false

                }
                return false
            }

        })
        rv_list.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                currentFocus?.let {

                    val mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    mInputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                    edit_search.clearFocus()
                    return false

                }
                return false
            }

        })
        edit_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                    Log.d("EditorInfo", "当前点击了")
                    agreeCourse()
                    return false
                }
                Log.d("EditorInfo", "当前点击了qita")
                return true
            }
        })


        }

    private lateinit var layoutManager: LinearLayoutManager
    private fun initAdapter() {
        val listClass = resources.getStringArray(R.array.identify_types).toMutableList()
        popWindow = SpinnerPopWindow(this,
            listClass,
            PopUpAdapter.OnItemClickListener { _, _, position ->
                tv_cg_select.text = listClass[position]
                skillSelect = if (position == listClass.size -1){
                    -1
                } else {
                    position + 1
                }
                page = 1
                agreeCourse()
                popWindow!!.dismiss()
            })
        changguan_eat.setOnClickListener {
            showPopwindow(popWindow!!,changguan_eat)

        }
        adapter = TeacherCgSelectAdapter(data)
        layoutManager = LinearLayoutManager(this)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter
        val view = LayoutInflater.from(this).inflate(R.layout.enpty_view, rv_list, false)
        adapter.emptyView = view
        adapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.rl_detail -> {
                    //ClassShouquanActivity
                    val intent = Intent(this, ChaungguanDetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("cgNum", data[position].areaNum)
                    bundle.putInt("listItem", position)
                    intent.putExtras(bundle)
                    startActivityForResult(intent, 1)
                }

                R.id.ck_select -> {
                    //选中或者取消选中
                    if ((view as CheckBox).isChecked){
                        Log.d("item","点击了")
                        val bindingCgBean = BindingCgBean()
                        bindingCgBean.areaNum = data[position].areaNum
                        bindingCgBean.teacherNum = SpUtils.getString(this,AppConstants.USER_NAME)
                        submitList.add(bindingCgBean)
                    } else {
                        Log.d("item","取消了")
                        for (i in 0 until submitList.size){
                            if (submitList[i].areaNum == data[position].areaNum){
                                submitList.removeAt(i)
                                return@setOnItemChildClickListener
                            }
                        }

                    }
                }
            }
        }

        refresh_layout.setEnableRefresh(false)
//        refresh_layout.setEnableLoadMore(false)
//        refresh_layout.setOnRefreshListener {
//            //下拉刷新
//            refresh_layout.finishRefresh(2000/*,false*/)
//        }
        refresh_layout.setOnLoadMoreListener {
            //上拉加载
            page++
            agreeCourse()
            refresh_layout.finishLoadMore(2000/*,false*/)
        }
    }

    private fun initArea(list:List<String>){
        popWindowArea = SpinnerPopWindow(this,
            list,
            PopUpAdapter.OnItemClickListener { _, _, position ->
                tv_location.text = list[position]
               //
                getLatlon(list[position])
                popWindowArea!!.dismiss()
            })
        ll_location.setOnClickListener {
            showPopwindow(popWindowArea!!,ll_location)

        }
    }

    private fun showPopwindow(pop: SpinnerPopWindow<String>, view: View){
        pop.width = view.width
        pop.showAsDropDown(view)

    }
    private var popWindow: SpinnerPopWindow<String>? = null
    private var popWindowArea: SpinnerPopWindow<String>? = null

    private fun requsetData(citycd:String) {
        val params = HashMap<String, Any>()
        params["citycd"] = citycd

        subscription = Network.getInstance("获取所有三级城市列表", this)
            .findAllCity(
                params,
                ProgressSubscriber("获取所有三级城市列表", object : SubscriberOnNextListener<Bean<AddressBean>> {
                    override fun onNext(result: Bean<AddressBean>) {
                        val list:MutableList<String> = ArrayList()
                        for (i in 0 until result.data.area.size){
                            list.add(result.data.area[i].distnm)
                        }
                        //申请成功
                        initArea(list)
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }

    private fun agreeCourse() {
        val params = HashMap<String, Any>()
        params["teacherNum"] = SpUtils.getString(applicationContext, AppConstants.USER_NAME)
//        params["genTeacherNum"] = SpUtils.getString(applicationContext, AppConstants.USER_NAME)
        params["page"] = page
        params["longitude"] = longitude
        params["latitude"] = latitude
//        params["province"] = province
//        params["city"] = city
        params["district"] = district
        if (skillSelect > -1){
            params["type"] = skillSelect
        }
        if (edit_search.text.toString() != ""){
            params["data"] = edit_search.text.toString().trim()
        }
        val subscription = Network.getInstance("场馆列表", this)
            .bindingAreaList(
                params,
                ProgressSubscriber("场馆列表", object : SubscriberOnNextListener<Bean<CgListBean>> {
                    override fun onNext(result: Bean<CgListBean>) {
                        submitList.clear()
                        if (result.data.areaList.size <=0){
                            refresh_layout.setEnableLoadMore(false)
                        } else {
                            refresh_layout.setEnableLoadMore(true)
                        }
                        if (page == 1){
                            data.clear()
                            data.addAll(result.data.areaList)
                        } else{
                            data.addAll(result.data.areaList)
                        }
                        adapter.notifyDataSetChanged()
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }

    private fun submitData(){

        bean.areaNumList = submitList
        subscription = Network.getInstance("绑定场馆", this)
            .bindingArea(bean,
                ProgressSubscriber("绑定场馆", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
                        //提交成功
                        SuperCustomToast.getInstance(this@ClassShouquanActivity)
                            .show("申请绑定场馆已提交",2000)
                        finish()
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data1: Intent?) {
        super.onActivityResult(requestCode, resultCode, data1)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //接受回来对数据的处理
                val item = data1!!.getIntExtra("item", -1)
//                Log.e("1", "run:---------> $dataStringExtra2")
                if (item > -1) {
                    val childAt = layoutManager.findViewByPosition(item)
                    if (childAt != null) {
                        val chabox = childAt.findViewById<CheckBox>(R.id.ck_select)
                        chabox.isChecked = true
                    }
                    val bindingCgBean = BindingCgBean()
                    bindingCgBean.areaNum = data[item].areaNum
                    bindingCgBean.teacherNum = SpUtils.getString(this,AppConstants.USER_NAME)
                    submitList.add(bindingCgBean)
                }


            }
        }
    }

    private fun initGaode() {
        //初始化定位
        mLocationClient = AMapLocationClient(this)
        //设置定位回调监听
        mLocationClient!!.setLocationListener(this)
        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption()

        mLocationOption!!.isOnceLocation = true
        //        mLocationOption.setOnceLocationLatest(true);
        // 同时使用网络定位和GPS定位,优先返回最高精度的定位结果,以及对应的地址描述信息
        mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。默认连续定位 切最低时间间隔为1000ms
        //        mLocationOption.setInterval(3500);
        //给定位客户端对象设置定位参数
        mLocationClient!!.setLocationOption(mLocationOption)
        //启动定位
        mLocationClient!!.startLocation()
    }

    @AfterPermissionGranted(PERMISSIONS)
    private fun requestPermission() {
        if (EasyPermissions.hasPermissions(this, *mPerms)) {
            //Log.d(TAG, "onClick: 获取读写内存权限,Camera权限和wifi权限");
            initGaode()

        } else {
            EasyPermissions.requestPermissions(this, "获取读写内存权限,Camera权限和wifi权限", PERMISSIONS, *mPerms)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.size > 0) {  //有权限
                // 获取到权限，作相应处理
                initGaode()
            } else {
                //                    showGPSContacts();
            }
            else -> {
                agreeCourse()
            }
        }
        Log.i("permission", "quan xian fan kui")
        //如果用户取消，permissions可能为null.

    }

    companion object {

        private const val PERMISSIONS = 100//请求码
    }

    private fun getLatlon(address:String){
        val geocodeSearch= GeocodeSearch(this)
        geocodeSearch.setOnGeocodeSearchListener(object : GeocodeSearch.OnGeocodeSearchListener{
            override fun onRegeocodeSearched(p0: RegeocodeResult?, p1: Int) {

            }

            override fun onGeocodeSearched(geocodeResult: GeocodeResult?, i: Int) {
                if (i == 1000){
                    if (geocodeResult?.geocodeAddressList != null &&
                        geocodeResult.geocodeAddressList.size>0){
                        val geocodeAddress = geocodeResult.getGeocodeAddressList().get(0)
                        latitude = geocodeAddress.latLonPoint.latitude//纬度
                        longitude = geocodeAddress.latLonPoint.longitude//经度
                        val adcode = geocodeAddress.adcode//区域编码
                        province = adcode.toString().substring(0,2)+"0000"
                        city = adcode.toString().substring(0,4)+"00"
                        district = adcode
                        agreeCourse()

                    }
                } else{
                    //地址输入错误
                }
            }

        })
        val geocodeQuery = GeocodeQuery(address.trim(),"29")
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery)

    }


}
