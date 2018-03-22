
package controllers;

import java.io.IOException;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TransporterService;
import services.TravelService;
import services.VehicleService;
import domain.Transporter;
import domain.Travel;
import domain.Vehicle;
import forms.TravelForm;

@Controller
@RequestMapping("/travel")
public class TravelController extends AbstractController {

	//Related services
	@Autowired
	private TravelService		travelService;

	@Autowired
	private VehicleService		vehicleService;

	@Autowired
	private TransporterService	transporterService;


	//Constructor
	public TravelController() {
		super();
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final TravelForm travelForm = new TravelForm();
		result = this.createModelAndView(travelForm);

		return result;
	}

	//Save create
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid final TravelForm travelForm, final BindingResult binding) throws IOException {
		ModelAndView result;
		Travel travel;
		if (binding.hasErrors())
			result = this.createModelAndView(travelForm);
		else
			try {
				travel = this.travelService.reconstruct(travelForm, binding);
				this.travelService.save(travel);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {

				result = this.createModelAndView(travelForm, "travel.commit.error");

			}
		return result;
	}

	//Register
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam final int travelId) {
		final ModelAndView result;
		final Travel travel = this.travelService.findOne(travelId);
		this.travelService.registerTravel(travel);
		result = new ModelAndView("redirect:list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int travelId) {
		ModelAndView result;
		Travel travel;
		TravelForm travelForm;

		travel = this.travelService.findOne(travelId);
		travelForm = this.travelService.toFormObject(travel);

		result = this.createModelAndView(travelForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final TravelForm travelForm, final BindingResult binding) throws IOException {

		ModelAndView result;
		Travel travel;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createModelAndView(travelForm);

		} else
			try {

				travel = this.travelService.reconstruct(travelForm, binding);
				travel = this.travelService.save(travel);
				result = new ModelAndView("master.page");

			} catch (final Throwable oops) {
				System.out.println(oops);

				result = this.createModelAndView(travelForm, "travel.commit.error");

			}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Travel travel, final BindingResult binding) {

		ModelAndView result;

		this.travelService.delete(travel);

		result = new ModelAndView("redirect:myList.do");

		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int travelId) {
		ModelAndView result;
		Travel travel;

		travel = this.travelService.findOne(travelId);
		try {
			this.travelService.delete(travel);
			result = new ModelAndView("redirect:myList.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:myList.do");
		}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Travel> travels;
		travels = this.travelService.findAll();
		result = new ModelAndView("travel/list");
		result.addObject("travels", travels);
		result.addObject("requestURI", "travel/list.do");

		return result;
	}

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView myList() {
		ModelAndView result;
		Collection<Travel> travels;
		Transporter principal;
		principal = this.transporterService.findByPrincipal();
		travels = this.travelService.findTravelByTransporterId(principal.getId());
		result = new ModelAndView("travel/myList");
		result.addObject("travels", travels);
		result.addObject("requestURI", "travel/myList.do");

		return result;
	}

	// Display -------------------------------------------------------		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int travelId) {
		ModelAndView result;
		Travel travel;

		travel = this.travelService.findOne(travelId);

		result = new ModelAndView("travel/display");
		result.addObject("travel", travel);
		result.addObject("requestURI", "travel/display.do?travelId=" + travel.getId());

		return result;
	}
	// Ancillary methods

	protected ModelAndView createModelAndView(final TravelForm travelForm) {
		final ModelAndView result = this.createModelAndView(travelForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final TravelForm travelForm, final String message) {
		Collection<Vehicle> vehicles;
		Transporter principal;

		principal = this.transporterService.findByPrincipal();
		vehicles = this.vehicleService.findVehicleByTransporterId(principal.getId());

		final ModelAndView result = new ModelAndView("travel/create");
		result.addObject("travelForm", travelForm);
		result.addObject("vehicles", vehicles);
		result.addObject("RequestURI", "travel/create.do");
		result.addObject("errorMessage", message);

		return result;
	}

}
