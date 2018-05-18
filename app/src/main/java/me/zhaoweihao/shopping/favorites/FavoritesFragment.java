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

    public FavoritesFragment() {
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

        for (int i = 0; i < tagNames.length; i++) {
            recyclerViews[i] = new RecyclerView(getActivity());
            TextView textView = new TextView(getActivity());
            textView.setText("分割线");
            TextView textView1 = new TextView(getActivity());
            textView1.setText("分割线end");
            linearLayout.addView(textView);
            linearLayout.addView(recyclerViews[i]);
            linearLayout.addView(textView1);
        }

        mPresenter.requestGoodsList(true, tagNames);

        mRefreshLayout.setOnRefreshListener(() -> {

            mPresenter.requestGoodsList(true, tagNames);
            stopLoading();
        });

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

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showResult(ArrayList<Goods.Data>[] goodsLists) {
        getActivity().runOnUiThread(() -> {
            for (int i = 0; i < tagNames.length; i++) {
                if (adapters[i] == null) {
                    adapters[i] = new GoodsAdapter(goodsLists[i], 1);
//                recyclerView.setAdapter(adapter);
                    recyclerViews[i].setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    recyclerViews[i].setAdapter(adapters[i]);

                }
                else {
                    adapters[i].notifyDataSetChanged();
                }

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
