package com.isa.nbpapi_spring.controller;

import com.isa.nbpapi_spring.service.ApiNbpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class NbpApiController {

    private final ApiNbpService apiNbpService;

    public NbpApiController(ApiNbpService apiNbpService) {
        this.apiNbpService = apiNbpService;
    }

    @GetMapping("/exchange-rates")
    public String getExchangeRates(Model model) {
        List<Map<String, Object>> ratesList = apiNbpService.getExchangeRates();
        model.addAttribute("exchangeRates", ratesList);
        return "exchange-rates";
    }
}
