package com.financialtargets.outflow.infrastructure.repository.impl;

import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import com.financialtargets.outflow.domain.model.DateFilter;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import com.financialtargets.outflow.domain.repository.EssentialOutflowRepository;
import com.financialtargets.outflow.infrastructure.entity.EssentialOutflowEntity;
import com.financialtargets.outflow.infrastructure.mapper.EssentialOutflowMapper;
import com.financialtargets.outflow.infrastructure.repository.AccountsJpaRepository;
import com.financialtargets.outflow.infrastructure.repository.EssentialOutflowJpaRepository;
import com.financialtargets.outflow.infrastructure.repository.UsersJpaRepository;
import com.financialtargets.outflow.infrastructure.repository.specification.EssentialOutflowSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class EssentialOutflowRepositoryImpl implements EssentialOutflowRepository {
    private final EssentialOutflowJpaRepository repository;
    private final UsersJpaRepository usersJpaRepository;
    private final AccountsJpaRepository accountsJpaRepository;
    private final EssentialOutflowMapper mapper;


    @Override
    public EssentialOutflow save(EssentialOutflow outflow) {
        EssentialOutflowEntity entity = mapper.toEntity(outflow);

        entity.setUser(usersJpaRepository.getReferenceById(outflow.getUserId()));
        entity.setAccount(accountsJpaRepository.getReferenceById(outflow.getAccountId()));

        EssentialOutflowEntity saved = repository.save(entity);

        return mapper.toModel(saved);
    }

    @Override
    public EssentialOutflow findByName(String name) {
        EssentialOutflowEntity entity = repository.findByName(name);

        return mapper.toModel(entity);
    }

    @Override
    public EssentialOutflow findById(Long id) throws ResourceNotFoundException {
        EssentialOutflowEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Essential outflow not found"));

        return mapper.toModel(entity);
    }

    @Override
    public List<EssentialOutflow> listByDate(DateFilter dateFilter) {
        List<EssentialOutflowEntity> outflows = repository.findAll(EssentialOutflowSpecification.byFilter(dateFilter)).stream().toList();

        return outflows.stream().map(mapper::toModel).toList();
    }

    @Override
    public EssentialOutflow update(EssentialOutflow outflow) throws ResourceNotFoundException {
        EssentialOutflowEntity update = mapper.toEntity(outflow);

        EssentialOutflowEntity current = this.repository.getReferenceById(outflow.getId());

        update.setUser(!Objects.isNull(outflow.getUserId()) ? this.usersJpaRepository.getReferenceById(outflow.getUserId()) : current.getUser());
        update.setAccount(!Objects.isNull(outflow.getAccountId()) ? this.accountsJpaRepository.getReferenceById(outflow.getAccountId()) : current.getAccount());
        
        update.setId(outflow.getId());

        return mapper.toModel(repository.save(update));
    }
}