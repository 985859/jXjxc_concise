package com.yiande.jxjxc.myInterface;


import com.yiande.jxjxc.bean.AdminBean;
import com.yiande.jxjxc.bean.AdminListBean;
import com.yiande.jxjxc.bean.AfterServiceBean;
import com.yiande.jxjxc.bean.AfterServiceBody;
import com.yiande.jxjxc.bean.AfterServiceInfoBean;
import com.yiande.jxjxc.bean.AfterServiceProductBean;
import com.yiande.jxjxc.bean.BalanceBean;
import com.yiande.jxjxc.bean.DataAnalysisBean;
import com.yiande.jxjxc.bean.DebtBean;
import com.yiande.jxjxc.bean.DebtDaetailBean;
import com.yiande.jxjxc.bean.DebtManageBean;
import com.yiande.jxjxc.bean.HomeBean;
import com.yiande.jxjxc.bean.InvoiceRecordBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.KeyBean;
import com.yiande.jxjxc.bean.LoginBean;
import com.yiande.jxjxc.bean.OClassBean;
import com.yiande.jxjxc.bean.OClassBody;
import com.yiande.jxjxc.bean.OrderBody;
import com.yiande.jxjxc.bean.OrderInfoBean;
import com.yiande.jxjxc.bean.OrderListBean;
import com.yiande.jxjxc.bean.ProductBean;
import com.yiande.jxjxc.bean.ProductListBean;
import com.yiande.jxjxc.bean.RoleBean;
import com.yiande.jxjxc.bean.SellUserInfoBean;
import com.yiande.jxjxc.bean.UserBean;
import com.yiande.jxjxc.bean.UserInfoBean;
import com.yiande.jxjxc.bean.UserKeyBean;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/7/8 15:07
 */
public interface HttpApi {

    //String baseUrl = "http://eande.natapp1.cc/api/";
    // String baseUrl = "http://xuan.yiande.com/api/";
    String baseUrl = "http://192.168.2.110:8098/api/";
    // String baseUrl = "http://192.168.2.204:8090/api/";

    /************************* 管理员管理  Admin  ******************************/
    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/14
     * @Description 用户登录
     */
    @POST("Admin/Login")
    @FormUrlEncoded
    Observable<JsonBean<LoginBean>> userLogin(@Field("Admin_Mob") String userName, @Field("Admin_Password") String code);

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/14
     * @Description 管理员列表
     */
    @GET("Admin/GetAdminList")
    Observable<JsonBean<List<AdminListBean>>> getAdminList(@Query("page") int page, @Query("keywords") String keywords, @Query("admin_IsOK") int isoK, @Query("admin_type") int type);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/14
     * @Description 全部管理员列表
     */
    @GET("Admin/GetAdmins")
    Observable<JsonBean<List<AdminListBean>>> getAdmins();

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/14
     * @Description 获取管理权限
     */
    @GET("Admin/GetRoles")
    Observable<JsonBean<List<RoleBean>>> getRoles();


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/14
     * @Description 添加管理员
     */
    @POST("Admin/AdminAdd")
    Observable<JsonBean<Object>> adminAdd(@Body AdminBean bean);

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/14
     * @Description 修改管理员
     */
    @POST("Admin/AdminModify")
    Observable<JsonBean<Object>> adminModify(@Body AdminBean bean);

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/15
     * @Description 获取管理员详细信息
     */
    @GET("Admin/GetAdmin")
    Observable<JsonBean<AdminBean>> getAdmin(@Query("Admin_ID") int adminID);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/15
     * @Description 删除管理员
     */
    @DELETE("Admin/AdminDel")
    Observable<JsonBean<Object>> adminDel(@Query("Admin_ID") int adminID);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/15
     * @Description 首页数据
     */
    @GET("Admin/GetAdminIndex")
    Observable<JsonBean<HomeBean>> getAdminIndex();

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/15
     * @Description 首页数据
     */
    @POST("Admin/PasswordModify")
    @FormUrlEncoded
    Observable<JsonBean<Object>> passwordModify(@Field("Password") String password);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/11/27
     * @Description 获取APP 开启的功能
     */
    @GET("Admin/GetAppFunction")
    Observable<JsonBean<List<KeyBean>>> getAppFunction();

