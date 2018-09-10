package com.kzj.mall.http.api

import com.kzj.mall.entity.*
import com.kzj.mall.entity.address.*
import com.kzj.mall.entity.cart.AddCartEntity
import com.kzj.mall.entity.home.HomeRecommendEntity
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/mobile/kzj/api/app_send_sms.htm")
    fun requestCode(@Field("mobile") mobile: String?, @Field("type") type: String?)
            : Observable<BaseResponse<CodeEntity>>

    @FormUrlEncoded
    @POST("/mobile/kzj/api/regist_by_mobile.htm")
    fun register(@Field("mobile") mobile: String?, @Field("code") code: String?, @Field("password") password: String?)
            : Observable<BaseResponse<RegisterEntity>>

    @FormUrlEncoded
    @POST("/mobile/kzj/api/code_check_login.htm")
    fun loginByCode(@Field("mobile") mobile: String?, @Field("code") code: String?)
            : Observable<BaseResponse<LoginEntity>>

    @FormUrlEncoded
    @POST("/mobile/kzj/api/check_login.htm")
    fun loginByPassword(@Field("username") username: String?, @Field("password") password: String?)
            : Observable<BaseResponse<LoginEntity>>

    @FormUrlEncoded
    @POST("/mobile/kzj/api/retrieve_password.htm")
    fun resetPassword(@Field("mobile") mobile: String?, @Field("code") code: String?, @Field("newPassword") newPassword: String?)
            : Observable<BaseResponse<LoginEntity>>

    @FormUrlEncoded
    @POST("/mobile/kzj/api/search_goods.htm")
    fun search(@FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<SearchEntity>>

    @FormUrlEncoded
    @POST("/mobile/kzj/api/search_goods_by_cid.htm")
    fun searchWithCid(@FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<SearchEntity>>

    @FormUrlEncoded
    @POST("/mobile/kzj/api/search_goods_by_brand.htm")
    fun searchWithBrandId(@FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<SearchEntity>>

    @POST("/mobile/kzj/api/get_category_one.htm")
    fun requestClassifyLeft(): Observable<BaseResponse<ClassifyLeftEntity>>

    @FormUrlEncoded
    @POST("/mobile/kzj/api/search_Category_by_cid.htm")
    fun requestClassifyRight(@Field("c_id") cid: Int?): Observable<BaseResponse<ClassifyRightEntity>>

    @POST("/mobile/kzj/api/app_index.htm")
    fun requeseHomeDatas(): Observable<BaseResponse<HomeEntity>>

    @POST("/mobile/kzj/api/app_nanke_index.htm")
    fun requestAndrologyDatas(): Observable<BaseResponse<HomeEntity>>

    @FormUrlEncoded
    @POST("/mobile/kzj/api/search_goods_by_cid.htm")
    fun loadRecommendHomeDatas(@Field("pageNo") pageNo: Int?, @Field("pageSize") pageSize: Int?, @Field("c_id") cid: String?)
            : Observable<BaseResponse<HomeRecommendEntity>>


    /**
     * 商品详情
     */
    @FormUrlEncoded
    @POST("/mobile/kzj/api/get_goods_info.htm")
    fun requestGoodsDetail(@FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<GoodsDetailEntity>>

    /**
     * 立即购买
     */
    @FormUrlEncoded
    @POST("/mobile/kzj/api/user_voucher/buy_now.htm")
    fun buyNow(@Header("token") token: String?, @FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<BuyEntity>>

    /**
     * 添加购物车
     */
    @FormUrlEncoded
    @POST("/mobile/kzj/api/user_voucher/add_product_toShopCar.htm")
    fun addCart(@Header("token") token: String?, @FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<AddCartEntity>>

    /**
     * 购物车列表
     */
    @POST("/mobile/kzj/api/my_shop_cart.htm")
    fun shopCart(@Header("token") token: String?): Observable<BaseResponse<CartEntity>>

    /**
     * 删除购物车
     */
    @FormUrlEncoded
    @POST("/mobile/kzj/api/user_voucher/delete_shop_cart.htm")
    fun deleteCart(@Header("token") token: String?, @Field("shopping_cart_id") cartId: String?): Observable<BaseResponse<SimpleResultEntity>>

    /**
     * 修改购物车数量
     */
    @FormUrlEncoded
    @POST("/mobile/kzj/api/user_voucher/change_shop_cart.htm")
    fun changeCartNum(@Header("token") token: String?, @Field("shopping_cart_id") cartId: String?,
                      @Field("goods_num") goodsNum: String?): Observable<BaseResponse<CartEntity>>

    /**
     * 购物车结算
     */
    @FormUrlEncoded
    @POST("/mobile/kzj/api/user_voucher/app_shop_submit.htm")
    fun cartBalance(@Header("token") token: String?, @Field("shopping_cart_ids") cartId: LongArray?)
            : Observable<BaseResponse<BuyEntity>>

    /**
     * 提交订单
     */
    @FormUrlEncoded
    @POST("/mobile/kzj/api/user_voucher/submitOrder.htm")
    fun submitOrder(@Header("token") token: String?,
                    @Field("shoppingCartIds") shoppingCartIds: LongArray?,
                    @Field("pay") pay: String?,
                    @Field("remark") remark: String?,
                    @Field("addressId") addressId: String?,
                    @Field("shopSumPrice") shopSumPrice: String?,
                    @Field("shopSumshipping") shopSumshipping: String?)
            : Observable<BaseResponse<ConfirmOrderEntity>>

    /**
     * 我的
     */
    @POST("/mobile/kzj/api/user_voucher/user_index.htm")
    fun requestMine(@Header("token") token: String?): Observable<BaseResponse<MineEntity>>

    /**
     * 获取省份
     */
    @POST("mobile/kzj/api/get_all_province.htm")
    fun requestP(): Observable<BaseResponse<ProvinceEntity>>

    /**
     * 获取市
     */
    @FormUrlEncoded
    @POST("mobile/kzj/api/get_all_citybypid.htm")
    fun requestC(@Field("provinceId") provinceId: String?): Observable<BaseResponse<CityEntity>>

    /**
     * 获取区
     */
    @FormUrlEncoded
    @POST("mobile/kzj/api/get_all_districtbycid.htm")
    fun requestD(@Field("cityId") cityId: String?): Observable<BaseResponse<DistrictEntity>>


    /**
     * 增加或修改地址
     */
    @FormUrlEncoded
    @POST("mobile/kzj/api/user_voucher/addOrUpdate_address.htm")
    fun addOrUpdateAddress(@Header("token") token: String?, @FieldMap params: Map<String, String>?): Observable<BaseResponse<CreateAddressEntity>>

    /**
     *  我的地址
     */
    @POST("mobile/kzj/api/user_voucher/my_address.htm")
    fun addressList(@Header("token") token: String?): Observable<BaseResponse<AddressEntity>>

    /**
     * 浏览记录
     */
    @FormUrlEncoded
    @POST("mobile/kzj/api/user_voucher/my_browserecord_list.htm")
    fun browseRecords(@Header("token") token: String?, @Field("pageNo") pageNo: Int?, @Field("pageSize") pageSize: Int?)
            : Observable<BaseResponse<BrowseRecordEntity>>

    /**
     * 删除浏览记录
     */
    @FormUrlEncoded
    @POST("mobile/kzj/api/user_voucher/batch_delete_browserecord.htm")
    fun deleteBrowseRecords(@Header("token") token: String?, @Field("like_ids") likeIds: LongArray?)
            : Observable<BaseResponse<SimpleResultEntity>>

    /**
     * 订单列表
     */
    @FormUrlEncoded
    @POST("mobile/kzj/api/user_voucher/my_order_list.htm")
    fun myOrderList(@Header("token") token: String?, @FieldMap params: MutableMap<String, String>?)
            : Observable<BaseResponse<OrderEntity>>
}