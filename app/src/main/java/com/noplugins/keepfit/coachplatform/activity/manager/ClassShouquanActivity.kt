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
import androidx.recyclerview.widget.LinearLayoutManager
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.TeacherCgSelectAdapter
import com.noplugins.keepfit.coachplatform.base.BaseActivity
import com.noplugins.keepfit.coachplatform.global.clickWithTrigger
import kotlinx.android.synthetic.main.activity_class_shouquan.*

class ClassShouquanActivity : BaseActivity() {

    lateinit var adapter: TeacherCgSelectAdapter
    private var data: MutableList<String> = ArrayList()
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_class_shouquan)
        initAdapter()
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
        data.add("")
        data.add("")
        data.add("")
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
                    bundle.putString("cgNum", "")
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
