package com.example.dmtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DmtestApplication.class)
public class DmtestApplicationTests {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    public void contextLoads() {
        DataSource dataSource =
                jdbcTemplate.getDataSource();
        try {
            Connection connection = dataSource.getConnection();
            System.out.println(connection.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @Test
    public void test() {
        String sql = "select \"IP_ID\",\"ACCESS_PROVIDER_IP_ID\",\"IP_TYPE\",\"START_IP\",\"END_IP\",\"IPV4_START_IP_FIGURE\",\"IPV4_END_IP_FIGURE\",\"IPV6_START_IP_FIGURE\",\"IPV6_END_IP_FIGURE\",\"SERVICE_ID\",\"ACCESS_DATA_ID\",\"DATA_ZTBS\",\"SYS_NUM\",\"SCBBSJ\",\"LAST_MODIFY_TIME\",\"IS_DELETE\",\"CREATE_TIME\",\"UPDATE_TIME\",\"CREATOR_ID\",\"UPDATE_USER_ID\",\"DEL_FLAG\"\n" +
                "from \"ICP_BA_BM\".\"ICP_ACCESSIP_INFO_T\";";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        System.out.println(maps);
    }

    @Test
    public void testQueryObject() {
    }
}
