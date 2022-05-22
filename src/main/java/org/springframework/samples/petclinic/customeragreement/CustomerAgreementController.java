package org.springframework.samples.petclinic.customeragreement;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// /customeragreement/show

@Controller
public class CustomerAgreementController {

    private static final String VIEWS_CUSTOMER_AGREEMENT_SHOHW = "customeragreement/show";

    @GetMapping(value = "/customeragreement/show")
    public String showPage(Map<String, Object> model) {
        return VIEWS_CUSTOMER_AGREEMENT_SHOHW;
    }
}
