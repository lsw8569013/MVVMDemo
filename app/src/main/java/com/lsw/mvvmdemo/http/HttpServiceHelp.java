package com.lsw.mvvmdemo.http;




import com.ironant.common.bean.LoginBean;
import com.ironant.common.bean.LoginInfo;
import com.lsw.mvvmdemo.bean.WeatherBean;


import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 定义 具体的请求接口（请求类型 参数 ）
 * Created by Administrator on 2017/10/18.
 */
public class HttpServiceHelp {

    /**
     *test
     */
    public interface getWeather{

        @GET("weather/json.shtml")
        Call<WeatherBean> getWeather(@Query("city") String city);
    }

    /**
     * 登录接口口
     */
    public interface Login{
        @FormUrlEncoded
        @POST("account/login")
        Observable<LoginInfo> Login(@Field("phone") String phone, @Field("password") String passwd);
    }

    /**
     * 获取待办的 count 集合
     */
//    public interface getCountI{
//        @FormUrlEncoded
//        @POST("apply/getApplyCount")
//        Observable<DealCountBean> getCountM(@Field("cid") String cid, @Field("aid") String aid);
//
//    }

    /**
     * 获取用户信息 集合
     */
//    public interface getInfo{
//        @FormUrlEncoded
//        @POST("account/getInfo")
//        Observable<InfoBackBean> getInfo(@Field("cid") String cid, @Field("aid") String aid);
//
//    }
    /**
     * 获取待审核信息列表
     */
//    public interface getWaitCheckListI{
//        @FormUrlEncoded
//        @POST("apply/getWaitProcessed")
//        Observable<WaitCheckBean> getWaitCheckList(@Field("cid") String cid, @Field("aid") String aid, @Field("pageid") int pageid);
//    }
//
//    /**
//     * 获取待签合同 信息列表
//     */
//    public interface getWaitSignContractI{
//        @FormUrlEncoded
//        @POST("apply/getWaitSignContract")
//        Observable<WaitCheckBean> getSignContractList(@Field("cid") String cid, @Field("aid") String aid, @Field("pageid") int pageid);
//
//    }
//    /**
//     * 获取待放款 信息列表
//     */
//    public interface getGivewMoneyListI{
//        @FormUrlEncoded
//        @POST("apply/getWaitLoan")
//        Observable<WaitCheckBean> getGivewMoneyList(@Field("cid") String cid, @Field("aid") String aid, @Field("pageid") int pageid);
//
//    }
//    /**
//     * 获取资料填写 信息列表
//     */
//    public interface getInfoFillListI{
//        @FormUrlEncoded
//        @POST("apply/getInFill")
//        Observable<WaitCheckBean> getInfoFillList(@Field("cid") String cid, @Field("aid") String aid, @Field("pageid") int pageid);
//
//    }
//    /**
//     * 获取拒绝贷款  信息列表
//     */
//    public interface getRefuseLoanListI{
//        @FormUrlEncoded
//        @POST("apply/getAuditDeny")
//        Observable<WaitCheckBean> getRefuseLoanList(@Field("cid") String cid, @Field("aid") String aid, @Field("pageid") int pageid);
//
//    }
//
//    /**
//     * 获取逾期  信息列表
//     */
//    public interface getOverDueListDataI{
//        @FormUrlEncoded
//        @POST("fenqi/getOverdueList")
//        Observable<OverDueListBean> getOverDueList(@Field("cid") String cid, @Field("aid") String aid, @Field("pageid") int pageid);
//
//    }
//
//    /**
//     * 放款列表接口
//     */
//    public interface getLoadListI{
//        @FormUrlEncoded
//        @POST("loanorder/getLoanList")
//        Observable<GMListListBean> getLoanList(@Field("aid") String aid, @Field("cid") String cid, @Field("weeksId") String weeksId);
//    }

    /**
     * 回款列表接口
     */
//    public interface getBackMoneyListI{
//        @FormUrlEncoded
//        @POST("fenqi/getBackMoneyList")
//        Observable<BMListListBean> getBackMoneyList(@Field("aid") String aid, @Field("cid") String cid, @Field("weeksId") String weeksId);
//    }

    public interface LoginT{

        @FormUrlEncoded
        @POST("account/login")
        Observable<LoginInfo> Login(@Field("phone") String phone, @Field("password") String passwd
                , @Field("isTest") String isTest);
    }

    public interface getWeatherRX{

//        @GET("weather/json.shtml")
//        Observable<WeatherBean> getWeather(@Query("city") String city);

        @GET("/api")
        Observable<WeatherBean> getWeather(@Query("version") String version
        ,@Query("appid") String appid
                ,@Query("appsecret") String appsecret);
    }

    /**
     * ddemo
     */
    public interface LoginTR {
        @POST("identity/login")
        Call<LoginBean> getList(@Body RequestBody body);
    }

    /**
     * 获取上传凭证
     */
    public interface GetUploadInfo {
        @GET("identity/{userId}/upload-info")
        Call<LoginBean> getInfo(@Path("userId") String userId);
    }

    /**
     * D页修改登录密码
     */
    public interface ChangeLoginPassword {
        @POST("identity/{userId}/password-change")
        Call<LoginBean> getInfo(@Path("userId") String userId, @Body RequestBody body);
    }

}
