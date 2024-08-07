package org.team.nagnebatch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.team.nagnebatch.place.client.TourApiConnectionDecorator;

@SpringBootTest
public class KeyChangeTest {

  @Autowired
  private TourApiConnectionDecorator tourApiConnectionDecorator;

  @Test
  @DisplayName("API 키가 제대로 로테이션 도는지 확인한다.")
  public void rotateTest(){
    String key1 = tourApiConnectionDecorator.getApiKey();
    String key2 = tourApiConnectionDecorator.getApiKey();
    String key3 = tourApiConnectionDecorator.getApiKey();
    String key4 = tourApiConnectionDecorator.getApiKey();
    String key5 = tourApiConnectionDecorator.getApiKey();
    String key6 = tourApiConnectionDecorator.getApiKey();

    Assertions.assertThat(key1).isNotEqualTo(key2);
    Assertions.assertThat(key2).isNotEqualTo(key3);
    Assertions.assertThat(key3).isNotEqualTo(key4);
    Assertions.assertThat(key4).isEqualTo(key1);
    Assertions.assertThat(key5).isEqualTo(key2);
    Assertions.assertThat(key6).isEqualTo(key3);
  }
}
