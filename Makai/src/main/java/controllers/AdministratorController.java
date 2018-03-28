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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.NotificationService;
import domain.Actor;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//Related services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private NotificationService	notificationService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-1");

		return result;
	}

	// ListActors --------------------------------------------------------------------
	@RequestMapping(value = "/listActors", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Actor> actors;
		final Integer numberNoti;

		actors = this.actorService.findAll();
		numberNoti = this.notificationService.findNotificationWithoutRead();

		result = new ModelAndView("administrator/listActors");
		result.addObject("requestURI", "administrator/listActors.do");
		result.addObject("numberNoti", numberNoti);
		result.addObject("actors", actors);

		return result;
	}

	// Ban --------------------------------------------------------------------		

	@RequestMapping(value = "/ban", method = RequestMethod.POST, params = "ban")
	public ModelAndView ban(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor;

		actor = this.actorService.findOne(actorId);

		this.actorService.ban(actor);

		result = new ModelAndView("redirect:listActors.do");

		return result;
	}

	//	// Unban ------------------------------------------------------------------		

	@RequestMapping(value = "/unban", method = RequestMethod.POST, params = "unban")
	public ModelAndView unban(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor;

		actor = this.actorService.findOne(actorId);

		this.actorService.unban(actor);

		result = new ModelAndView("redirect:listActors.do");

		return result;
	}

	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-2");

		return result;
	}

}
