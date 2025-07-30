package com.financialtargets.outflow.presentation.controller.impl;

import com.financialtargets.outflow.application.dto.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowUpdateDTO;
import com.financialtargets.outflow.application.service.EssentialOutflowService;
import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import com.financialtargets.outflow.domain.mapper.EssentialOutflowMapper;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.presentation.controller.EssentialOutflowController;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/essential-outflow")
@RequiredArgsConstructor
public class EssentialOutflowControllerImpl implements EssentialOutflowController {
    private final EssentialOutflowService service;

    @GetMapping
    @Override
    public ResponseEntity<List<EssentialOutflowDTO>> listByMonth(@RequestParam @Valid @NonNull String month, @RequestParam @NonNull @Valid String year) {
        List<EssentialOutflow> essentialsOutflow = service.listByMonth(month, year);

        return ResponseEntity.status(HttpStatus.OK).body(EssentialOutflowMapper.toDTOList(essentialsOutflow));
    }

    @PostMapping
    @Override
    public ResponseEntity<EssentialOutflowDTO> create(@Valid @RequestBody EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws EssentialOutflowException {
        EssentialOutflow essentialOutflow = service.create(essentialOutflowCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(EssentialOutflowMapper.toDTO(essentialOutflow));
    }

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<EssentialOutflowDTO> update(@PathVariable("id") String id, @Valid @RequestBody EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) throws EssentialOutflowException {
        EssentialOutflow essentialOutflow = service.update(Long.valueOf(id), essentialOutflowUpdateDTO);

        return ResponseEntity.status(HttpStatus.OK).body(EssentialOutflowMapper.toDTO(essentialOutflow));
    }
}