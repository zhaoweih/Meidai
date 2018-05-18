package me.zhaoweihao.shopping.data


/**
 * Created by zhao weihao on 2018/4/5.
 */

interface OnStringListener {

    /**
     * 请求成功时回调
     * @param result
     */
    fun onSuccess(result: String)

    /**
     * 请求失败时回调
     * @param error
     */
    fun onError(error: String)
}
