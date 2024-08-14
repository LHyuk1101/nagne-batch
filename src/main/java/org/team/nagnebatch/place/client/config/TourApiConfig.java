package org.team.nagnebatch.place.client.config;

import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.team.nagnebatch.place.client.TourApiConnection;

@Configuration
public class TourApiConfig {

  @Bean
  public List<TourApiConnection> tourApiConnections() {
    return Collections.singletonList(new TourApiConnection());
  }

}

