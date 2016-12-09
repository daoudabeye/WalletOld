package org.wallet.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wallet.operation.Operation;
import org.wallet.operation.OperationService;

@Controller
@RequestMapping("/ws-cgi/")
public class RestController {

	@Autowired
	OperationService opertionService;
	
	@RequestMapping(value = "depot/{numeroAgent}/{codeAgent}/{numeroClient}/{montant}", method = RequestMethod.POST)
	public String depot( @PathVariable String numeroAgent, @PathVariable String numeroClient, @PathVariable String montant){
		
		try {
			@SuppressWarnings("unused")
			Operation op = opertionService.doOperation("DEPOT", numeroClient, numeroAgent, 
					Double.valueOf(montant), "-1441", "", false, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
