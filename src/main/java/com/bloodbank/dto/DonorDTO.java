package com.bloodbank.dto;

import java.util.UUID;

public record DonorDTO(UUID id, String name, Integer age, Double weight, Double height, String bloodType, String gender,
                       String state) {
}
