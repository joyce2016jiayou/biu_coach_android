package com.noplugins.keepfit.coachplatform.bean;

import java.util.List;

public class MessageListBean {
    private List<MessageBean> messageList;
    private int maxPage;

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public List<MessageBean> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageBean> messageList) {
        this.messageList = messageList;
    }
}
