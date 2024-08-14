package org.team.nagnebatch.place.client;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.team.nagnebatch.place.batch.service.ApiService;
import org.team.nagnebatch.place.exception.ApiResponseException;

@Component
public class TourApiConnectionDecorator implements ApiService<String> {

  private static final Logger log = LoggerFactory.getLogger(TourApiConnectionDecorator.class);

  @Autowired
  private List<TourApiConnection> apiConnections;

  private final AtomicInteger currentApiIndex = new AtomicInteger(0);

  @Value("#{'${tourapi.keys}'.split(',')}")
  private String[] apiKeys;

  private final AtomicInteger currentKeyIndex = new AtomicInteger(0);

  @Override
  public ResponseEntity<String> getConnectJsonData(URI requestUrl) {
    ApiResponseException lastException = null;

    for (int i = 0; i < apiConnections.size(); i++) {
      try {
        return apiConnections.get(getCurrentConnectionIndex()).getConnectJsonData(requestUrl);
      } catch (ApiResponseException e) {
        lastException = e;
        rotateApiConnection();
      }
    }

    throw lastException;
  }

  private void rotateApiConnection() {
    currentApiIndex.updateAndGet(current -> (current + 1) % apiConnections.size());
  }

  private int getCurrentConnectionIndex() {
    return currentApiIndex.get();
  }


  public String getApiKey() {
    if (apiKeys == null || apiKeys.length == 0) {
      throw new IllegalStateException("Api Keys Not loaded for properties files");
    }
    return apiKeys[getAndIncrementKeyIndex()];
  }

  private int getAndIncrementKeyIndex() {
    return currentKeyIndex.getAndUpdate(current -> (current + 1) % apiKeys.length);
  }
}
