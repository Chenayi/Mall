package com.kzj.mall.http.api

import com.kzj.mall.entity.*
import com.kzj.mall.entity.home.HomeRecommendEntity
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/kzj/api/app_send_sms.htm")
    fun requestCode(@Field("mobile") mobile: String?, @Field("type") type: String?)
            : Observable<BaseResponse<CodeEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/regist_by_mobile.htm")
    fun register(@Field("mobile") mobile: String?, @Field("code") code: String?, @Field("password") password: String?)
            : Observable<BaseResponse<RegisterEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/code_check_login.htm")
    fun loginByCode(@Field("mobile") mobile: String?, @Field("code") code: String?)
            : Observable<BaseResponse<LoginEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/check_login.htm")
    fun loginByPassword(@Field("username") username: String?, @Field("password") password: String?)
            : Observable<BaseResponse<LoginEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/retrieve_password.htm")
    fun resetPassword(@Field("mobile") mobile: String?, @Field("code") code: String?, @Field("newPassword") newPassword: String?)
            : Observable<BaseResponse<LoginEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/search_goods.htm")
    fun search(@FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<SearchEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/search_goods_by_cid.htm")
    fun searchWithCid(@FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<SearchEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/search_goods_by_brand.htm")
    fun searchWithBrandId(@FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<SearchEntity>>

    @POST("/kzj/api/get_category_one.htm")
    fun requestClassifyLeft(): Observable<BaseResponse<ClassifyLeftEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/search_Category_by_cid.htm")
    fun requestClassifyRight(@Field("c_id") cid: Int?): Observable<BaseResponse<ClassifyRightEntity>>

    @POST("/kzj/api/app_index.htm")
    fun requeseHomeDatas(): Observable<BaseResponse<HomeEntity>>

    @POST("/kzj/api/app_nanke_index.htm")
    fun requestAndrologyDatas(): Observable<BaseResponse<HomeEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/search_goods_by_cid.htm")
    fun loadRecommendHomeDatas(@Field("pageNo") pageNo: Int?, @Field("pageSize") pageSize: Int?, @Field("c_id") cid: String?)
            : Observable<BaseResponse<HomeRecommendEntity>>


    /**
     * 商品详情
     */
    @FormUrlEncoded
    @POST("/kzj/api/get_goods_info.htm")
    fun requestGoodsDetail(@FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<GoodsDetailEntity>>

    /**
     * 立即购买
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/buy_now.htm")
    fun buyNow(@Header("token") token: String?, @FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<BuyEntity>>
}