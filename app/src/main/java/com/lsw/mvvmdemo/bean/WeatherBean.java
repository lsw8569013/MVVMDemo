package com.lsw.mvvmdemo.bean;



//"date":"20171018","message":"Success !","status":200,"city":"北京","count":2,

import android.os.Parcel;

import com.ironant.common.bean.BaseBean;


/**
 * 如果有公共的参数 可以把公共参数放到 baseBean 中
 */
public class WeatherBean extends BaseBean {
    String date;

    String city;
    int count;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.date);
        dest.writeString(this.city);
        dest.writeInt(this.count);

    }

    public WeatherBean() {
    }

    protected WeatherBean(Parcel in) {
        super(in);
        this.date = in.readString();
        this.city = in.readString();
        this.count = in.readInt();

    }

    public static final Creator<WeatherBean> CREATOR = new Creator<WeatherBean>() {
        @Override
        public WeatherBean createFromParcel(Parcel source) {
            return new WeatherBean(source);
        }

        @Override
        public WeatherBean[] newArray(int size) {
            return new WeatherBean[size];
        }
    };
}
