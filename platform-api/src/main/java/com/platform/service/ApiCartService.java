package com.platform.service;

import com.platform.dao.ApiCartMapper;
import com.platform.entity.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ApiCartService {
    @Autowired
    private ApiCartMapper cartDao;

    public CartVo queryObject(Integer id) {
        return cartDao.queryObject(id);
    }


    public List<CartVo> queryList(Map<String, Object> map) {
        return cartDao.queryList(map);
    }


    public int queryTotal(Map<String, Object> map) {
        return cartDao.queryTotal(map);
    }


    public void save(CartVo cart) {
        cartDao.save(cart);
        // 更新购物车搭配减价
        // 判断购物车中是否存在此规格商品
        Map<String, Object> cartParam = new HashMap<>();
        cartParam.put("userId", cart.getUserId());
        List<CartVo> cartInfoList = cartDao.queryList(cartParam);
        Map<String, Object> crashParam = new HashMap<>();
        List<Integer> goodsIds = new ArrayList<>();
        List<CartVo> cartUpdateList = new ArrayList<>();
        for (CartVo cartItem : cartInfoList) {
            if (null != cartItem.getChecked() && 1 == cartItem.getChecked()) {
                goodsIds.add(cartItem.getGoodsId());
            }
            if (!cartItem.getRetailPrice().equals(cartItem.getRetailProductPrice())) {
                cartItem.setRetailPrice(cartItem.getRetailProductPrice());
                cartUpdateList.add(cartItem);
            }
        }
        crashParam.put("goodsIds", goodsIds);
        for (CartVo cartItem : cartInfoList) {
            // 存在原始的
            if (null != cartItem.getChecked() && 1 == cartItem.getChecked()) {
                for (CartVo cartCrash : cartInfoList) {
                    if (!cartCrash.getId().equals(cartItem.getId())) {
                        cartUpdateList.add(cartItem);
                    }
                }
            }
        }
        if (null != cartUpdateList && cartUpdateList.size() > 0) {
            for (CartVo cartItem : cartUpdateList) {
                cartDao.update(cartItem);
            }
        }
    }

    public void update(CartVo cart) {
        cartDao.update(cart);
    }


    public void delete(Integer id) {
        cartDao.delete(id);
    }


    public void deleteBatch(Integer[] ids) {
        cartDao.deleteBatch(ids);
    }

    public void updateCheck(String[] productIds, Integer isChecked, Long userId) {
        cartDao.updateCheck(productIds, isChecked, userId);

        // 判断购物车中是否存在此规格商品
        Map<String, Object> cartParam = new HashMap<>();
        cartParam.put("userId", userId);
        List<CartVo> cartInfoList = cartDao.queryList(cartParam);
        Map<String, Object> crashParam = new HashMap<>();
        List<Integer> goodsIds = new ArrayList<>();
        List<CartVo> cartUpdateList = new ArrayList<>();
        for (CartVo cartItem : cartInfoList) {
            if (null != cartItem.getChecked() && 1 == cartItem.getChecked()) {
                goodsIds.add(cartItem.getGoodsId());
            }
            if (!cartItem.getRetailPrice().equals(cartItem.getRetailProductPrice())) {
                cartItem.setRetailPrice(cartItem.getRetailProductPrice());
                cartUpdateList.add(cartItem);
            }
        }
        if (null != goodsIds && goodsIds.size() > 0) {
            crashParam.put("goodsIds", goodsIds);
            for (CartVo cartItem : cartInfoList) {
                // 存在原始的
                if (null != cartItem.getChecked() && 1 == cartItem.getChecked()) {
                    for (CartVo cartCrash : cartInfoList) {
                        if (null != cartItem.getChecked() && 1 == cartItem.getChecked() && !cartCrash.getId().equals(cartItem.getId())) {
                            cartUpdateList.add(cartCrash);
                            break;
                        }
                    }
                }
            }
        }
        if (null != cartUpdateList && cartUpdateList.size() > 0) {
            for (CartVo cartItem : cartUpdateList) {
                cartDao.update(cartItem);
            }
        }
    }

    public void deleteByProductIds(String[] productIds) {
        cartDao.deleteByProductIds(productIds);
    }

    public void deleteByUserAndProductIds(Long userId, String[] productIds) {
        cartDao.deleteByUserAndProductIds(userId, productIds);
    }

    public void deleteByCart(Long userId, Integer sessionId, Integer checked) {
        cartDao.deleteByCart(userId, sessionId, checked);
    }

}
