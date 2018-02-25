package me.zhaoweihao.shopping;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2018/2/20.
 */

public class Login {
    private String phone;
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    Gson gson = new Gson();

    String[] ints2 = gson.fromJson("[1,2,3,4,5]", String[].class);
}