    /**
     * @param type 空或其它：今天 1：本周 2：本月 3：本季度 4:本年
     * @return
     * @author hukui
     * @time 2020/11/27
     * @Description 不同时期数据分析
     */
    @GET("Admin/GetVariousTimeAnalysis")
    Observable<JsonBean<DataAnalysisBean>> getVariousTimeAnalysis(@Query("type") int type);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/21
     * @Description 删除员工
     */
    @PUT("Admin/StaffDel/{id}")
    Observable<JsonBean<Object>> staffDel(@Path("id") int id);


    /**
     * @param entryID 多个ID,ID,ID,ID
     * @return
     * @author hukui
     * @time 2020/12/22
     * @Description 设置快捷入口
     */
    @POST("Admin/QuickEntryConfig")
    @FormUrlEncoded
    Observable<JsonBean<Object>> quickEntryConfig(@Field("EntryID") String entryID);

    /************************* 管理员管理  Admin  ******************************/


    /************************* 客户 供应商  User ******************************/


    /**
     * @return
     * @author hukui
     * @time 2020/9/15
     * @Description 客户 供应商 列表
     */
    @GET("User/GetUserList")
    Observable<JsonBean<List<UserBean>>> getUserList(@Query("page") int page,
                                                     @Query("user_Type") int type, @Query("user_Class1") int user_Class1,
                                                     @Query("user_IsOK") int user_IsOK, @Query("keywords") String keywords);

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/15
     * @Description 客户 供应商 列表s
     */
    @GET("User/GetAllUserList")
    Observable<JsonBean<List<UserBean>>> getAllUserList(@Query("User_Type") String User_Type);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/15
     * @Description 删除 客户 供应商
     */
    @DELETE("User/UserDel")
    Observable<JsonBean<Object>> userDel(@Query("User_Type") int type, @Query("User_ID") int user_ID);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/16
     * @Description 客户 供应商 资料
     */
    @GET("User/GetUser")
    Observable<JsonBean<UserInfoBean>> getUser(@Query("User_ID") int userID, @Query("User_Type") int userType);

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/11/27
     * @Description 添加 客户 供应商
     */
    @POST("User/UserAdd")
    Observable<JsonBean<Object>> userAdd(@Body UserBean bean);

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/11/27
     * @Description 批量添加 客户 供应商
     */
    @POST("User/BatchUserAdd")
    Observable<JsonBean<Object>> userBatchAdd(@Body List<UserBean> bean);

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/11/27
     * @Description 修改 客户 供应商
     */
    @POST("User/UserModify")
    Observable<JsonBean<Object>> userModify(@Body UserBean bean);


    /**
     * @param userID 客户ID
     * @param type   0 消费 1 充值 2扣除
     * @param begin  格式 yyyy-MM-dd
     * @param end    格式 yyyy-MM-dd
     * @return
     * @author hukui
     * @time 2020/12/21
     * @Description
     */

    @GET("User/GetUserBalanceList")
    Observable<JsonBean<BalanceBean>> getUserBalanceList(@Query("page") int page, @Query("uid") int userID, @Query("type") String type
            , @Query("begin") String begin, @Query("end") String end);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/21
     * @Description 客户余额充值
     */
    @POST("User/UserBalanceAdd")
    @FormUrlEncoded
    Observable<JsonBean<Object>> userBalanceAdd(@Field("UID") int uid, @Field("Money") String Money, @Field("Memo") String Memo);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/21
     * @Description 客户余额充值
     */
    @POST("User/UserBalanceDeduct")
    @FormUrlEncoded
    Observable<JsonBean<Object>> userBalanceDeduct(@Field("UID") int uid, @Field("Money") String Money, @Field("Memo") String Memo);


