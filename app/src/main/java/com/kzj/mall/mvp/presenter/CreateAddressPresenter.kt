package com.kzj.mall.mvp.presenter

import android.content.Context
import com.google.gson.Gson
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.JsonBean
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.CreateAddressContract
import com.kzj.mall.utils.GetJsonDataUtil
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Consumer
import org.json.JSONArray
import java.util.ArrayList
import javax.inject.Inject

@ActivityScope
class CreateAddressPresenter @Inject
constructor(model: CreateAddressContract.Model?, view: CreateAddressContract.View?, context: Context?)
    : BasePresenter<CreateAddressContract.Model, CreateAddressContract.View>(model, view, context) {
    private var options1Items = ArrayList<String>()
    private val options2Items = ArrayList<MutableList<String>>()
    private val options3Items = ArrayList<MutableList<MutableList<String>>>()

    private var isLoaded = false

    fun requestArea() {
        if (!isLoaded) {
            Observable.create(object : ObservableOnSubscribe<Boolean> {
                override fun subscribe(emitter: ObservableEmitter<Boolean>) {
                    initJsonData()
                    emitter.onNext(true)
                }
            })
                    .compose(RxScheduler.compose())
                    .subscribe(object : Consumer<Boolean> {
                        override fun accept(t: Boolean?) {
                            view?.retundArea(options1Items, options2Items, options3Items)
                            isLoaded = true
                        }
                    })
        }else{
            view?.retundArea(options1Items, options2Items, options3Items)
        }
    }

    private fun initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         */
        val JsonData = GetJsonDataUtil().getJson(context, "province.json")//获取assets目录下的json文件数据

        val jsonBean = parseData(JsonData)//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        for (i in jsonBean.indices) {//遍历省份
            options1Items.add(jsonBean?.get(i).name)

            val CityList = ArrayList<String>()//该省的城市列表（第二级）
            val Province_AreaList = ArrayList<MutableList<String>>()//该省的所有地区列表（第三极）

            for (c in 0 until jsonBean.get(i).getCityList().size) {//遍历该省份的所有城市
                val CityName = jsonBean.get(i).getCityList().get(c).getName()
                CityList.add(CityName)//添加城市
                val City_AreaList = ArrayList<String>()//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null || jsonBean.get(i).getCityList().get(c).getArea().size == 0) {
                    City_AreaList.add("")
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea())
                }
                Province_AreaList.add(City_AreaList)//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList)

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList)
        }
    }

    fun parseData(result: String): ArrayList<JsonBean> {//Gson 解析
        val detail = ArrayList<JsonBean>()
        try {
            val data = JSONArray(result)
            val gson = Gson()
            for (i in 0 until data.length()) {
                val entity = gson.fromJson<JsonBean>(data.optJSONObject(i).toString(), JsonBean::class.java)
                detail.add(entity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return detail
    }
}