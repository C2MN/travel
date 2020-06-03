package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteDaoImpl implements RouteDao {

    //使用Spring的template
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid) {
        //1.定义sql
        String sql = "select count(*) from tab_route where cid = ?";
        //2.执行sql语句
        Integer integer = template.queryForObject(sql, Integer.class, cid);
        return integer;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize) {
        //1.定义sql语句
        String sql = "select * from tab_route where cid = ? limit ?,?";
        //            select * from tab_route where cid = ? limit ?,?
        //2.执行sql语句
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cid, start, pageSize);
        return query;
    }

    @Override
    public Route findOne(int rid) {
        //1.定义sql语句
        String sql = "select * from tab_route where rid = ?";
        //2.执行sql
        Route route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        return route;
    }
}