    /************************* 客户 供应商  User ******************************/

    /************************* 产品分类 1,品牌2,单位3   OClass ******************************/


    /**
     * @param flag 产品分类 1,品牌2,单位3
     * @return
     * @author hukui
     * @time 2020/9/16
     * @Description 获取列表
     */

    @GET("OClass/GetOClassList")
    Observable<JsonBean<List<OClassBean>>> getOClassList(@Query("p.oClass_Flag") int flag);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/1
     * @Description 添加分类
     */
    @POST("OClass/OClassAdd")
    Observable<JsonBean<Object>> oClassAdd(@Body OClassBody bean);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/17
     * @Description 修改分类
     */
    @POST("OClass/OClassModify")
    Observable<JsonBean<Object>> oClassModify(@Body OClassBody bean);

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/17
     * @Description 删除分类
     */
    @DELETE("OClass/OClassDel")
    Observable<JsonBean<Object>> oClassDel(@Query("OClass_ID") int oClassID, @Query("OClass_Flag") int flag);

    /************************* 产品分类 1,品牌2,单位3   OClass ******************************/


    /************************* 产品 Product ******************************/

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/18
     * @Description 添加 产品
     */
    @POST("Product/ProductAddV2")
    Observable<JsonBean<Object>> productAdd(@Body ProductBean bean);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/18
     * @Description 修改  产品
     */
    @POST("Product/ProductModifyV2")
    Observable<JsonBean<Object>> productModify(@Body ProductBean bean);


    /**
     * 通过 MultipartBody和@body作为参数来实现多文件上传
     *
     * @param multipartBody MultipartBody包含多个Part
     * @param type          默认 产品图 1:售后 2：发票
     * @time 2020/9/18
     * @Description 多图上传
     */
    @POST("Product/UploadImage")
    Observable<JsonBean<String>> uploadImage(@Body MultipartBody multipartBody, @Query("type") int type);


    /**
     * @time 2020/9/18
     * @Description 产品详情
     */
    @GET("Product/GetProductV2")
    Observable<JsonBean<ProductBean>> getProduct(@Query("id") int Product_ID);

    /**
     * @time 2020/9/18
     * @Description 产品列表
     */
    @GET("Product/GetProductListV2")
    Observable<JsonBean<ProductListBean>> getProductList(@QueryMap Map<String, Object> map);

    /**
     * @time 2020/9/18
     * @Description 产品列表
     */
    @DELETE("Product/ProductDel")
    Observable<JsonBean<Object>> productDel(@Query("Product_ID") int Product_ID);


    /************************* 产品 Product ******************************/


    /************************* 出库/入库单 Order ******************************/


    /**
     * @param type       1:入库 2:报溢 其它:全部
     * @param aID        经手人ID
     * @param begin      开始时间 格式 yyyy-MM-dd
     * @param end        结束时间 格式 yyyy-MM-dd
     * @param text       入库单号搜索
     * @param searchType 0:全部 1:红冲 2:备注 3:有赊账
     * @return
     * @author hukui
     * @time 2020/9/20
     * @Description 进货单列表
     */
    @GET("Order/GetBuyOrderListV2")
    Observable<JsonBean<OrderListBean>> getOrderBuyList(@Query("p.page") int page, @Query("p.type") int type, @Query("p.aID")
            int aID, @Query("p.begin") String begin, @Query("p.end") String end, @Query("p.text") String text, @Query("p.searchType") int searchType);

