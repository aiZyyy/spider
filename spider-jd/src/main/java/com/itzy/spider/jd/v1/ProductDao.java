package com.itzy.spider.jd.v1;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Author: ZY
 * @Date: 2019/7/30 10:36
 * @Version 1.0
 */

/**
 *
 * 将数据保存到数据库 // 首先有一个数据库 表也建设OK
 * 通过程序将数据写入到数据库-------jdbc\mybatis\springjdbctemplate|DataSource C3P0 DRUID
 *
 */
public class ProductDao extends JdbcTemplate {

    public ProductDao() {
        // 创建C3P0的datasource 1.配置 2.代码
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        // 1.url
        // 2.driver
        // 3.username&password
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setJdbcUrl("jdbc:mysql://node03:3306/spider?characterEncoding=utf-8");
        setDataSource(dataSource);
    }

    public void saveProduct(Product producet) {
        String sql = "INSERT INTO `spider`.`jd_product`(`id`, `url`, `name`, `price`) VALUES (?, ?, ?, ?);";
        update(sql, producet.getId(), producet.getUrl(), producet.getName(), producet.getPrice());
    }

}