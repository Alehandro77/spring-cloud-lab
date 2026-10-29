package com.example.adminservice.client;

import com.example.adminservice.client.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "auth-service", contextId = "adminUserClient")
public interface UserClient {

    @GetMapping("/users")
    List<UserDto> getAllUsers();
}