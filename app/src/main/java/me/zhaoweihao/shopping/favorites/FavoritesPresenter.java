package me.zhaoweihao.shopping.favorites;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import me.zhaoweihao.shopping.constant.Constant;
import me.zhaoweihao.shopping.data.Good;
import me.zhaoweihao.shopping.data.Goods;
import me.zhaoweihao.shopping.data.OnStringListener;
import me.zhaoweihao.shopping.data.StringModelImpl;

public class FavoritesPresenter implements FavoritesContract.Presenter,OnStringListener{

    private static final String TAG = "FavoritesPresenter";

    private FavoritesContract.View view;
    private Context context;
    private StringModelImpl model;
    private ArrayList<Goods.Data> goodsList = new ArrayList<>();

    public FavoritesPresenter(Context context, FavoritesContract.View view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
    }
    @Override
    public void start() {

    }

    @Override
    public void onSuccess(@NotNull String result) {
        Log.d(TAG, result);
        Goods goods = new Gson().fromJson(result, Goods.class);
        if (goods.getCode() == 200) {
            goodsList.addAll(goods.getData());
            view.showResult(goodsList);
            view.stopLoading();
        }

    }

    @Override
    public void onError(@NotNull String error) {
        view.showLoadError();
        view.stopLoading();
    }

    @Override
    public void requestGoodsList(Boolean forceRefresh, String tagName) {
        String url = "http://meidai.maocanhua.cn/get_goods_by_tag?tagName="+tagName+"&begin=0&num=6";
        view.startLoading();
        if (forceRefresh) {
            goodsList.clear();
        }
        Log.d(TAG, goodsList.toString());
        model.load(url, this);
    }

    @Override
    public ArrayList<Goods.Data> getGoodsList() {
        return goodsList;
    }
}
