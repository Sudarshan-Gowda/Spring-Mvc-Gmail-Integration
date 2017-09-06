package com.star.sud.web;
/*@ Sudarshan*/
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.star.sud.gmail.GmailService;
import com.star.sud.gmail.GmailUser;

@Controller
public class LoginController {

	@Resource(name = "gmailService")
	protected GmailService gmailService;

	final static String JSON_REG_DATA = "JSON_REG_DATA";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginPage(Model model) {

		String gUrl = gmailService.buildLoginUrl();
		model.addAttribute("gUrl", gUrl);
		return "login/login-page";

	}

	public Object fromJson(String json) throws JsonParseException, IOException {
		GmailUser guser = new ObjectMapper().readValue(json, GmailUser.class);
		return guser;
	}

	@RequestMapping(value = "/google/result{state}{code}", method = RequestMethod.GET)
	public String getResultFromgoogle(Model model, ServletRequest request, HttpServletResponse response,
			HttpSession session, @PathVariable("state") String state, @PathVariable("code") String code,
			RedirectAttributes redirectAttributes) {

		model.addAttribute("request", request);
		String parameter = request.getParameter("code");

		if (request.getParameter("code") != null && request.getParameter("state") != null) {

			session.setAttribute("code", parameter);

			String userInfoJson = gmailService.getUserInfoJson(request.getParameter("code"));

			if (userInfoJson != null)
				session.setAttribute(JSON_REG_DATA, userInfoJson);

			GmailUser guser;

			try {

				if (userInfoJson != null)
					guser = (GmailUser) fromJson(userInfoJson);
				else {
					String userData = (String) session.getAttribute(JSON_REG_DATA);
					guser = (GmailUser) fromJson(userData);
				}

				String firstName = guser.getGiven_name();
				String lastName = guser.getFamily_name();
				String eMail = guser.getEmail();
				String gender = guser.getGender();
				System.out.println(firstName + lastName + eMail + gender);

				model.addAttribute("gmailData", guser);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "welcome/welcome-page";

	}

}
