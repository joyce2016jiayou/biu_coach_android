package cn.qqtheme.framework.wheelpicker.contract;

import cn.qqtheme.framework.wheelpicker.entity.ProvinceEntity;

import java.util.List;

/**
 * 地址数据加载监听器
 *
 * @author <a href="mailto:1032694760@qq.com">liyujiang</a>
 * @date 2019/6/17 16:42
 */
public interface AddressDataLoadListener {

    void onDataLoadStart();

    void onDataLoadSuccess(List<ProvinceEntity> data);

    void onDataLoadFailure();

}
