package com.noplugins.keepfit.coachplatform.callback;

import com.noplugins.keepfit.coachplatform.bean.CheckInformationBean;

public interface ImageCompressCallBack {
    void onSucceed(String data, boolean b);
    void onSucceed2(String data,CheckInformationBean.CoachPicTeachingsBean teachingsBean, String expectKey, int position);
    void onFailure(String msg);
}
