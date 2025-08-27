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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EssentialOutflowEssentialServiceImpl implements EssentialOutflowService {
    private static final Logger log = LoggerFactory.getLogger(EssentialOutflowService.class);

    private final EssentialOutflowRepository repository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<EssentialOutflow> listByMonth(String month, String year) {
        log.debug("Listando saídas essenciais para o mês = {} e ano = {}", month, year);

        Instant start = DateUtil.getStartDateByFilter(month, year);
        Instant end = DateUtil.getEndDateByFilter(month, year);

        List<EssentialOutflowEntity> essentialOutflows = repository.findByDueDateBetween(start, end).stream().toList();

        return EssentialOutflowMapper.toModelList(essentialOutflows);
    }

    @Override
    public EssentialOutflow create(EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws BusinessException {
        if (!OutflowRecurrence.isValidRecurrence(essentialOutflowCreateDTO.recurrence())) {
            throw new BusinessException("Recorrencia inválida para a saída essencial");
        }

        EssentialOutflowEntity existingEntity = repository.findByName(essentialOutflowCreateDTO.name());

        if (!Objects.isNull(existingEntity)) return existingEntity.toModel();

        EssentialOutflow essentialOutflow = new EssentialOutflow(essentialOutflowCreateDTO);

        if (essentialOutflowCreateDTO.paidValue() >= essentialOutflowCreateDTO.value()) {
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

        return repository.save(entity).toModel();
    }

    @Override
    public EssentialOutflow update(Long id, EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) throws BusinessException, ResourceNotFoundException {
        if (!Objects.isNull(essentialOutflowUpdateDTO.recurrence()) && !OutflowRecurrence.isValidRecurrence(essentialOutflowUpdateDTO.recurrence())) {
            throw new BusinessException("Recorrencia inválida para a saída essencial");
        }

        EssentialOutflowEntity currentOutflow = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Saída essencial não encontrada"));

        if (Objects.equals(currentOutflow.getName(), essentialOutflowUpdateDTO.name()) && !Objects.equals(currentOutflow.getId(), id)) {
            throw new BusinessException("Já existe uma saída essencial com esse nome");
        }

        EssentialOutflow essentialOutflowUpdate = new EssentialOutflow(essentialOutflowUpdateDTO);

        if (Objects.isNull(essentialOutflowUpdateDTO.paidValue())) {
            if (!Objects.isNull(essentialOutflowUpdateDTO.value())) {
                Float paidValue = currentOutflow.getPaidValue() >= essentialOutflowUpdate.getValue() ? essentialOutflowUpdate.getValue() : currentOutflow.getPaidValue();

                essentialOutflowUpdate.setPaidValue(paidValue);
                currentOutflow.setPaidValue(paidValue);
            }
        } else {
            Float paidValue;

            if (Objects.isNull(essentialOutflowUpdateDTO.value())) {
                paidValue = essentialOutflowUpdate.getPaidValue() >= currentOutflow.getValue() ? currentOutflow.getValue() : essentialOutflowUpdate.getPaidValue();

            } else {
                paidValue = essentialOutflowUpdate.getPaidValue() >= essentialOutflowUpdate.getValue() ? essentialOutflowUpdateDTO.value() : essentialOutflowUpdateDTO.paidValue();
            }

            essentialOutflowUpdate.setPaidValue(paidValue);
            currentOutflow.setPaidValue(paidValue);
        }

        if (!Objects.isNull(essentialOutflowUpdateDTO.accountId()))
            currentOutflow.setAccount(accountRepository.getReferenceById(essentialOutflowUpdateDTO.accountId()));

        if (!Objects.isNull(essentialOutflowUpdate.getName())) currentOutflow.setName(essentialOutflowUpdate.getName());
        if (!Objects.isNull(essentialOutflowUpdate.getValue()))
            currentOutflow.setValue(essentialOutflowUpdate.getValue());
        if (!Objects.isNull(essentialOutflowUpdate.getDueDate()))
            currentOutflow.setDueDate(essentialOutflowUpdate.getDueDate());
        if (!Objects.isNull(essentialOutflowUpdate.getNotes()))
            currentOutflow.setNotes(essentialOutflowUpdate.getNotes());
        if (!Objects.isNull(essentialOutflowUpdate.getRecurrence()))
            currentOutflow.setRecurrence(essentialOutflowUpdate.getRecurrence().name());

        return repository.save(currentOutflow).toModel();
    }
}
