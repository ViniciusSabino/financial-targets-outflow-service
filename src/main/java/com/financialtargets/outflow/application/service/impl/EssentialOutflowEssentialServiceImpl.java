package com.financialtargets.outflow.application.service.impl;

import com.financialtargets.outflow.application.dto.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.dto.EssentialOutflowUpdateDTO;
import com.financialtargets.outflow.application.service.EssentialOutflowService;
import com.financialtargets.outflow.application.utils.DateUtil;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import com.financialtargets.outflow.domain.exception.NotFoundException;
import com.financialtargets.outflow.domain.mapper.EssentialOutflowMapper;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.infrastructure.entity.EssentialOutflowEntity;
import com.financialtargets.outflow.infrastructure.repository.AccountRepository;
import com.financialtargets.outflow.infrastructure.repository.EssentialOutflowRepository;
import com.financialtargets.outflow.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EssentialOutflowEssentialServiceImpl implements EssentialOutflowService {
    private final EssentialOutflowRepository repository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<EssentialOutflow> listByMonth(String month, String year) {
        Instant start = DateUtil.getStartDateByFilter(month, year);
        Instant end = DateUtil.getEndDateByFilter(month, year);

        List<EssentialOutflowEntity> essentialOutflows = repository.findByDueDateBetween(start, end).stream().toList();

        return EssentialOutflowMapper.toModelList(essentialOutflows);
    }

    @Override
    public EssentialOutflow create(EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws EssentialOutflowException {
        if (!OutflowRecurrence.isValidRecurrence(essentialOutflowCreateDTO.recurrence())) {
            throw new EssentialOutflowException("Recorrencia inválida para a saída essencial");
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
    public EssentialOutflow update(Long id, EssentialOutflowUpdateDTO essentialOutflowUpdateDTO) throws EssentialOutflowException, NotFoundException {
        if (!Objects.isNull(essentialOutflowUpdateDTO.recurrence()) && !OutflowRecurrence.isValidRecurrence(essentialOutflowUpdateDTO.recurrence())) {
            throw new EssentialOutflowException("Recorrencia inválida para a saída essencial");
        }

        Optional<EssentialOutflowEntity> optionalExistingEntity = repository.findById(id);

        if (optionalExistingEntity.isEmpty()) {
            throw new NotFoundException("Saída essencial não encontrada");
        }

        EssentialOutflowEntity currentEntity = optionalExistingEntity.get();

        if (Objects.equals(currentEntity.getName(), essentialOutflowUpdateDTO.name()) && !Objects.equals(currentEntity.getId(), id)) {
            throw new EssentialOutflowException("Já existe uma saída essencial com esse nome");
        }

        EssentialOutflow essentialOutflowUpdate = new EssentialOutflow(essentialOutflowUpdateDTO);

        if (Objects.isNull(essentialOutflowUpdateDTO.paidValue())) {
            if (!Objects.isNull(essentialOutflowUpdateDTO.value())) {
                Float paidValue = currentEntity.getPaidValue() >= essentialOutflowUpdate.getValue() ? essentialOutflowUpdate.getValue() : currentEntity.getPaidValue();

                essentialOutflowUpdate.setPaidValue(paidValue);
                currentEntity.setPaidValue(paidValue);
            }
        } else {
            Float paidValue;

            if (Objects.isNull(essentialOutflowUpdateDTO.value())) {
                paidValue = essentialOutflowUpdate.getPaidValue() >= currentEntity.getValue() ? currentEntity.getValue() : essentialOutflowUpdate.getPaidValue();

            } else {
                paidValue = essentialOutflowUpdate.getPaidValue() >= essentialOutflowUpdate.getValue() ? essentialOutflowUpdateDTO.value() : essentialOutflowUpdateDTO.paidValue();
            }

            essentialOutflowUpdate.setPaidValue(paidValue);
            currentEntity.setPaidValue(paidValue);
        }

        if(!Objects.isNull(essentialOutflowUpdateDTO.accountId()))
            currentEntity.setAccount(accountRepository.getReferenceById(essentialOutflowUpdateDTO.accountId()));

        if (!Objects.isNull(essentialOutflowUpdate.getName())) currentEntity.setName(essentialOutflowUpdate.getName());
        if (!Objects.isNull(essentialOutflowUpdate.getValue())) currentEntity.setValue(essentialOutflowUpdate.getValue());
        if (!Objects.isNull(essentialOutflowUpdate.getDueDate())) currentEntity.setDueDate(essentialOutflowUpdate.getDueDate());
        if (!Objects.isNull(essentialOutflowUpdate.getNotes())) currentEntity.setNotes(essentialOutflowUpdate.getNotes());
        if (!Objects.isNull(essentialOutflowUpdate.getRecurrence())) currentEntity.setRecurrence(essentialOutflowUpdate.getRecurrence().name());

        return repository.save(currentEntity).toModel();
    }
}
