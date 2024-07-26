package org.team.nagnebatch.place.batch.market.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.team.nagnebatch.place.batch.market.domain.CsvData;

import java.io.IOException;

@Configuration
public class RestaurantReader {

  private static final Logger log = LoggerFactory.getLogger(RestaurantReader.class);

  public FlatFileItemReader<CsvData> delegateReader() {
    return new FlatFileItemReaderBuilder<CsvData>()
            .name("csvDataItemReader")
            .delimited()
            .names("areatype", "index", "name", "address", "phone_number", "average_rating", "latitude", "longitude", "business_hours")
            .linesToSkip(1)
            .fieldSetMapper(new BeanWrapperFieldSetMapper<CsvData>() {{
              setTargetType(CsvData.class);
            }})
            .build();
  }

  public MultiResourceItemReader<CsvData> multiResourceItemReader(String resourcePattern) throws IOException {
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    Resource[] resources = resolver.getResources(resourcePattern);

    for (Resource resource : resources) {
      log.info("Reading CSV file: " + resource.getFilename());
    }

    MultiResourceItemReader<CsvData> resourceItemReader = new MultiResourceItemReader<>();
    resourceItemReader.setResources(resources);
    resourceItemReader.setDelegate(delegateReader());

    return resourceItemReader;
  }
}
