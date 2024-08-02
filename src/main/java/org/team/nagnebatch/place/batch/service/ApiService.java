package org.team.nagnebatch.place.batch.service;

import java.net.URI;
import org.springframework.http.ResponseEntity;

public interface ApiService<T> {

  ResponseEntity<T> getConnectJsonData(URI requestUrl);

}
