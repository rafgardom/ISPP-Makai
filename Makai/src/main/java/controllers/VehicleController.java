
package controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TransporterService;
import services.VehicleService;
import domain.Brand;
import domain.CarType;
import domain.Transporter;
import domain.Vehicle;
import forms.VehicleForm;

@Controller
@RequestMapping("/vehicle")
public class VehicleController extends AbstractController {

	//Related services
	@Autowired
	private VehicleService		vehicleService;

	@Autowired
	private TransporterService	transporterService;


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
		Vehicle vehicle;
		byte[] savedFile;

		vehicle = this.vehicleService.reconstruct(vehicleForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createModelAndView(vehicleForm);

		} else
			try {

				if (vehicleForm.getUserImage().getSize() > 0) {

					savedFile = vehicleForm.getUserImage().getBytes();
					vehicle.setPicture(savedFile);
				}

				vehicle = this.vehicleService.save(vehicle);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				System.out.println(oops);

				result = this.createModelAndView(vehicleForm, "vehicle.commit.error");

			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int vehicleId) {
		ModelAndView result;
		final Vehicle vehicle;
		VehicleForm vehicleForm;

		vehicle = this.vehicleService.findOne(vehicleId);
		vehicleForm = this.vehicleService.toFormObject(vehicle);

		result = this.createModelAndView(vehicleForm);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final VehicleForm vehicleForm, final BindingResult binding) throws IOException {

		ModelAndView result;
		Vehicle vehicle;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createModelAndView(vehicleForm);

		} else
			try {

				vehicle = this.vehicleService.reconstruct(vehicleForm, binding);
				vehicle = this.vehicleService.save(vehicle);
				result = new ModelAndView("master.page");

			} catch (final Throwable oops) {
				System.out.println(oops);

				result = this.createModelAndView(vehicleForm, "travel.commit.error");

			}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Vehicle vehicle, final BindingResult binding) {

		ModelAndView result;

		this.vehicleService.delete(vehicle);

		result = new ModelAndView("redirect:list.do");

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
		Collection<VehicleForm> vehicles;
		Transporter principal;

		principal = this.transporterService.findByPrincipal();
		vehicles = new HashSet<VehicleForm>();

		for (final Vehicle a : this.vehicleService.findVehicleByTransporterId(principal.getId())) {
			VehicleForm vehicleForm;

			vehicleForm = this.vehicleService.toFormObject(a);

			vehicles.add(vehicleForm);
		}

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
		Brand[] brands;
		brands = Brand.values();
		CarType[] carTypes;
		carTypes = CarType.values();

		final ModelAndView result = new ModelAndView("vehicle/register");
		result.addObject("vehicleForm", vehicleForm);
		result.addObject("RequestURI", "vehicle/register.do");
		result.addObject("errorMessage", message);
		result.addObject("brands", brands);
		result.addObject("carTypes", carTypes);

		return result;
	}

}
