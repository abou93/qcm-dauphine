package fr.dauphine.qcm.component.controller;

import static fr.dauphine.qcm.util.UserUtil.setUser;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.dauphine.qcm.component.service.IUserService;
import fr.dauphine.qcm.exception.FunctionalException;
import fr.dauphine.qcm.model.User;

@Controller
@RequestMapping(value = "/login.do")
public class LoginController {

	@Autowired
	private IUserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelMap displayLoginPage() {
		return new ModelMap("user", new User());
	}

	@RequestMapping(method = RequestMethod.POST)
	public String handleLoginForm(
			@Valid @ModelAttribute(value = "user") User user,
			BindingResult result, HttpSession session) {
		String view = "login";

		if (!result.hasErrors()) {
			try {
				setUser(session, userService.checkCredentials(user));
				view = "redirect:/";

			} catch (FunctionalException e) {
				result
						.addError(new FieldError("user", "login", e
								.getMessage()));
			}
		}

		return view;
	}
}
