package com.financialtargets.outflow.presentation.controller.impl;

import com.financialtargets.outflow.application.dto.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowUpdateDTO;
import com.financialtargets.outflow.application.service.EssentialOutflowService;
import com.financialtargets.outflow.domain.exception.BusinessException;
import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.domain.mapper.EssentialOutflowMapper;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
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
    private final EssentialOutflowService service;

    @GetMapping
    @Override
    public ResponseEntity<List<EssentialOutflowDTO>> listByMonth(@RequestParam @Valid @NonNull String month, @RequestParam @NonNull @Valid String year) throws Exception {
        log.trace("GET /essential-outflow - List essential outflows by month: {} and year: {}", month, year);

        List<EssentialOutflow> essentialsOutflow = service.listByMonth(Integer.parseInt(month), Integer.parseInt(year));

        return ResponseEntity.status(HttpStatus.OK).body(EssentialOutflowMapper.toDTOList(essentialsOutflow));
    }

    @PostMapping
    @Override
    public ResponseEntity<EssentialOutflowDTO> create(@Valid @RequestBody EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws BusinessException {
        log.trace("POST /essential-outflow - Creating a new essential outflow for user {}", essentialOutflowCreateDTO.userId());
        log.debug("Request body: {}", essentialOutflowCreateDTO);

        EssentialOutflow essentialOutflow = service.create(essentialOutflowCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(EssentialOutflowMapper.toDTO(essentialOutflow));
    }

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<EssentialOutflowDTO> update(@PathVariable("id") String id, @Valid @RequestBody EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) throws BusinessException, ResourceNotFoundException {
        log.trace("PATCH /essential-outflow - Update a essential outflow for id: {}", id);
        log.debug("Request body: {}", essentialOutflowUpdateDTO);

        EssentialOutflow essentialOutflow = service.update(Long.valueOf(id), essentialOutflowUpdateDTO);

        return ResponseEntity.status(HttpStatus.OK).body(EssentialOutflowMapper.toDTO(essentialOutflow));
    }
}