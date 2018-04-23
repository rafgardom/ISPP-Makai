/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import services.NotificationService;
import domain.Banner;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}


	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private BannerService		bannerService;


	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false) final String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		final Integer numberNoti;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		final ArrayList<Banner> imagesLeft = this.bannerService.getBannerByZone("izquierda");
		final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
		final ArrayList<Banner> imagesRight = this.bannerService.getBannerByZone("derecha");

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);

		result.addObject("imagesLeft", imagesLeft);
		result.addObject("imagesBottom", imagesBottom);
		result.addObject("imagesRight", imagesRight);
		try {
			numberNoti = this.notificationService.findNotificationWithoutRead();
			result.addObject("numberNoti", numberNoti);
		} catch (final Exception e) {

		}

		return result;
	}
}
