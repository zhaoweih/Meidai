package me.zhaoweihao.shopping.favorites;

import java.util.ArrayList;
import java.util.List;

import me.zhaoweihao.shopping.BasePresenter;
import me.zhaoweihao.shopping.BaseView;
import me.zhaoweihao.shopping.data.Goods;

public class FavoritesContract {

    interface View extends BaseView<FavoritesContract.Presenter> {
        boolean isActive();

        void showResult(ArrayList<Goods.Data> goodsList);

        void startLoading();

        void stopLoading();

        void showLoadError();

    }

    interface Presenter extends BasePresenter {
        void requestGoodsList(Boolean forceRefresh, String tagName);

        ArrayList<Goods.Data> getGoodsList();

    }
}
