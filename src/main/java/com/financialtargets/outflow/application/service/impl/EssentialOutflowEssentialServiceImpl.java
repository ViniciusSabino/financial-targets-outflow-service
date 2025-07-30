package com.financialtargets.outflow.application.service.impl;

import com.financialtargets.outflow.application.dto.EssentialOutflowCreateDTO;
import com.financialtargets.outflow.application.service.EssentialOutflowService;
import com.financialtargets.outflow.domain.enums.OutflowRecurrence;
import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.infrastructure.entity.AccountEntity;
import com.financialtargets.outflow.infrastructure.entity.EssentialOutflowEntity;
import com.financialtargets.outflow.infrastructure.entity.UsersEntity;
import com.financialtargets.outflow.infrastructure.repository.AccountRepository;
import com.financialtargets.outflow.infrastructure.repository.EssentialOutflowRepository;
import com.financialtargets.outflow.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EssentialOutflowEssentialServiceImpl implements EssentialOutflowService {
    private final EssentialOutflowRepository repository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    public EssentialOutflow create(EssentialOutflowCreateDTO essentialOutflowCreateDTO) throws EssentialOutflowException {
        if (!OutflowRecurrence.isValidRecurrence(essentialOutflowCreateDTO.recurrence())) {
            throw new EssentialOutflowException("Recorrencia inválida para a saída essencial", HttpStatus.BAD_REQUEST);
        }

        EssentialOutflowEntity existingEntity = repository.findByName(essentialOutflowCreateDTO.name());

        if (!Objects.isNull(existingEntity)) return existingEntity.toModel();

        EssentialOutflow essentialOutflow = new EssentialOutflow(essentialOutflowCreateDTO);

        if (essentialOutflowCreateDTO.paidValue() > essentialOutflowCreateDTO.value()) {
            essentialOutflow.setPaidValue(essentialOutflowCreateDTO.value());
            essentialOutflow.setFullyPaid(true);
        }

        UsersEntity userEntity = userRepository.getReferenceById(essentialOutflowCreateDTO.userId());
        AccountEntity accountEntity = accountRepository.getReferenceById(essentialOutflowCreateDTO.accountId());

        EssentialOutflowEntity entity = new EssentialOutflowEntity();

        entity.setUser(userEntity);
        entity.setAccount(accountEntity);

        entity.setName(essentialOutflow.getName());
        entity.setDueDate(essentialOutflow.getDueDate());
        entity.setValue(essentialOutflow.getValue());
        entity.setPaidValue(essentialOutflow.getPaidValue());
        entity.setIsFullyPaid(essentialOutflow.isFullyPaid());
        entity.setNotes(essentialOutflow.getNotes());
        entity.setRecurrence(essentialOutflow.getRecurrence().name());
        entity.setCreatedAt(essentialOutflow.getCreatedAt());
        entity.setUpdatedAt(essentialOutflow.getUpdatedAt());

        return repository.save(entity).toModel();
    }
}
