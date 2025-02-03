package com.bloodbank.dto;

public record DonorDTO(Long id, String name, Integer age, Double weight, Double height, String bloodType, String gender,
                       String state) {
}
