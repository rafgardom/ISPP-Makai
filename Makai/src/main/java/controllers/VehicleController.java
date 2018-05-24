
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import services.NotificationService;
import services.TransporterService;
import services.TravelService;
import services.VehicleService;
import domain.Banner;
import domain.Brand;
import domain.CarType;
import domain.Transporter;
import domain.Travel;
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

	@Autowired
	private BannerService		bannerService;

	@Autowired
	private TravelService		travelService;


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

		ModelAndView result = new ModelAndView();
		Vehicle vehicle;
		Calendar today;
		boolean yearError = false;
		boolean pictureTooLong = false;
		boolean error = false;

		today = Calendar.getInstance();

		vehicle = this.vehicleService.reconstruct(vehicleForm, binding);
		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			if (binding.toString().contains("userImage"))
				result.addObject("imageError", "vehicle.license.error");
			result = this.createModelAndView(vehicleForm);

		} else
			try {

				if (today.get(Calendar.YEAR) < vehicle.getYear()) {
					yearError = true;
					FieldError fieldError;
					final String[] codes = {
						"vehicle.year.error"
					};
					fieldError = new FieldError("vehicleForm", "year", vehicleForm.getYear(), false, codes, null, "");
					binding.addError(fieldError);
					error = true;
				}
				if (vehicleForm.getUserImage().getSize() > 2097152) {
					pictureTooLong = true;
					FieldError fieldError;
					final String[] codes = {
						"vehicle.pictureSize.error"
					};
					fieldError = new FieldError("vehicleForm", "userImage", vehicleForm.getUserImage(), false, codes, null, "");
					binding.addError(fieldError);
					error = true;
				}

				if (error)
					throw new IllegalArgumentException();

				vehicle = this.vehicleService.save(vehicle);
				result = new ModelAndView("redirect:/vehicle/list.do");

			} catch (final Throwable oops) {
				//				if (yearError = true)
				//					result = this.createModelAndView(vehicleForm, "vehicle.year.error");
				//				if (pictureTooLong == true)
				//					result = this.createModelAndView(vehicleForm, "vehicle.pictureSize.error");
				if (error)
					result = this.createModelAndView(vehicleForm, "vehicle.validation.error");
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
		Transporter principal;
		boolean noPermission = false;

		principal = this.transporterService.findByPrincipal();

		try {
			vehicle = this.vehicleService.findOne(vehicleId);
			vehicleForm = this.vehicleService.toFormObject(vehicle);

			if (principal.getId() != vehicle.getTransporter().getId()) {
				noPermission = true;
				throw new IllegalArgumentException();
			}

			result = this.createModelAndView(vehicleForm);
		} catch (final Throwable e) {
			//			if (noPermission)
			//				result = new ModelAndView("redirect:travel/menu.do");
			//			else
			result = new ModelAndView("error");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final VehicleForm vehicleForm, final BindingResult binding) throws IOException {

		ModelAndView result;
		Vehicle vehicle;
		boolean error = false;
		boolean pictureTooLong = false;
		boolean tripWithCar = false;
		final Calendar today = Calendar.getInstance();
		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createModelAndView(vehicleForm);

		} else
			try {

				Vehicle aux = this.vehicleService.findVehicleInTravel(vehicleForm.getId());
				if (aux != null) {
					tripWithCar = true;
					throw new IllegalArgumentException();
				}

				if (vehicleForm.getUserImage().getSize() != 0 && (!vehicleForm.getUserImage().getContentType().contains("image")) || vehicleForm.getUserImage().getSize() > 2097152) {
					FieldError fieldError;
					final String[] codes = {
						"vehicle.picture.register.error"
					};
					fieldError = new FieldError("vehicleForm", "userImage", vehicleForm.getUserImage(), false, codes, null, "");
					binding.addError(fieldError);
					error = true;
					pictureTooLong = true;
				}

				if (today.get(Calendar.YEAR) < vehicleForm.getYear()) {
					FieldError fieldError;
					final String[] codes = {
						"vehicle.year.error"
					};
					fieldError = new FieldError("vehicleForm", "year", vehicleForm.getYear(), false, codes, null, "");
					binding.addError(fieldError);
					error = true;
				}

				final Pattern newLicensePatter = Pattern.compile("^\\d{4}[A-Z]{3}");
				final Pattern oldLicensePattern = Pattern.compile("^([A-Z]{1})?([A-Z]{2})?\\d{4}[A-Z]{2}");
				newLicensePatter.matcher(vehicleForm.getLicense());

				if (!newLicensePatter.matcher(vehicleForm.getLicense()).matches() && !oldLicensePattern.matcher(vehicleForm.getLicense()).matches()) {
					FieldError fieldError;
					final String[] codes = {
						"vehicle.license.error"
					};
					fieldError = new FieldError("vehicleForm", "license", vehicleForm.getLicense(), false, codes, null, "");
					binding.addError(fieldError);
					error = true;
				}

				aux = this.vehicleService.findOne(vehicleForm.getId());
				if (!aux.getIsActived())
					throw new IllegalArgumentException();

				if (error)
					throw new IllegalArgumentException();
				vehicle = this.vehicleService.reconstruct(vehicleForm, binding);
				vehicle = this.vehicleService.save(vehicle);
				result = new ModelAndView("redirect:/vehicle/list.do");

			} catch (final Throwable oops) {
				if (tripWithCar)
					result = this.createModelAndView(vehicleForm, "vehicle.travel.error");
				if (pictureTooLong == true)
					result = this.createModelAndView(vehicleForm, "vehicle.pictureSize.error");
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
		Collection<Vehicle> vehiclesAux;
		Transporter principal;
		final Integer numberNoti;
		final Boolean[] canDelete;
		Collection<Travel> travels;
		int i = 0;

		principal = this.transporterService.findByPrincipal();
		vehicles = new HashSet<VehicleForm>();
		numberNoti = this.notificationService.findNotificationWithoutRead();
		vehiclesAux = this.vehicleService.findActivatedVehicles(principal);
		canDelete = new Boolean[vehiclesAux.size()];
		try {
			for (final Vehicle a : vehiclesAux) {
				VehicleForm vehicleForm;

				vehicleForm = this.vehicleService.toFormObject(a);

				final Vehicle b = this.vehicleService.findVehicleInTravel(a.getId());
				if (b != null)
					vehicleForm.setHasNonStartedTrip(true);

				vehicles.add(vehicleForm);

				travels = this.travelService.findTravelByVehicleId(a);
				canDelete[i] = !this.vehicleService.checkTravelActive(travels);
				i++;
			}
			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("vehicle/list");
			result.addObject("vehicles", vehicles);
			result.addObject("canDelete", canDelete);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "vehicle/list.do");

			//			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			//			result.addObject("imagesRight", imagesRight);
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
			result.addObject("RequestURI", "vehicle/edit.do?vehicleId=" + vehicleForm.getId());
		}

		result.addObject("vehicleForm", vehicleForm);
		result.addObject("errorMessage", message);
		result.addObject("brands", brands);
		result.addObject("numberNoti", numberNoti);
		result.addObject("carTypes", carTypes);

		return result;
	}

}
