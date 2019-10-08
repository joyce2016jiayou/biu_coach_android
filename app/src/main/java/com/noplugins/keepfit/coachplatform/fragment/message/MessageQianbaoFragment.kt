package com.noplugins.keepfit.coachplatform.fragment.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.noplugins.keepfit.coachplatform.R
import com.noplugins.keepfit.coachplatform.adapter.MessageQianbaoAdapter
import com.noplugins.keepfit.coachplatform.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_manager_teacher_1.*

class MessageQianbaoFragment : BaseFragment(){
    companion object {
        fun newInstance(title: String): MessageQianbaoFragment {
            val fragment = MessageQianbaoFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    var newView: View? = null

    var data:MutableList<String> = ArrayList()
    var adapter: MessageQianbaoAdapter ?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_manager_teacher_1, container, false)
        }
        return newView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
    }

    private fun initAdapter(){
        data.add("")
        data.add("")
        adapter = MessageQianbaoAdapter(data)
        rv_list.layoutManager = LinearLayoutManager(context)
        val view = LayoutInflater.from(context).inflate(R.layout.enpty_view, rv_list, false)
        adapter!!.emptyView = view
        rv_list.adapter = adapter
    }
}
