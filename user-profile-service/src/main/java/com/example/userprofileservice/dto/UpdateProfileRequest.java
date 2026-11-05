package com.example.userprofileservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Обновление профиля")
public record UpdateProfileRequest(
        @Schema(example = "Ivan") String firstName,
        @Schema(example = "Ivanov") String lastName,
        @Schema(example = "25") Integer age,
        @Schema(example = "Moscow") String address
) {}