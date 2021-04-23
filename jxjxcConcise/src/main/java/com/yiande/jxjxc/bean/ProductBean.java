package com.yiande.jxjxc.bean;

import com.mylibrary.api.utils.StringUtil;
import com.yiande.jxjxc.utils.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/18 11:19
 */
public class ProductBean implements Serializable {


    private static final long serialVersionUID = -8584024359560613294L;
    /**
     * Product_ID : 2
     * Product_Class1_Name : 搅拌机
     * Product_Class2_Name : JS强制性
     * Product_Brand_Name : 大正
     * Product_Unit_Name : 个
     * Product_Title : 测试
     * Product_Model : 1
     * Product_No : null
     * Product_Pic : http://192.168.2.204/file/202009/23b51878716049738c2981ca57c7cbac.jpg
     * Product_Price : 0
     * Product_BuyPrice : 0
     * Product_InventoryNum : 0
     * Product_InventoryWarningNum : 0
     * Product_IsOK : 1
     * Product_Class1 : 0
     * Product_Class2 : 0
     * Product_Class3 : 0
     * Product_Class4 : 0
     * Product_Brand_ID : 0
     * Product_Unit_ID : 0
     * Product_SellPrice : 0
     * Product_Memo : string
     * Product_IsBuyPrice 0进货价显示为****,1正常显示
     */

    private String Product_Class1_Name;
    private String Product_Class2_Name;
    private String Product_Class3_Name;
    private String Product_Class4_Name;
    private String Product_Brand_Name;
    private String Product_Unit_Name;
    private String Product_Title;
    private String Product_Model;
    private String Product_No;
    private String Product_Pic;
    private String Product_Price;
    private String Product_Vip1_Price;
    private String Product_Vip2_Price;
    private String Product_Vip3_Price;
    private String Product_Vip4_Price;
    private String Product_BuyPrice;
    private String Product_InventoryNum;
    private String Product_InventoryWarningNum;
    private String Product_Memo;
    private String Product_Kg;
    private String Product_IsBuyPrice;
    private String Product_Amount;

    private int Product_ID;
    private int Product_IsOK;
    private int Product_Class1;
    private int Product_Class2;
    private int Product_Class3;
    private int Product_Class4;
    private int Product_Brand_ID;
    private int Product_Unit_ID;
    private int Product_SellPrice;
    /**
     * Product_No : null
     * Product_PicUrl : 202009/23b51878716049738c2981ca57c7cbac.jpg,202009/a9c1808f0e6e4bbfae14408e0b0a6016.jpg,202009/7532634cef804b03b3d99149726ab229.jpg,202009/035cbe58115844adb95b5474edee26b2.png
     * Product_Price : 0
     * Product_BuyPrice : 0
     * Product_InventoryNum : 0
     * Product_InventoryWarningNum : 0
     */

    private String Product_PicUrl;


    public int getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(int Product_ID) {
        this.Product_ID = Product_ID;
    }

    public String getProduct_Class1_Name() {
        return Product_Class1_Name;
    }

    public void setProduct_Class1_Name(String Product_Class1_Name) {
        this.Product_Class1_Name = Product_Class1_Name;
    }

    public String getProduct_Class2_Name() {
        return Product_Class2_Name;
    }

    public void setProduct_Class2_Name(String Product_Class2_Name) {
        this.Product_Class2_Name = Product_Class2_Name;
    }


    public String getProduct_Class3_Name() {
        return Product_Class3_Name;
    }

    public void setProduct_Class3_Name(String product_Class3_Name) {
        Product_Class3_Name = product_Class3_Name;
    }

    public String getProduct_Class4_Name() {
        return Product_Class4_Name;
    }

    public void setProduct_Class4_Name(String product_Class4_Name) {
        Product_Class4_Name = product_Class4_Name;
    }

    public String getProduct_Brand_Name() {
        return Product_Brand_Name;
    }

