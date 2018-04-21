
package controllers;

import java.io.IOException;
import java.util.Calendar;
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

import services.NotificationService;
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

	@Autowired
	private NotificationService	notificationService;


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

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRegister(@Valid final VehicleForm vehicleForm, final BindingResult binding) throws IOException {

		ModelAndView result;
		Vehicle vehicle;
		Calendar today;
		boolean yearError = false;
		boolean pictureTooLong = false;

		today = Calendar.getInstance();

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createModelAndView(vehicleForm);

		} else
			try {
				vehicle = this.vehicleService.reconstruct(vehicleForm, binding);
				if (today.get(Calendar.YEAR) <= vehicle.getYear()) {
					yearError = true;
					throw new IllegalArgumentException();
				}
				if (vehicleForm.getUserImage().getSize() > 2097152 || !vehicleForm.getUserImage().getContentType().contains("image")) {
					pictureTooLong = true;
					throw new IllegalArgumentException();
				}
				vehicle = this.vehicleService.save(vehicle);
				result = new ModelAndView("redirect:/vehicle/list.do");

			} catch (final Throwable oops) {
				if (yearError = true)
					result = this.createModelAndView(vehicleForm, "vehicle.year.error");
				if (pictureTooLong == false)
					result = this.createModelAndView(vehicleForm, "advertising.register.error");
				else
					result = this.createModelAndView(vehicleForm, "vehicle.commit.error");
			}
		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int vehicleId) {
		ModelAndView result;
		final Vehicle vehicle;
		VehicleForm vehicleForm;
		try {
			vehicle = this.vehicleService.findOne(vehicleId);
			vehicleForm = this.vehicleService.toFormObject(vehicle);

			result = this.createModelAndView(vehicleForm);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final VehicleForm vehicleForm, final BindingResult binding) throws IOException {

		ModelAndView result;
		Vehicle vehicle;
		boolean pictureTooLong = false;
		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createModelAndView(vehicleForm);

		} else
			try {

				if (vehicleForm.getUserImage().getSize() > 2097152 || !vehicleForm.getUserImage().getContentType().contains("image")) {
					pictureTooLong = true;
					throw new IllegalArgumentException();
				}

				vehicle = this.vehicleService.reconstruct(vehicleForm, binding);
				vehicle = this.vehicleService.save(vehicle);
				result = new ModelAndView("redirect:/vehicle/list.do");

			} catch (final Throwable oops) {
				System.out.println(oops);
				if (pictureTooLong == false)
					result = this.createModelAndView(vehicleForm, "advertising.register.error");
				else
					result = this.createModelAndView(vehicleForm, "vehicle.commit.error");

			}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Vehicle vehicle, final BindingResult binding) {

		ModelAndView result;
		try {
			this.vehicleService.delete(vehicle);

			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable e) {
			result = new ModelAndView("error");
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
			result = new ModelAndView("error");
		}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<VehicleForm> vehicles;
		Transporter principal;
		final Integer numberNoti;

		principal = this.transporterService.findByPrincipal();
		vehicles = new HashSet<VehicleForm>();
		numberNoti = this.notificationService.findNotificationWithoutRead();
		try {
			for (final Vehicle a : this.vehicleService.findActivatedVehicles(principal)) {
				VehicleForm vehicleForm;

				vehicleForm = this.vehicleService.toFormObject(a);

				vehicles.add(vehicleForm);
			}

			result = new ModelAndView("vehicle/list");
			result.addObject("vehicles", vehicles);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "vehicle/list.do");
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
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
		final Integer numberNoti;
		numberNoti = this.notificationService.findNotificationWithoutRead();

		ModelAndView result;

		if (vehicleForm.getId() == 0) {
			result = new ModelAndView("vehicle/register");
			result.addObject("RequestURI", "vehicle/register.do");
		} else {
			result = new ModelAndView("vehicle/edit");
			result.addObject("RequestURI", "vehicle/edit.do");
		}

		result.addObject("vehicleForm", vehicleForm);
		result.addObject("errorMessage", message);
		result.addObject("brands", brands);
		result.addObject("numberNoti", numberNoti);
		result.addObject("carTypes", carTypes);

		return result;
	}

}
