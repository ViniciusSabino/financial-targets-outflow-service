package com.financialtargets.outflow.infrastructure.client;

import com.financialtargets.outflow.application.dto.IncomesSummaryResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "incomes", url = "http://localhost:8080/api")
public interface IncomesClient {
    @RequestMapping(method = RequestMethod.GET, value = "/summary/incomes")
    IncomesSummaryResponseDTO getIncomesSummary(@RequestParam String month, @RequestParam String year);
}
