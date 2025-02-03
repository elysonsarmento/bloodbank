package com.bloodbank.api.v1.controller;

import com.bloodbank.dto.DonorDTO;
import com.bloodbank.model.Donor;
import com.bloodbank.service.DonorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class DonorController {

    private final DonorService donorService;

    @PostMapping(path = "/donors")
    public ResponseEntity<Void> createDonors(@Valid @RequestBody List<Donor> donors) {
        donorService.saveAllDonors(donors);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/donor")
    public ResponseEntity<DonorDTO> createDonor(@Valid @RequestBody Donor donor) {
        final DonorDTO result = donorService.saveDonor(donor);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping(path = "/donor")
    public ResponseEntity<Page<DonorDTO>> getAllDonors(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(donorService.getAllDonors(pageable));
    }

}