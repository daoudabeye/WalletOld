package org.wallet.phone;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RoleController {

	private static final String ROLE_VIEW_NAME = "utilisateur/profile";
	private static final String CPARAM_VIEW_NAME = "utilisateur/commissionsParams";
	private static final String OPARAM_VIEW_NAME = "utilisateur/operationsParams";
	private static final String EDIT_VIEW_NAME = "utilisateur/editionProfile";
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	CommissionParamRepository cparamRepository;
	
	@Autowired
	OperationParamRepository oparamRepository;
	
	@RequestMapping(value = "/roles")
	public ModelAndView profileListe(){
		ModelAndView mav = new ModelAndView(ROLE_VIEW_NAME);
		List<Role> roles = roleRepository.findAll();
		
		mav.addObject("profiles", roles);
		return mav;
	}
	
	@RequestMapping(value = "/roles/edit")
	public ModelAndView profileEdit(){
		ModelAndView mav = new ModelAndView(EDIT_VIEW_NAME);
		mav.addObject("roleForm", new RoleForm());
		return mav;
	}
	
	@RequestMapping(value = "/roles/edit", method = RequestMethod.POST)
	public String profileEdit(@Validated @ModelAttribute RoleForm roleForm, 
			Errors errors, RedirectAttributes ra, Model model) throws Exception{
		
		return EDIT_VIEW_NAME;
	}
	
	@RequestMapping(value = "/commissionsParams/{roleId}")
	public ModelAndView cpramsListe(@PathVariable Integer roleId){
		ModelAndView mav = new ModelAndView(CPARAM_VIEW_NAME);
		List<CommissionParam> cparams = cparamRepository.findByIdRoleId(roleId);
				
		mav.addObject("cparams", cparams);
		return mav;
	}
	
	@RequestMapping(value = "/operationsParams/{roleId}")
	public ModelAndView opramsListe(@PathVariable Integer roleId){
		ModelAndView mav = new ModelAndView(OPARAM_VIEW_NAME);
		List<OperationParam> oparams = oparamRepository.findByRoleRoleId(roleId);
		
		mav.addObject("oparams", oparams);
		return mav;
	}
	
}
