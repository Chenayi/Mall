package com.kzj.mall.http.api

import com.kzj.mall.entity.*
import com.kzj.mall.entity.address.*
import com.kzj.mall.entity.ask.AskAnswerDetailEntity
import com.kzj.mall.entity.ask.AskAnswerEntity
import com.kzj.mall.entity.ask.AskAnswerTypeEntity
import com.kzj.mall.entity.cart.AddCartEntity
import com.kzj.mall.entity.cart.CartRecommendEntity
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.entity.order.ConfirmOrderEntity
import com.kzj.mall.entity.order.OrderDetailEntity
import com.kzj.mall.entity.order.OrderEntity
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

    @FormUrlEncoded
    @POST("/kzj/api/search_goods_by_cid.htm")
    fun loadCartRecommendDatas(@Field("pageNo") pageNo: Int?, @Field("pageSize") pageSize: Int?, @Field("c_id") cid: String?)
            : Observable<BaseResponse<CartRecommendEntity>>


    /**
     * 商品详情
     */
    @FormUrlEncoded
    @POST("/kzj/api/get_goods_info.htm")
    fun requestGoodsDetail(@FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<GoodsDetailEntity>>

    /**
     * 商品详情
     */
    @FormUrlEncoded
    @POST("/kzj/api/get_goods_info.htm")
    fun requestGoodsDetailWithLogin(@Header("token") token: String?, @FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<GoodsDetailEntity>>

    /**
     * 立即购买
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/buy_now.htm")
    fun buyNow(@Header("token") token: String?, @FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<BuyEntity>>

    /**
     * 立即收货
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/take_delivery.htm")
    fun takeDelivery(@Header("token") token: String?, @Field("orderId") orderId: String?): Observable<BaseResponse<SimpleResultEntity>>

    /**
     * 处方登记
     */
    @FormUrlEncoded
    @POST("/kzj/api/to_demand_record.htm")
    fun demandRecord(@Header("token") token: String?, @FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<BuyEntity>>

    /**
     * 提交处方登记
     */
    @FormUrlEncoded
    @POST("/kzj/api/app_save_Rxrecord.htm")
    fun submitDemandReord(@Header("token") token: String?, @FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<SimpleResultEntity>>

    /**
     * 收藏商品
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/save_goods_atte.htm")
    fun saveGoodsAtte(@Header("token") token: String?, @Field("goods_info_id") goodsInfoId: String?): Observable<BaseResponse<SimpleResultEntity>>

    /**
     * 添加购物车
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/add_product_toShopCar.htm")
    fun addCart(@Header("token") token: String?, @FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<AddCartEntity>>

    /**
     * 购物车列表
     */
    @POST("/kzj/api/my_shop_cart.htm")
    fun shopCart(@Header("token") token: String?): Observable<BaseResponse<CartEntity>>

    /**
     * 删除购物车
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/delete_shop_cart.htm")
    fun deleteCart(@Header("token") token: String?, @Field("shopping_cart_id") cartId: String?): Observable<BaseResponse<SimpleResultEntity>>

    /**
     * 修改购物车数量
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/change_shop_cart.htm")
    fun changeCartNum(@Header("token") token: String?, @Field("shopping_cart_id") cartId: String?,
                      @Field("goods_num") goodsNum: String?): Observable<BaseResponse<CartEntity>>

    /**
     * 购物车结算
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/app_shop_submit.htm")
    fun cartBalance(@Header("token") token: String?, @Field("shopping_cart_ids") cartId: LongArray?)
            : Observable<BaseResponse<BuyEntity>>

    /**
     * 提交订单
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/submitOrder.htm")
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
    @POST("/kzj/api/user_voucher/user_index.htm")
    fun requestMine(@Header("token") token: String?): Observable<BaseResponse<MineEntity>>

    /**
     * 个人信息
     */
    @POST("/kzj/api/user_voucher/customerInfo.htm")
    fun customerInfo(@Header("token") token: String?): Observable<BaseResponse<CustomerEntity>>

    /**
     * 修改个人信息
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/update_customerInfo.htm")
    fun updateInfo(@Header("token") token: String?, @FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<SimpleResultEntity>>

    /**
     * 我的收藏
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/my_follow_list.htm")
    fun myFollow(@Header("token") token: String?, @Field("goods_type") goosType: String?, @Field("pageNo") pageNo: Int?, @Field("pageSize") pageSize: Int?)
            : Observable<BaseResponse<MyCollectEntity>>

    /**
     * 删除收藏
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/batch_delete_follow.htm")
    fun deleteMyFollow(@Header("token") token: String?, @Field("followIds") followIds: LongArray?)
            : Observable<BaseResponse<SimpleResultEntity>>

    /**
     * 获取省份
     */
    @POST("/kzj/api/get_all_province.htm")
    fun requestP(): Observable<BaseResponse<ProvinceEntity>>

    /**
     * 获取市
     */
    @FormUrlEncoded
    @POST("/kzj/api/get_all_citybypid.htm")
    fun requestC(@Field("provinceId") provinceId: String?): Observable<BaseResponse<CityEntity>>

    /**
     * 获取区
     */
    @FormUrlEncoded
    @POST("/kzj/api/get_all_districtbycid.htm")
    fun requestD(@Field("cityId") cityId: String?): Observable<BaseResponse<DistrictEntity>>


    /**
     * 增加或修改地址
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/addOrUpdate_address.htm")
    fun addOrUpdateAddress(@Header("token") token: String?, @FieldMap params: Map<String, String>?): Observable<BaseResponse<CreateAddressEntity>>

    /**
     *  我的地址
     */
    @POST("/kzj/api/user_voucher/my_address.htm")
    fun addressList(@Header("token") token: String?): Observable<BaseResponse<AddressEntity>>


    /**
     * 删除地址
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/delete_address_by_id.htm")
    fun deleteAddress(@Header("token") token: String?, @Field("addressId") addressId: String?)
            : Observable<BaseResponse<SimpleResultEntity>>

    /**
     * 浏览记录
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/my_browserecord_list.htm")
    fun browseRecords(@Header("token") token: String?, @Field("pageNo") pageNo: Int?, @Field("pageSize") pageSize: Int?)
            : Observable<BaseResponse<BrowseRecordEntity>>

    /**
     * 删除浏览记录
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/batch_delete_browserecord.htm")
    fun deleteBrowseRecords(@Header("token") token: String?, @Field("like_ids") likeIds: LongArray?)
            : Observable<BaseResponse<SimpleResultEntity>>

    /**
     * 订单列表
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/my_order_list.htm")
    fun myOrderList(@Header("token") token: String?, @FieldMap params: MutableMap<String, String>?)
            : Observable<BaseResponse<OrderEntity>>

    /**
     * 订单详情
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/order_info.htm")
    fun orderDetail(@Header("token") token: String?, @Field("orderId") orderId: String?)
            : Observable<BaseResponse<OrderDetailEntity>>

    /**
     * 支付宝key
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/ali_pay_str.htm")
    fun aliPayKey(@Header("token") token: String?, @Field("orderId") orderId: String?)
            : Observable<BaseResponse<AliPayKeyEntity>>

    /**
     * 问答
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/interlucation_list.htm")
    fun askanswer(@Header("token") token: String?, @Field("q_status") qStatus: String?,
                  @Field("pageNo") pageNo: Int?, @Field("pageSize") pageSize: Int?)
            : Observable<BaseResponse<AskAnswerEntity>>

    /**
     * 问答详情
     */
    @FormUrlEncoded
    @POST("/kzj/api/interlucation_info.htm")
    fun interlucationInfo(@Header("token") token: String?, @Field("qId") qId: String?): Observable<BaseResponse<AskAnswerDetailEntity>>

    /**
     * 问答提交
     */
    @FormUrlEncoded
    @POST("/kzj/api/user_voucher/save_interlucation.htm")
    fun saveInterlucation(@Header("token") token: String?, @Field("cat_id") catId: String?, @Field("q_name") content: String?
                          , @Field("user_age") userAge: String?, @Field("user_sex") userSex: String?)
            : Observable<BaseResponse<SimpleResultEntity>>

    /**
     * 问答分类
     */
    @POST("/kzj/api/interlucation_.htm")
    fun interlucationType(): Observable<BaseResponse<AskAnswerTypeEntity>>
}