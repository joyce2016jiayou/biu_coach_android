package com.noplugins.keepfit.coachplatform.activity.manager

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.PopUpAdapter
import com.noplugins.keepfit.coachplatform.adapter.TeacherCgSelectAdapter
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.bean.manager.CgListBean
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.coachplatform.util.ui.pop.SpinnerPopWindow
import kotlinx.android.synthetic.main.activity_class_shouquan.*
import java.util.HashMap

class ClassShouquanActivity : BaseActivity() {

    lateinit var adapter: TeacherCgSelectAdapter
    private var data: MutableList<CgListBean.AreaListBean> = ArrayList()
    private val latitude = ""
    private val longitude = ""
    private var page = 1
    private var skillSelect = -1
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_class_shouquan)
        initAdapter()
        agreeCourse()
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.clickWithTrigger {
            finish()
        }
        queren_btn.clickWithTrigger {
            //todo 确认选择
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
                }
            }
        }

        refresh_layout.setEnableRefresh(false)
//        refresh_layout.setOnRefreshListener {
//            //下拉刷新
//            refresh_layout.finishRefresh(2000/*,false*/)
//        }
        refresh_layout.setOnLoadMoreListener {
            //上拉加载
            refresh_layout.finishLoadMore(2000/*,false*/)
        }
    }

    private fun showPopwindow(pop: SpinnerPopWindow<String>, view: View){
        pop.width = view.width
        pop.showAsDropDown(view)

    }
    private var popWindow: SpinnerPopWindow<String>? = null

    private fun agreeCourse() {
        val params = HashMap<String, Any>()
//        params["teacherNum"] = SpUtils.getString(activity, AppConstants.USER_NAME)
        params["teacherNum"] = "GEN23456"
        params["page"] = page
        params["longitude"] = longitude
        params["latitude"] = latitude
        if (skillSelect > -1){
            params["type"] = skillSelect
        }
        val subscription = Network.getInstance("场馆列表", this)
            .bindingAreaList(
                params,
                ProgressSubscriber("场馆列表", object : SubscriberOnNextListener<Bean<CgListBean>> {
                    override fun onNext(result: Bean<CgListBean>) {
//                        setting(result.data.areaList)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //接受回来对数据的处理
                val item = data!!.getIntExtra("item", -1)
//                Log.e("1", "run:---------> $dataStringExtra2")
                if (item > -1) {
                    val childAt = layoutManager.findViewByPosition(item)
                    if (childAt != null) {
                        val chabox = childAt.findViewById<CheckBox>(R.id.ck_select)
                        chabox.isChecked = true
                    }
                }


            }
        }
    }

}
