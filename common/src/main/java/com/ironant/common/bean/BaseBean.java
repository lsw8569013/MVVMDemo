package com.ironant.common.bean;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lsw on 2017/2/22.
 */

public  class BaseBean<T> implements Parcelable {

    private String message;
    private int status;
    private T data;


    public static final Creator<BaseBean> CREATOR = new Creator<BaseBean>() {
        @Override
        public BaseBean createFromParcel(Parcel in) {
            return new BaseBean(in);
        }

        @Override
        public BaseBean[] newArray(int size) {
            return new BaseBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeInt(this.status);

    }

    public BaseBean() {
    }

    protected BaseBean(Parcel in) {
        this.message = in.readString();
        this.status = in.readInt();
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



    @Override
    public String toString() {
        return "BaseBean{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }


}
