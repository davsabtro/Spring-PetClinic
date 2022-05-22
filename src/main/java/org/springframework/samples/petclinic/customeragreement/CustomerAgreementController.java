package org.springframework.samples.petclinic.customeragreement;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerAgreementController {

    private static final String VIEWS_CUSTOMER_AGREEMENT_SHOW_ALL = "customeragreement/showAll";
    private static final String VIEWS_CUSTOMER_AGREEMENT_SHOW_FREE =
            "customeragreement/showFreePlan";
    private static final String VIEWS_CUSTOMER_AGREEMENT_SHOW_ADVANCED =
            "customeragreement/showPaidPlans";
    private static final String VIEWS_CUSTOMER_AGREEMENT_SHOW_PRO =
            "customeragreement/showPaidPlans";

    @GetMapping(value = "/customeragreement/showAll")
    public String showAllPlans(Map<String, Object> model) {
        return VIEWS_CUSTOMER_AGREEMENT_SHOW_ALL;
    }

    @GetMapping(value = "/customeragreement/showBasic")
    public String showPageBasic(Map<String, Object> model) {
        return VIEWS_CUSTOMER_AGREEMENT_SHOW_FREE;
    }

    @GetMapping(value = "/customeragreement/showAdvanced")
    public String showPageAdvanced(Map<String, Object> model) {
        return VIEWS_CUSTOMER_AGREEMENT_SHOW_ADVANCED;
    }

    @GetMapping(value = "/customeragreement/showPro")
    public String showPagePro(Map<String, Object> model) {
        return VIEWS_CUSTOMER_AGREEMENT_SHOW_PRO;
    }
}
