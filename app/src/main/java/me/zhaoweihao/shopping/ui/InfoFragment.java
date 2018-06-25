package me.zhaoweihao.shopping.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import me.zhaoweihao.shopping.R;
import me.zhaoweihao.shopping.database.UserInfo;
import me.zhaoweihao.shopping.goods.CollectionActivity;
import me.zhaoweihao.shopping.goods.MyGoodsActivity;
import me.zhaoweihao.shopping.goods.PublishGoodsActivity;
import me.zhaoweihao.shopping.trade.PurchaserTradeActivity;
import me.zhaoweihao.shopping.trade.SellerTradeActivity;
import me.zhaoweihao.shopping.user.ConcernActivity;

public class InfoFragment extends Fragment {

    private LinearLayout[] linearLayouts;

    private int[] lls = {R.id.ll_user_collect, R.id.ll_user_concern, R.id.ll_user_trade_purchaser, R.id.ll_user_trade_seller,
    R.id.ll_publish_goods, R.id.ll_my_goods};

    private int[] tvs = {R.id.tv_username, R.id.tv_phone, R.id.tv_usercoin, R.id.tv_location, R.id.tv_userauthenticated};

    private TextView[] textViews;

    public InfoFragment() {

    }

    public static InfoFragment newInstance() { return new InfoFragment(); }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {

        UserInfo userInfo = DataSupport.findFirst(UserInfo.class);

        if (userInfo == null || userInfo.getUserAuthenticated() == null) {
            Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] strings = {userInfo.getUserName(), userInfo.getUserPhone(), String.valueOf(userInfo.getUserCoin()),
        userInfo.getUserAddressMore(), userInfo.getUserAuthenticated() == 1 ? "已实名":"未实名"};

        textViews = new TextView[tvs.length];

        for (int i = 0;i < tvs.length; i++) {
            textViews[i] = view.findViewById(tvs[i]);
            textViews[i].setText(strings[i]);
        }

        Class[] classes = {CollectionActivity.class, ConcernActivity.class, PurchaserTradeActivity.class,
                SellerTradeActivity.class, PublishGoodsActivity.class, MyGoodsActivity.class};

        linearLayouts = new LinearLayout[lls.length];

        for (int i = 0; i < lls.length; i++) {
            linearLayouts[i] = view.findViewById(lls[i]);

            int finalI = i;
            linearLayouts[i].setOnClickListener(view1 -> {
                Intent intent = new Intent(getActivity(), classes[finalI]);
                startActivity(intent);
            });
        }

    }
}

