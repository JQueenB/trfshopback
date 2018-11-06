package com.jqueenb.service.impl;

import com.google.common.collect.Interners;
import com.google.common.collect.Lists;
import com.jqueenb.common.Const;
import com.jqueenb.common.ServerResponse;
import com.jqueenb.dao.CartMapper;
import com.jqueenb.dao.ProductMapper;
import com.jqueenb.pojo.Cart;
import com.jqueenb.pojo.Product;
import com.jqueenb.service.ICartService;
import com.jqueenb.utils.BigDecimalUtils;
import com.jqueenb.vo.CartProductVo;
import com.jqueenb.vo.CartVo;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;
    @Override
    public ServerResponse add(Integer userId,Integer productId, Integer count) {
        //step1:参数非空校验
        if(productId==null||count==null){
            return ServerResponse.createByError("参数不能为空");
        }
        Product product=productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.createByError("要添加的商品不存在");
        }
        //step2:根据productId和userId查询购物信息
        Cart cart=cartMapper.selectCartByUseridAndProductId(userId,productId);
        if(cart==null){
            //添加
            Cart cart1=new Cart();
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            cart1.setQuantity(count);
            cart1.setChecked(Const.CartCheckedEnum.PRODUCT_CHECKED.getCode());
            cartMapper.insert(cart1);
        }else {
            //更新数量
            Cart cart1=new Cart();
            cart1.setId(cart.getId());
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            cart1.setQuantity(cart.getQuantity()+count);
            cart1.setChecked(cart.getChecked());
            cartMapper.updateByPrimaryKey(cart1);
        }
        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo=new CartVo();
        //step1:根据userid查询购物信息--list<cart>
        List<Cart> cartList = cartMapper.selectCartByUserid(userId);
        //step2:list<cart>--》list<carproductvo>
        List<CartProductVo> cartProductVoList= Lists.newArrayList();
        //购物车总价格
        BigDecimal carttotalprice=new BigDecimal("0");
        if(cartList!=null&&cartList.size()>0){
            for(Cart cart:cartList){
                CartProductVo cartProductVo=new CartProductVo();
                cartProductVo.setId(cart.getId());
                cartProductVo.setQuantity(cart.getQuantity());
                cartProductVo.setUserId(cart.getUserId());
                cartProductVo.setProductChecked(cart.getChecked());
                //查询商品
                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
                if(product!=null){
                    cartProductVo.setProductId(cart.getProductId());
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductStock(product.getStock());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    int stock=product.getStock();
                    int limitProductCount=0;
                    if(stock>=cart.getQuantity()){
                        limitProductCount=cart.getQuantity();
                        cartProductVo.setLimitQuantity("LIMIT_NUM_SUCCESS");
                    }else {
                        //商品不足
                        limitProductCount=stock;
                        //更新购物车中的商品数量
                        Cart cart1=new Cart();
                        cart1.setId(cart.getId());
                        cart1.setQuantity(cart.getQuantity());
                        cart1.setProductId(cart.getProductId());
                        cart1.setChecked(cart.getChecked());
                        cart1.setUserId(userId);
                        cartMapper.updateByPrimaryKey(cart1);
                        cartProductVo.setLimitQuantity("LIMIT_NUM_FAIL");
                    }
                    cartProductVo.setQuantity(limitProductCount);
                    cartProductVo.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(),Double.valueOf(cartProductVo.getQuantity()) ));
                }
                if(cartProductVo.getProductChecked()==Const.CartCheckedEnum.PRODUCT_CHECKED.getCode()){
                    carttotalprice=BigDecimalUtils.add(carttotalprice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());

                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartProductVoList(cartProductVoList);
        //step3:计算总价格
        cartVo.setCarttotalprice(carttotalprice);
        //step4:判断购物车是否全选
        int count=cartMapper.isCheckedAll(userId);
        if (count>0){
            cartVo.setIsallchecked(false);
        }else {
            cartVo.setIsallchecked(true);
        }

        //step5:返回结果
        return cartVo;
    }
    @Override
    public ServerResponse list(Integer userId) {
        CartVo cartVo=getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse update(Integer userId, Integer productId, Integer count) {
        //step1:参数判定
        if(productId==null||count==null){
            return ServerResponse.createByError("参数不能为空");
        }
        //step2:查询购物车中商品
        Cart cart=cartMapper.selectCartByUseridAndProductId(userId,productId);
        if(cart!=null){
            //step3:更新数量
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }
        //step4:返回catvo
        return ServerResponse.createBySuccess(getCartVoLimit(userId));
    }

    @Override
    public ServerResponse delete_product(Integer userId, String productIds) {
        //step1: 参数非空校验
        if(productIds==null||productIds.equals("")){
            return ServerResponse.createByError("参数不能为空");
        }
        //step2:productids-->list<integer>
        List<Integer> productIdList=Lists.newArrayList();
        String[] productIdsArr=productIds.split(",");
        if(productIdsArr!=null&&productIdsArr.length>0){
            for(String productIdstr:productIdsArr){
                Integer productId=Integer.parseInt(productIdstr);
                productIdList.add(productId);
            }
        }
        //step3:调用dao
        cartMapper.deleteByUseridAndProductIds(userId,productIdList);
        //step4:返回结果
        return ServerResponse.createBySuccess(getCartVoLimit(userId));
    }

    @Override
    public ServerResponse select(Integer userId, Integer productId,Integer check) {
        //step1: 参数非空校验
        /*if(productId==null){
            return ServerResponse.createByError("参数不能为空");
        }*/
        //step2:调用dao
        cartMapper.selectOrUnselectProduct(userId,productId,check);
        //step3:返回结果
        return ServerResponse.createBySuccess(getCartVoLimit(userId));
    }

    @Override
    public ServerResponse get_cart_product_count(Integer userId) {
        int quantity=cartMapper.get_cart_product_count(userId);
        return ServerResponse.createBySuccess(quantity);
    }
}
