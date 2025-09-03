package com.financialtargets.outflow.application.service.impl;

import com.financialtargets.outflow.application.dto.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowUpdateDTO;
import com.financialtargets.outflow.application.service.EssentialOutflowService;
import com.financialtargets.outflow.application.utils.DateUtil;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.exception.BusinessException;
import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.domain.mapper.EssentialOutflowMapper;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.infrastructure.entity.EssentialOutflowEntity;
import com.financialtargets.outflow.infrastructure.repository.AccountRepository;
import com.financialtargets.outflow.infrastructure.repository.EssentialOutflowRepository;
import com.financialtargets.outflow.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EssentialOutflowEssentialServiceImpl implements EssentialOutflowService {

    private final EssentialOutflowRepository repository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<EssentialOutflow> listByMonth(Integer month, Integer year) throws Exception {
        Instant start = DateUtil.getStartDateByFilter(month, year);
        Instant end = DateUtil.getEndDateByFilter(month, year);

        log.info("Listing essential outflows for the period {} to {}", start, end);

        List<EssentialOutflowEntity> essentialOutflows = repository.findByDueDateBetween(start, end).stream().toList();

        log.info("Listed {} essential outflows successfully", essentialOutflows.stream().toList().size());

        return EssentialOutflowMapper.toModelList(essentialOutflows);
    }

    @Override
    public EssentialOutflow create(EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws BusinessException {
        if (!OutflowRecurrence.isValidRecurrence(essentialOutflowCreateDTO.recurrence())) {
            throw new BusinessException("Invalid recurrence for create a new essential outflow");
        }

        EssentialOutflowEntity existingEntity = repository.findByName(essentialOutflowCreateDTO.name());

        if (!Objects.isNull(existingEntity)) return existingEntity.toModel();

        EssentialOutflow essentialOutflow = new EssentialOutflow(essentialOutflowCreateDTO);

        if (essentialOutflowCreateDTO.paidValue().compareTo(essentialOutflowCreateDTO.value()) >= 0) {
            essentialOutflow.setPaidValue(essentialOutflowCreateDTO.value());
        }

        EssentialOutflowEntity entity = new EssentialOutflowEntity();

        entity.setUser(userRepository.getReferenceById(essentialOutflowCreateDTO.userId()));
        entity.setAccount(accountRepository.getReferenceById(essentialOutflowCreateDTO.accountId()));

        entity.setName(essentialOutflow.getName());
        entity.setDueDate(essentialOutflow.getDueDate());
        entity.setValue(essentialOutflow.getValue());
        entity.setPaidValue(essentialOutflow.getPaidValue());
        entity.setNotes(essentialOutflow.getNotes());
        entity.setRecurrence(essentialOutflow.getRecurrence().name());
        entity.setCreatedAt(essentialOutflow.getCreatedAt());
        entity.setUpdatedAt(essentialOutflow.getUpdatedAt());

        EssentialOutflow savedOutflow = repository.save(entity).toModel();

        log.info("Outflow created successfully, id: {}", savedOutflow.getId());

        return savedOutflow;
    }

    @Override
    public EssentialOutflow update(Long id, EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) throws BusinessException, ResourceNotFoundException {
        if (!Objects.isNull(essentialOutflowUpdateDTO.recurrence()) && !OutflowRecurrence.isValidRecurrence(essentialOutflowUpdateDTO.recurrence())) {
            throw new BusinessException("Invalid recurrence for create a new essential outflow");
        }

        EssentialOutflowEntity currentOutflow = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Essential outflow not found"));

        if (Objects.equals(currentOutflow.getName(), essentialOutflowUpdateDTO.name()) && !Objects.equals(currentOutflow.getId(), id)) {
            throw new BusinessException("There is already an essential exit with that name");
        }

        EssentialOutflow essentialOutflowUpdate = new EssentialOutflow(essentialOutflowUpdateDTO);

        if (Objects.isNull(essentialOutflowUpdateDTO.paidValue())) {
            if (!Objects.isNull(essentialOutflowUpdateDTO.value())) {
                BigDecimal paidValue = currentOutflow.getPaidValue().compareTo(essentialOutflowUpdate.getValue()) >= 0  ? essentialOutflowUpdate.getValue() : currentOutflow.getPaidValue();

                essentialOutflowUpdate.setPaidValue(paidValue);
                currentOutflow.setPaidValue(paidValue);
            }
        } else {
            BigDecimal paidValue;

            if (Objects.isNull(essentialOutflowUpdateDTO.value())) {
                paidValue = essentialOutflowUpdate.getPaidValue().compareTo(currentOutflow.getValue()) >= 0 ? currentOutflow.getValue() : essentialOutflowUpdate.getPaidValue();

            } else {
                paidValue = essentialOutflowUpdate.getPaidValue().compareTo(essentialOutflowUpdate.getValue()) >= 0 ? essentialOutflowUpdateDTO.value() : essentialOutflowUpdateDTO.paidValue();
            }

            essentialOutflowUpdate.setPaidValue(paidValue);
            currentOutflow.setPaidValue(paidValue);
        }

        if (!Objects.isNull(essentialOutflowUpdateDTO.accountId()))
            currentOutflow.setAccount(accountRepository.getReferenceById(essentialOutflowUpdateDTO.accountId()));

        if (!Objects.isNull(essentialOutflowUpdate.getName())) currentOutflow.setName(essentialOutflowUpdate.getName());
        if (!Objects.isNull(essentialOutflowUpdate.getValue())) currentOutflow.setValue(essentialOutflowUpdate.getValue());
        if (!Objects.isNull(essentialOutflowUpdate.getDueDate())) currentOutflow.setDueDate(essentialOutflowUpdate.getDueDate());
        if (!Objects.isNull(essentialOutflowUpdate.getNotes())) currentOutflow.setNotes(essentialOutflowUpdate.getNotes());
        if (!Objects.isNull(essentialOutflowUpdate.getRecurrence())) currentOutflow.setRecurrence(essentialOutflowUpdate.getRecurrence().name());

        EssentialOutflow updatedOutflow = repository.save(currentOutflow).toModel();

        log.info("Outflow updated successfully, id: {}", updatedOutflow.getId());

        return repository.save(currentOutflow).toModel();
    }
}
