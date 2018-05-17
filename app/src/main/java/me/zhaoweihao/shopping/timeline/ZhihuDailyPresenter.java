package me.zhaoweihao.shopping.timeline;

import android.content.Context;

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {

    private final ZhihuDailyContract.View mView;
    private Context context;


    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
        this.context = context;
    }




    @Override
    public void start() {

    }
}
