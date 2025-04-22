package com.cooba.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cooba.constant.Status;
import com.cooba.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Update("UPDATE orders SET status = #{status} WHERE order_no = #{orderNo}")
    void updateStatusByOrderNo(@Param("orderNo") String orderNo, @Param("status") Status status);

    @Select("SELECT * FROM orders WHERE order_no = #{orderNo}")
    Order getOrderByOrderNo(String orderNo);

    @Select("SELECT * FROM orders WHERE round = #{round}")
    List<Order> getOrdersByRound(int round);
}
