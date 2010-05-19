package fr.dauphine.qcm.component.controller;

import static fr.dauphine.qcm.util.UserUtil.setUser;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.dauphine.qcm.component.service.IUserService;
import fr.dauphine.qcm.model.User;

@Controller
@RequestMapping("/user/{id}")
@SessionAttributes( { "user" })
public class UserController {

	@Autowired
	private IUserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String displayProfilePage(@PathVariable("id") Long id, ModelMap model) {
		model.put("user", userService.getById(id));

		return "user";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String handleEditForm(@ModelAttribute("user") User user,
			HttpSession session) {
		setUser(session, userService.updateAccount(user));
		return "redirect:/userlist";
	}
}
