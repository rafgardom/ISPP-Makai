
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

import services.VehicleService;
import domain.Vehicle;
import forms.VehicleForm;

@Controller
@RequestMapping("/vehicle")
public class VehicleController extends AbstractController {

	//Related services
	@Autowired
	private VehicleService	vehicleService;


	//Constructor
	public VehicleController() {
		super();
	}

	//Register
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		final VehicleForm vehicleForm = new VehicleForm();
		result = this.createModelAndView(vehicleForm);

		return result;
	}

	//Save register
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid final VehicleForm vehicleForm, final BindingResult binding) throws IOException {
		ModelAndView result;
		final Vehicle vehicle;
		boolean pictureTooLong = false;
		vehicle = this.vehicleService.reconstruct(vehicleForm, binding);
		if (binding.hasErrors())
			result = this.createModelAndView(vehicleForm, "vehicle.register.error");
		else
			try {
				if (vehicleForm.getUserImage().getSize() > 5242880 || !vehicleForm.getUserImage().getContentType().contains("image")) {
					pictureTooLong = true;
					throw new IllegalArgumentException();
				}
				this.vehicleService.save(vehicle);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				if (pictureTooLong == false)
					result = this.createModelAndView(vehicleForm, "vehicleForm.register.error");
				else
					result = this.createModelAndView(vehicleForm, "vehicleForm.register.picture.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView edit(@RequestParam final int vehicleId) {
		ModelAndView result;
		Vehicle vehicle;

		vehicle = this.vehicleService.findOne(vehicleId);
		try {
			this.vehicleService.delete(vehicle);
			result = new ModelAndView("redirect:register.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int vehicleId) {
		ModelAndView result;
		Vehicle vehicle;

		vehicle = this.vehicleService.findOne(vehicleId);
		try {
			this.vehicleService.delete(vehicle);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Vehicle> vehicles;
		vehicles = this.vehicleService.findAll();
		result = new ModelAndView("vehicle/list");
		result.addObject("vehicles", vehicles);
		result.addObject("requestURI", "vehicle/list.do");

		return result;
	}

	// Ancillary methods

	protected ModelAndView createModelAndView(final VehicleForm vehicleForm) {
		final ModelAndView result = this.createModelAndView(vehicleForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final VehicleForm vehicleForm, final String message) {
		final ModelAndView result = new ModelAndView("vehicle/register");
		result.addObject("vehicleForm", vehicleForm);
		result.addObject("RequestURI", "vehicle/register.do");
		result.addObject("errorMessage", message);

		return result;
	}

}