    /**
     * @param type       1:入库 2:报溢 其它:全部
     * @param aID        经手人ID
     * @param begin      开始时间 格式 yyyy-MM-dd
     * @param end        结束时间 格式 yyyy-MM-dd
     * @param text       入库单号搜索
     * @param searchType 0:全部 1:红冲 2:备注 3:有赊账
     * @return
     * @author hukui
     * @time 2020/9/21
     * @Description 出库单列表
     */
    @GET("Order/GetSellOrderListV2")
    Observable<JsonBean<OrderListBean>> getOrderSellList(@Query("p.page") int page, @Query("p.type") int type, @Query("p.aID")
            int aID, @Query("p.begin") String begin, @Query("p.end") String end, @Query("p.text") String text, @Query("p.searchType") int searchType);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/20
     * @Description 添加入库单
     */
    @POST("Order/OrderBuyAddV2")
    Observable<JsonBean<String>> orderBuyAdd(@Body OrderBody bean);

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/20
     * @Description 进货单详情
     */
    @GET("Order/GetBuyOrderInfoV2")
    Observable<JsonBean<OrderInfoBean>> getBuyOrderInfoV2(@Query("id") int id);

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/21
     * @Description 出库单详情
     */
    @GET("Order/GetSellOrderInfoV2")
    Observable<JsonBean<OrderInfoBean>> getOrderSellShow(@Query("id") int id);

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/9/21
     * @Description 添加出库单
     */
    @POST("Order/OrderSellAddV2")
    Observable<JsonBean<String>> srderSellAdd(@Body OrderBody bean);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/11/13
     * @Description 入库单红冲
     */
    @POST("Order/BuyOrderHCV2")
    @FormUrlEncoded
    Observable<JsonBean<Object>> orderBuyHong(@Field("ID") int id, @Field("Memo") String memo);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/11/13
     * @Description 出库单红冲
     */
    @POST("Order/SellOrderHCV2")
    @FormUrlEncoded
    Observable<JsonBean<Object>> orderSellHong(@Field("ID") int id, @Field("Memo") String memo);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/11/16
     * @Description 修改备注
     */
    @POST("Order/OrderModifyMemo")
    Observable<JsonBean<Object>> modifyMemo(@Query("Order_ID") int ID, @Query("Order_Memo") String memo);


    /**
     * @param type 1客户  2 供应商
     * @return
     * @author hukui
     * @time 2020/12/11
     * @Description 获取客户供应商列表
     */
    @GET("Order/GetChoiceUserV2")
    Observable<JsonBean<List<UserKeyBean>>> getChoiceUser(@Query("type") int type);


    /**
     * @param id 客户或供应商ID
     * @return
     * @author hukui
     * @time 2020/12/11
     * @Description 获取客户供应商列表
     */
    @GET("Order/GetBuyOrSellUserInfo")
    Observable<JsonBean<SellUserInfoBean>> getBuyOrSellUserInfo(@Query("id") int id);


    /**
     * @param id  产品ID
     * @param uid 客户ID
     * @return
     * @author hukui
     * @time 2020/12/14
     * @Description 获取某客户 购买某产品上次的价格
     */
    @GET("Order/GetSellOldPrice")
    Observable<JsonBean<String>> getBuyOrSellUserInfo(@Query("id") int id, @Query("uid") int uid);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/29
     * @Description 添加出入库单发票
     */
    @POST("Order/InvoiceAdd")
    @FormUrlEncoded
    Observable<JsonBean<Object>> invoiceAdd(@Field("OID") int oID, @Field("Pic") String pic, @Field("Money") String money, @Field("Memo") String memo);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/29
     * @Description 出票记录
     */

    @GET("Order/GetInvoiceList")
    Observable<JsonBean<InvoiceRecordBean>> getInvoiceList(@Query("oid") int oid);


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/29
     * @Description 作废此发票
     */
    @POST("Order/InvoiceDel")
    @FormUrlEncoded
    Observable<JsonBean<Object>> invoiceDel(@Field("ID") int ID, @Field("Memo") String memo);

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/29
     * @Description 停止出票
     */
    @PUT("Order/InvoiceStop")
    Observable<JsonBean<Object>> invoiceStop(@Query("oid") int ID);


