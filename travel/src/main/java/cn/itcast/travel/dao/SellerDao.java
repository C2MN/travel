package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {

    /**
     * 根据rid查询
     * @param rid
     * @return
     */
    public Seller findById(int rid);

}
