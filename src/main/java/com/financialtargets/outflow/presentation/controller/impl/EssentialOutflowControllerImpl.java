package com.financialtargets.outflow.presentation.controller.impl;

import com.financialtargets.outflow.application.dto.essential.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.dto.essential.EssentialOutflowResponseDTO;
import com.financialtargets.outflow.application.dto.essential.EssentialOutflowUpdateDTO;
import com.financialtargets.outflow.application.usecase.essential.CreateEssentialOutflowUseCase;
import com.financialtargets.outflow.application.usecase.essential.ListingOutflowUseCase;
import com.financialtargets.outflow.application.usecase.essential.UpdateOutflowUseCase;
import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.presentation.controller.EssentialOutflowController;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/essential-outflow", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class EssentialOutflowControllerImpl implements EssentialOutflowController {
    private final CreateEssentialOutflowUseCase createEssentialOutflowUseCase;
    private final ListingOutflowUseCase listingOutflowUseCaseUseCase;
    private final UpdateOutflowUseCase updateOutflowUseCase;

    @PostMapping
    @Override
    public ResponseEntity<EssentialOutflowResponseDTO> create(@Valid @RequestBody EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws EssentialOutflowException {
        log.trace("POST /essential-outflow - Creating a new essential outflow for user {}", essentialOutflowCreateDTO.userId());
        log.debug("Request body: {}", essentialOutflowCreateDTO);

        EssentialOutflowResponseDTO essentialOutflow = createEssentialOutflowUseCase.create(essentialOutflowCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(essentialOutflow);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<EssentialOutflowResponseDTO>> listByMonth(@RequestParam @Valid @NonNull String month, @RequestParam @Valid @NonNull String year) throws Exception {
        log.trace("GET /essential-outflow - List essential outflows by month: {} and year: {}", month, year);

        List<EssentialOutflowResponseDTO> essentialOutflows = listingOutflowUseCaseUseCase.byPeriod(month, year);

        return ResponseEntity.status(HttpStatus.OK).body(essentialOutflows);
    }

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<EssentialOutflowResponseDTO> update(@PathVariable("id") String id, @Valid @RequestBody EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) throws EssentialOutflowException, ResourceNotFoundException {
        log.trace("PATCH /essential-outflow - Update a essential outflow for id: {}", id);
        log.debug("Request body: {}", essentialOutflowUpdateDTO);

        EssentialOutflowResponseDTO essentialOutflow = updateOutflowUseCase.update(Long.valueOf(id), essentialOutflowUpdateDTO);

        return ResponseEntity.status(HttpStatus.OK).body(essentialOutflow);
    }
}