    /************************* 出库/入库单 Order ******************************/


    /************************* 赊账/欠款 Debt ******************************/

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/16
     * @Description 客户/供应商 新增欠款/赊账
     */
    @POST("Debt/UserDebtAdd")
    @FormUrlEncoded
    Observable<JsonBean<Object>> userDebtAdd(@Field("ID") int id, @Field("Money") String money, @Field("Memo") String memo);


    /**
     * @param id    客户/供应商ID
     * @param state 0:欠款 1:结清 其它：全部
     * @param begin 格式 yyyy-MM-dd
     * @param end   格式 yyyy-MM-dd
     * @return
     * @author hukui
     * @time 2020/12/16
     * @Description 客户/供应商 欠款/赊账记录
     */
    @GET("Debt/GetUserDebtList")
    Observable<JsonBean<DebtBean>> getUserDebtList(@Query("page") int page, @Query("id") int id, @Query("state")
            int state, @Query("begin") String begin, @Query("end") String end);

    /**
     * @param type  1欠款 2赊账
     * @param state 0:欠款/赊账 1:结清 其它：全部
     * @return
     * @author hukui
     * @time 2020/12/16
     * @Description 欠款/赊账记录
     */
    @GET("Debt/GetDebtList")
    Observable<JsonBean<DebtManageBean>> getDebtList(@Query("page") int page, @Query("type") int type, @Query("state") int state);

    /**
     * @param id 欠款/赊账 id
     * @return
     * @author hukui
     * @time 2020/12/18
     * @Description 单笔欠款/赊账详情
     */
    @GET("Debt/GetDebtInfo")
    Observable<JsonBean<DebtDaetailBean>> getDebtInfo(@Query("id") int id);


    /**
     * @param ID    欠款/赊账 id
     * @param Money 欠款/赊账 还款金额
     * @return
     * @author hukui
     * @time 2020/12/18
     * @Description 单笔欠款/赊账还款
     */
    @POST("Debt/RepayAdd")
    @FormUrlEncoded
    Observable<JsonBean<Object>> repayAdd(@Field("ID") int ID, @Field("Money") String Money, @Field("Memo") String Memo);


    /**
     * @param ID    欠款/赊账 id 多个ID用‘,’隔开 ,
     * @param Money 欠款/赊账 还款金额
     * @return
     * @author hukui
     * @time 2020/12/18
     * @Description 多笔欠款/赊账还款
     */
    @POST("Debt/RepayMoreAdd")
    @FormUrlEncoded
    Observable<JsonBean<Object>> repayMoreAdd(@Field("ID") String ID, @Field("Money") String Money, @Field("Memo") String Memo);
    /************************* 赊账/欠款 Debt ******************************/

    /************************* 售后 Service ******************************/


    @GET("Service/GetServiceList")
    Observable<JsonBean<AfterServiceBean>> getServiceList(@Query("page") int page, @Query("uid") int uid, @Query("aid") int aid,
                                                          @Query("begin") String begin, @Query("end") String end, @Query("text") String text);


    /**
     * @param uid 客户ID
     * @return
     * @author hukui
     * @time 2020/12/23
     * @Description 获取客户可售后的产品列表
     */
    @GET("Service/GetUserServiceProduct")
    Observable<JsonBean<List<AfterServiceProductBean>>> getUserServiceProduct(@Query("id") int uid);

    /**
     * @return
     * @author hukui
     * @time 2020/12/23
     * @Description 添加售后
     */
    @POST("Service/ServiceAdd")
    Observable<JsonBean<Object>> serviceAdd(@Body AfterServiceBody body);

    /**
     * @param id
     * @return
     * @author hukui
     * @time 2020/12/23
     * @Description 售后详情
     */
    @GET("Service/GetServiceInfo")
    Observable<JsonBean<AfterServiceInfoBean>> getServiceInfo(@Query("id") int id);
    /************************* 赊账/欠款 Debt ******************************/

}
