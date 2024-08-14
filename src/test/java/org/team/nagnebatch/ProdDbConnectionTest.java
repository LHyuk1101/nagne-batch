package org.team.nagnebatch;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"classpath:application.yml", "classpath:application-key.yml"})
public class ProdDbConnectionTest {

  @Value("${database.name}")
  private String dbUrl;
  @Value("${database.username}")
  private String username;
  @Value("${database.ip}")
  private String ip;
  @Value("${database.password}")
  private String password;

  @Test
  public void prodDbConnectionTest() {
    // HikariCP 설정
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://" + ip + "/" + dbUrl + "?serverTimezone=UTC&characterEncoding=UTF-8");
    config.setUsername(username);
    config.setPassword(password);
    config.setMaximumPoolSize(10);
    config.setAutoCommit(false);
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

    try (HikariDataSource dataSource = new HikariDataSource(config)) {
      try (Connection conn = dataSource.getConnection()) {
        if (conn.isValid(5)) {
          System.out.println("데이터베이스 연결 성공!");
        } else {
          System.out.println("데이터베이스 연결 실패.");
        }
      } catch (SQLException e) {
        System.err.println("연결 중 오류 발생: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

}
