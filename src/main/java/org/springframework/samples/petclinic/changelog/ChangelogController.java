package org.springframework.samples.petclinic.changelog;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChangelogController {

    @GetMapping({"/changelog"})
	  public String welcome(Map<String, Object> model) {	    

	    return "changelog/changelog";
	  }
}
