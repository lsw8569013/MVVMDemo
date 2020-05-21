package com.ironant.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: lsw
 * Created by lsw on 2017/11/15.
 *
 * 持有 message status 字段
 * GsonFormat  插件工具自动生成bean
 */
public class LoginInfo extends BaseBean implements Parcelable {


    /**
     * 公司id
     */
    private String cid;
    /**
     * 用户id
     */
    private String aid;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cid);
        dest.writeString(this.aid);
    }

    public LoginInfo() {
    }

    protected LoginInfo(Parcel in) {
        this.cid = in.readString();
        this.aid = in.readString();
    }

    public static final Creator<LoginInfo> CREATOR = new Creator<LoginInfo>() {
        @Override
        public LoginInfo createFromParcel(Parcel source) {
            return new LoginInfo(source);
        }

        @Override
        public LoginInfo[] newArray(int size) {
            return new LoginInfo[size];
        }
    };

    @Override
    public String toString() {
        return "LoginInfo{" +
                "cid='" + cid + '\'' +
                ", aid='" + aid + '\'' +
                '}';
    }
}
