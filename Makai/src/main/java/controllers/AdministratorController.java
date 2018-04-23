/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BannerService;
import services.NotificationService;
import domain.Actor;
import domain.Banner;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//Related services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private BannerService		bannerService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// ListActors --------------------------------------------------------------------
	@RequestMapping(value = "/listActors", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Actor> actors;
		final Integer numberNoti;
		try {
			actors = this.actorService.findAllActorsWithoutAdmin();
			numberNoti = this.notificationService.findNotificationWithoutRead();

			final ArrayList<Banner> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			final ArrayList<Banner> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("administrator/listActors");
			result.addObject("requestURI", "administrator/listActors.do");
			result.addObject("numberNoti", numberNoti);
			result.addObject("actors", actors);

			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			result.addObject("imagesRight", imagesRight);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	// Ban --------------------------------------------------------------------		

	@RequestMapping(value = "/ban", method = RequestMethod.POST, params = "ban")
	public ModelAndView ban(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor;
		try {
			actor = this.actorService.findOne(actorId);

			this.actorService.ban(actor);

			result = new ModelAndView("redirect:listActors.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
		}
		return result;
	}

	//	// Unban ------------------------------------------------------------------		

	@RequestMapping(value = "/unban", method = RequestMethod.POST, params = "unban")
	public ModelAndView unban(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor;
		try {
			actor = this.actorService.findOne(actorId);

			this.actorService.unban(actor);

			result = new ModelAndView("redirect:listActors.do");
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

}
