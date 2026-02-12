package com.financialtargets.outflow.application.usecase.essential;

import com.financialtargets.outflow.application.dto.essential.EssentialOutflowResponseDTO;
import com.financialtargets.outflow.application.mapper.EssentialOutflowMapper;
import com.financialtargets.outflow.domain.model.DateFilter;
import com.financialtargets.outflow.domain.model.EssentialOutflow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingOutflowUseCase implements ListingOutflowUseCase {
    private final EssentialOutflowService service;

    @Override
    public List<EssentialOutflowResponseDTO> byPeriod(String month, String year) throws Exception {
        DateFilter filter = new DateFilter(month, year);

        List<EssentialOutflow> incomes = service.listByDate(filter);

        return EssentialOutflowMapper.toResponseList(incomes);
    }
}