package dev.jam.accountservice.service.dtos;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "LOCATION-SERVICE")
public interface LocationService {
    @GetMapping("/api/v1/")
    String getDataFromOtherService();

}