package com.idea.psychiatry.modules.encounter.service;


import com.idea.psychiatry.modules.encounter.entity.Encounter;
import com.idea.psychiatry.modules.encounter.enums.EncounterStatus;
import com.idea.psychiatry.modules.encounter.repository.EncounterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class EncounterService {

    private final EncounterRepository repository;

    public Encounter create(Encounter encounter) {

        encounter.setStatus(EncounterStatus.SCHEDULED);
        encounter.setEncounterDate(Instant.now());

        return repository.save(encounter);
    }
}
