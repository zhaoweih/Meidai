package me.zhaoweihao.shopping.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.zhaoweihao.shopping.R;
import me.zhaoweihao.shopping.adapter.GoodsAdapter;
import me.zhaoweihao.shopping.data.Goods;

public class FavoritesFragment extends Fragment implements FavoritesContract.View{

    private static final String TAG = "FavoritesFragment";

    private FavoritesContract.Presenter mPresenter;
    private SwipeRefreshLayout mRefreshLayout;
    private GoodsAdapter[] adapters;
    private View mEmptyView;
//    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private String[] tagNames;
    private RecyclerView[] recyclerViews;
    private int count;

    public FavoritesFragment() {
        count = 0;
    }

    public static FavoritesFragment newInstance() { return new FavoritesFragment(); }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        initViews(view);

        tagNames = new String[] { "衣服", "化妆品"};

        recyclerViews = new RecyclerView[tagNames.length];
        adapters = new GoodsAdapter[tagNames.length];

        Log.d(TAG, "adapter大小" + adapters.length);

        for (int i = 0; i < tagNames.length; i++) {
            Log.d(TAG, "测试点");
            recyclerViews[i] = new RecyclerView(getActivity());
            TextView textView = new TextView(getActivity());
            textView.setText("分割线");
            linearLayout.addView(textView);
            linearLayout.addView(recyclerViews[i]);
            mPresenter.requestGoodsList(true, tagNames[i]);
        }

//        mRefreshLayout.setOnRefreshListener(() -> {
//
//            mPresenter.requestGoodsList(true, "衣服");
////            adapter.notifyDataSetChanged();
//            stopLoading();
//        });

        return view;

    }

    @Override
    public void setPresenter(FavoritesContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        mEmptyView = view.findViewById(R.id.empty_view);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        linearLayout = view.findViewById(R.id.ll);
//        recyclerView = view.findViewById(R.id.rv_cloths);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
//        for (int i = 0; i < tagNames.length; i++) {
//            mPresenter.requestGoodsList(true, tagNames[i]);
//        }

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showResult(ArrayList<Goods.Data> goodsList) {
        Log.d(TAG, "执行");
        Log.d(TAG, goodsList.toString());
        getActivity().runOnUiThread(() -> {
            if (adapters[count] == null) {
                adapters[count] = new GoodsAdapter(goodsList, 1);
//                recyclerView.setAdapter(adapter);
                recyclerViews[count].setLayoutManager(new GridLayoutManager(getActivity(), 3));
                recyclerViews[count].setAdapter(adapters[count]);

            }
            else {
                adapters[count].notifyDataSetChanged();
            }

            if (count < tagNames.length) {
                count ++;
            }
        });
    }

    @Override
    public void startLoading() {
        mRefreshLayout.post(() -> mRefreshLayout.setRefreshing(true));
    }

    @Override
    public void stopLoading() {
        if (mRefreshLayout.isRefreshing()){
            mRefreshLayout.post(() -> mRefreshLayout.setRefreshing(false));
        }
    }

    @Override
    public void showLoadError() {
//        Snackbar.make(recyclerView, "加载失败", Snackbar.LENGTH_SHORT)
//                .setAction("重试", view -> mPresenter.requestGoodsList(false, "衣服")).show();
    }

}
