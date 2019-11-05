package com.noplugins.keepfit.coachplatform.fragment.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.MessageAdapter
import com.noplugins.keepfit.coachplatform.base.BaseFragment
import com.noplugins.keepfit.coachplatform.bean.MessageBean
import com.noplugins.keepfit.coachplatform.bean.MessageListBean
import com.noplugins.keepfit.coachplatform.global.AppConstants
import com.noplugins.keepfit.coachplatform.util.SpUtils
import com.noplugins.keepfit.coachplatform.util.net.Network
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.fragment_manager_teacher_1.*
import java.util.HashMap

class MessageCgFragment : BaseFragment(){
    companion object {
        fun newInstance(title: String): MessageCgFragment {
            val fragment = MessageCgFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    var newView: View? = null

    var data:MutableList<MessageBean> = ArrayList()
    var adapter: MessageAdapter?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_manager_teacher_1, container, false)
        }
        return newView
    }

    var page = 1
    var maxPage = 0
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()
        requestData()
    }

    private fun initAdapter(){
        adapter = MessageAdapter(data)
        rv_list.layoutManager = LinearLayoutManager(context)
        val view = LayoutInflater.from(context).inflate(R.layout.enpty_view, rv_list, false)
        adapter!!.emptyView = view
        rv_list.adapter = adapter
        adapter!!.setOnItemChildClickListener { adapter, view, position ->
            if (data[position].deleted == 1){
                requestReadMessage(data[position].messageNum)
            }
        }

        refresh_layout.setOnRefreshListener {
            //下拉刷新
            page = 1
            requestData()
            refresh_layout.setEnableLoadMore(true)
            refresh_layout.finishRefresh(2000/*,false*/)
        }
        refresh_layout.setOnLoadMoreListener {
            //上拉加载
            page++
            requestData()
            refresh_layout.finishLoadMore(2000/*,false*/)
        }
    }

    private fun requestData(){
        val params = HashMap<String, Any>()
        params["userNum"] = SpUtils.getString(activity, AppConstants.USER_NAME)
        params["page"] = page
        params["type"] = 2
        val subscription = Network.getInstance("消息列表", activity)
            .coachMessageList(params,
                ProgressSubscriber("消息列表", object : SubscriberOnNextListener<Bean<MessageListBean>> {
                    override fun onNext(result: Bean<MessageListBean>) {
                        maxPage = result.data.maxPage
                        if (page == 1){
                            data.clear()
                            data.addAll(result.data.messageList)
                        } else{
                            data.addAll(result.data.messageList)
                        }
                        adapter!!.notifyDataSetChanged()
                        if (maxPage == page){
                            refresh_layout.setEnableLoadMore(false)
                        }

                    }

                    override fun onError(error: String) {


                    }
                }, activity, false)
            )

    }

    private fun requestReadMessage(messageNum:String){
        val params = HashMap<String, Any>()
        params["messageNum"] = messageNum
        params["type"] = 2
        val subscription = Network.getInstance("读消息", activity)
            .readMessage(params,
                ProgressSubscriber("读消息", object : SubscriberOnNextListener<Bean<String>> {
                    override fun onNext(result: Bean<String>) {

                    }

                    override fun onError(error: String) {


                    }
                }, activity, false)
            )

    }
}