    public void setProduct_Brand_Name(String Product_Brand_Name) {
        this.Product_Brand_Name = Product_Brand_Name;
    }

    public String getProduct_Unit_Name() {
        return Product_Unit_Name;
    }

    public void setProduct_Unit_Name(String Product_Unit_Name) {
        this.Product_Unit_Name = Product_Unit_Name;
    }

    public String getProduct_Title() {
        return Product_Title;
    }

    public void setProduct_Title(String Product_Title) {
        this.Product_Title = Product_Title;
    }

    public String getProduct_Model() {
        return Product_Model;
    }

    public void setProduct_Model(String Product_Model) {
        this.Product_Model = Product_Model;
    }

    public String getProduct_No() {
        return Product_No;
    }

    public void setProduct_No(String Product_No) {
        this.Product_No = Product_No;
    }

    public String getProduct_Pic() {
        return Product_Pic;
    }

    public void setProduct_Pic(String Product_Pic) {
        this.Product_Pic = Product_Pic;
    }

    public String getProduct_Price() {
        return Product_Price;
    }

    public void setProduct_Price(String product_Price) {
        Product_Price = product_Price;
    }

    public String getProduct_BuyPrice() {
        return Product_BuyPrice;
    }

    public void setProduct_BuyPrice(String product_BuyPrice) {
        Product_BuyPrice = product_BuyPrice;
    }

    public String getProduct_InventoryNum() {
        return Product_InventoryNum;
    }

    public void setProduct_InventoryNum(String product_InventoryNum) {
        Product_InventoryNum = product_InventoryNum;
    }

    public String getProduct_InventoryWarningNum() {
        return Product_InventoryWarningNum;
    }

    public void setProduct_InventoryWarningNum(String product_InventoryWarningNum) {
        Product_InventoryWarningNum = product_InventoryWarningNum;
    }

    public int getProduct_IsOK() {
        return Product_IsOK;
    }

    public void setProduct_IsOK(int Product_IsOK) {
        this.Product_IsOK = Product_IsOK;
    }

    public int getProduct_Class1() {
        return Product_Class1;
    }

    public void setProduct_Class1(int Product_Class1) {
        this.Product_Class1 = Product_Class1;
    }

    public int getProduct_Class2() {
        return Product_Class2;
    }

    public void setProduct_Class2(int Product_Class2) {
        this.Product_Class2 = Product_Class2;
    }

    public int getProduct_Class3() {
        return Product_Class3;
    }

    public void setProduct_Class3(int Product_Class3) {
        this.Product_Class3 = Product_Class3;
    }

    public int getProduct_Class4() {
        return Product_Class4;
    }

    public void setProduct_Class4(int Product_Class4) {
        this.Product_Class4 = Product_Class4;
    }

    public int getProduct_Brand_ID() {
        return Product_Brand_ID;
    }

    public void setProduct_Brand_ID(int Product_Brand_ID) {
        this.Product_Brand_ID = Product_Brand_ID;
    }

    public int getProduct_Unit_ID() {
        return Product_Unit_ID;
    }

    public void setProduct_Unit_ID(int Product_Unit_ID) {
        this.Product_Unit_ID = Product_Unit_ID;
    }

    public int getProduct_SellPrice() {
        return Product_SellPrice;
    }

    public void setProduct_SellPrice(int Product_SellPrice) {
        this.Product_SellPrice = Product_SellPrice;
    }

    public String getProduct_Kg() {
        return Product_Kg;
    }

    public void setProduct_Kg(String product_Kg) {
        Product_Kg = product_Kg;
    }

    public String getProduct_Memo() {
        return Product_Memo;
    }

    public void setProduct_Memo(String Product_Memo) {
        this.Product_Memo = Product_Memo;
    }


