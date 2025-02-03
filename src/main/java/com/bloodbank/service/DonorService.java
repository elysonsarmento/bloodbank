package com.bloodbank.service;

import com.bloodbank.dto.DonorDTO;
import com.bloodbank.model.Donor;
import com.bloodbank.repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonorService {

    private final DonorRepository donorRepository;

    @Transactional
    public void saveAllDonors(List<Donor> donors) {
        donorRepository.saveAll(donors);
    }

    public DonorDTO saveDonor(Donor donor) {
        final Donor donorResult = donorRepository.save(donor);

        return new DonorDTO(
                donorResult.getId(),
                donorResult.getName(),
                donorResult.getAge(),
                donorResult.getWeight(),
                donorResult.getHeight(),
                donorResult.getBloodType().toString(),
                donorResult.getGender(),
                donorResult.getState()
        );
    }

    public Page<DonorDTO> getAllDonors(Pageable pageable) {
        return donorRepository.findAll(pageable)
                .map(donor -> new DonorDTO(
                        donor.getId(),
                        donor.getName(),
                        donor.getAge(),
                        donor.getWeight(),
                        donor.getHeight(),
                        donor.getBloodType().toString(),
                        donor.getGender(),
                        donor.getState()
                ));
    }


}