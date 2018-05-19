package me.zhaoweihao.shopping.favorites;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import me.zhaoweihao.shopping.constant.Constant;
import me.zhaoweihao.shopping.data.Good;
import me.zhaoweihao.shopping.data.Goods;
import me.zhaoweihao.shopping.data.OnStringListener;
import me.zhaoweihao.shopping.data.StringModelImpl;
import me.zhaoweihao.shopping.utils.HttpUtil;
import me.zhaoweihao.shopping.utils.NewHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FavoritesPresenter implements FavoritesContract.Presenter,OnStringListener{

    private static final String TAG = "FavoritesPresenter";

    private FavoritesContract.View view;
    private Context context;
    private StringModelImpl model;
    private ArrayList<Goods.Data>[] goodsLists = new ArrayList[5];

    public FavoritesPresenter(Context context, FavoritesContract.View view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
        for (int i = 0; i < goodsLists.length; i++) {
            Log.d(TAG, "运行");
            goodsLists[i] = new ArrayList<>();
        }
    }
    @Override
    public void start() {

    }

    @Override
    public void onSuccess(@NotNull String result) {
        Log.d(TAG, "点3");
        Log.d(TAG, "onSuccess " + result);


    }

    @Override
    public void onError(@NotNull String error) {
        view.showLoadError();
        view.stopLoading();
    }

    /**
     * 此方法需要简化
     * @param forceRefresh
     * @param tagNames
     */
    @Override
    public void requestGoodsList(Boolean forceRefresh, String[] tagNames) {
        String[] urls = new String[tagNames.length];
        for (int i = 0; i < urls.length; i++) {
            urls[i] = "http://meidai.maocanhua.cn/get_goods_by_tag?tagName="+tagNames[i]+"&begin=0&num=3";
        }

        view.startLoading();
        if (forceRefresh) {
            goodsLists[0].clear();
        }
        NewHttpUtil.sendGetRequest(urls[0], new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                Goods goods = new Gson().fromJson(body, Goods.class);
                if (goods.getCode() == 200) {
                    goodsLists[0].clear();
                    goodsLists[0].addAll(goods.getData());
                    NewHttpUtil.sendGetRequest(urls[1], new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String body = response.body().string();
                            Goods goods = new Gson().fromJson(body, Goods.class);
                            if (goods.getCode() == 200) {
                                goodsLists[1].clear();
                                goodsLists[1].addAll(goods.getData());
                                NewHttpUtil.sendGetRequest(urls[2], new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        String body = response.body().string();
                                        Goods goods = new Gson().fromJson(body, Goods.class);
                                        if (goods.getCode() == 200) {
                                            goodsLists[2].clear();
                                            goodsLists[2].addAll(goods.getData());
                                            NewHttpUtil.sendGetRequest(urls[3], new Callback() {
                                                @Override
                                                public void onFailure(Call call, IOException e) {

                                                }

                                                @Override
                                                public void onResponse(Call call, Response response) throws IOException {
                                                    String body = response.body().string();
                                                    Goods goods = new Gson().fromJson(body, Goods.class);
                                                    if (goods.getCode() == 200) {
                                                        goodsLists[3].clear();
                                                        goodsLists[3].addAll(goods.getData());
                                                        NewHttpUtil.sendGetRequest(urls[4], new Callback() {
                                                            @Override
                                                            public void onFailure(Call call, IOException e) {

                                                            }

                                                            @Override
                                                            public void onResponse(Call call, Response response) throws IOException {
                                                                String body = response.body().string();
                                                                Goods goods = new Gson().fromJson(body, Goods.class);
                                                                if (goods.getCode() == 200) {
                                                                    goodsLists[4].clear();
                                                                    goodsLists[4].addAll(goods.getData());
                                                                    view.showResult(goodsLists);
                                                                    view.stopLoading();
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    public ArrayList<Goods.Data> getGoodsList() {
        return null;
    }
}