    public void setClassID(List<String> idLsit) {
        setProduct_Class1(0);
        setProduct_Class2(0);
        setProduct_Class3(0);
        setProduct_Class4(0);
        if (Util.isNotListEmpty(idLsit)) {
            for (int i = 0; i < idLsit.size(); i++) {

                switch (i) {
                    case 0:
                        setProduct_Class1(StringUtil.toInt(idLsit.get(i)));
                        break;
                    case 1:
                        setProduct_Class2(StringUtil.toInt(idLsit.get(i)));
                        break;
                    case 2:
                        setProduct_Class3(StringUtil.toInt(idLsit.get(i)));
                        break;
                    case 3:
                        setProduct_Class4(StringUtil.toInt(idLsit.get(i)));
                        break;
                    default:
                        break;
                }
            }

        }
    }

    public List<String> getClassID() {
        List<String> list = new ArrayList<>();
        if (getProduct_Class1() > 0) {
            list.add(String.valueOf(getProduct_Class1()));
        }
        if (getProduct_Class2() > 0) {
            list.add(String.valueOf(getProduct_Class2()));
        }
        if (getProduct_Class3() > 0) {
            list.add(String.valueOf(getProduct_Class3()));
        }
        if (getProduct_Class4() > 0) {
            list.add(String.valueOf(getProduct_Class4()));
        }
        return list;
    }


    public String getClassName() {
        String name = "";

        if (StringUtil.isNotEmpty(getProduct_Class1_Name())) {
            name += getProduct_Class1_Name();
        }
        if (StringUtil.isNotEmpty(getProduct_Class2_Name())) {
            name += " / " + getProduct_Class2_Name();
        }

        if (StringUtil.isNotEmpty(getProduct_Class3_Name())) {
            name += " / " + getProduct_Class3_Name();
        }

        if (StringUtil.isNotEmpty(getProduct_Class4_Name())) {
            name += " / " + getProduct_Class4_Name();
        }

        return name;
    }

    public void setCLassName(List<String> names) {
        if (Util.isNotListEmpty(names)) {
            for (int i = 0; i < names.size(); i++) {
                switch (i) {
                    case 0:
                        setProduct_Class1_Name(names.get(i));
                        break;
                    case 1:
                        setProduct_Class2_Name(names.get(i));
                        break;
                    case 2:
                        setProduct_Class3_Name(names.get(i));
                        break;
                    case 3:
                        setProduct_Class4_Name(names.get(i));
                        break;

                    default:
                        break;
                }
            }
        }
    }

    public String getProduct_PicUrl() {
        return Product_PicUrl;
    }

    public void setProduct_PicUrl(String Product_PicUrl) {
        this.Product_PicUrl = Product_PicUrl;
    }

    public String getProduct_IsBuyPrice() {
        return Product_IsBuyPrice;
    }

    public void setProduct_IsBuyPrice(String product_IsBuyPrice) {
        Product_IsBuyPrice = product_IsBuyPrice;
    }

    public String getProduct_Vip1_Price() {
        return Product_Vip1_Price;
    }

    public void setProduct_Vip1_Price(String product_Vip1_Price) {
        Product_Vip1_Price = product_Vip1_Price;
    }

    public String getProduct_Vip2_Price() {
        return Product_Vip2_Price;
    }

    public void setProduct_Vip2_Price(String product_Vip2_Price) {
        Product_Vip2_Price = product_Vip2_Price;
    }

    public String getProduct_Vip3_Price() {
        return Product_Vip3_Price;
    }

    public void setProduct_Vip3_Price(String product_Vip3_Price) {
        Product_Vip3_Price = product_Vip3_Price;
    }

    public String getProduct_Vip4_Price() {
        return Product_Vip4_Price;
    }

    public void setProduct_Vip4_Price(String product_Vip4_Price) {
        Product_Vip4_Price = product_Vip4_Price;
    }

    public String getProduct_Amount() {
        return Product_Amount;
    }

    public void setProduct_Amount(String product_Amount) {
        Product_Amount = product_Amount;
    }
}
