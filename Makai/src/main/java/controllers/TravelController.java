
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnimalService;
import services.BannerService;
import services.NotificationService;
import services.RatingService;
import services.SpecieService;
import services.TransporterService;
import services.TravelService;
import services.VehicleService;
import domain.Animal;
import domain.Banner;
import domain.Breed;
import domain.Rating;
import domain.Specie;
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
	private AnimalService		animalService;

	@Autowired
	private RatingService		ratingService;

	@Autowired
	private TransporterService	transporterService;

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private BannerService		bannerService;

	@Autowired
	private SpecieService		specieService;


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
		boolean wrongSeats = false;
		boolean tooManySeats = false;
		boolean wrongTime = false;
		final boolean notPrincipal = false;
		final boolean passengers = false;
		boolean speciesError = false;
		boolean error = false;

		Calendar today;
		Transporter principal;
		principal = this.transporterService.findByPrincipal();

		today = Calendar.getInstance();

		if (binding.hasErrors())
			result = this.createModelAndView(travelForm, "travel.commit.error");
		else
			try {
				if (travelForm.getHumanSeats() == null)
					travelForm.setHumanSeats(0);

				if (travelForm.getSpecies() == null || travelForm.getSpecies().isEmpty()) {
					speciesError = true;
					FieldError fieldError;
					final String[] codes = {
						"travel.species.error"
					};
					fieldError = new FieldError("travelForm", "species", travelForm.getSpecies(), false, codes, null, "");
					binding.addError(fieldError);
					error = true;
				}

				if ((travelForm.getAnimalSeats() == null || travelForm.getAnimalSeats() < 1) && (travelForm.getHumanSeats() == null || travelForm.getHumanSeats() < 1)) {
					FieldError fieldError;
					final String[] codes = {
						"travel.seats.error"
					};
					fieldError = new FieldError("travelForm", "humanSeats", travelForm.getHumanSeats(), false, codes, null, "");
					binding.addError(fieldError);
					error = true;
				}

				if (travelForm.getAnimalSeats() + travelForm.getAnimalSeats() > travelForm.getVehicle().getSeats()) {
					tooManySeats = true;
					FieldError fieldError;
					final String[] codes = {
						"travel.tooManySeats.error"
					};
					fieldError = new FieldError("travelForm", "humanSeats", travelForm.getHumanSeats(), false, codes, null, "");
					binding.addError(fieldError);
					error = true;
				}

				//				final Collection<Transporter> transporters = this.transporterService.findPassengersByTravel(travel.getId());

				if (!today.getTime().before(travelForm.getStartDate())) {
					wrongTime = true;
					FieldError fieldError;
					final String[] codes = {
						"travel.time.error"
					};
					fieldError = new FieldError("travelForm", "startDate", travelForm.getStartDate(), false, codes, null, "");
					binding.addError(fieldError);
					error = true;
				}

				//				if (transporters.size() > 0) {
				//					passengers = true;
				//					throw new IllegalArgumentException();
				//				}

				//				if (travel.getTransporterOwner().getId() != principal.getId()) {
				//					notPrincipal = true;
				//					throw new IllegalArgumentException();
				//				}
				if (travelForm.getHumanSeats() == 0 && travelForm.getAnimalSeats() == 0 || travelForm.getHumanSeats() == null && travelForm.getAnimalSeats() == null || travelForm.getHumanSeats() == 0 && travelForm.getAnimalSeats() == null
					|| travelForm.getHumanSeats() == null && travelForm.getAnimalSeats() == 0) {
					FieldError fieldError;
					wrongSeats = true;
					final String[] codes = {
						"travel.seats.error"
					};
					fieldError = new FieldError("travelForm", "humanSeats", travelForm.getHumanSeats(), false, codes, null, "");
					binding.addError(fieldError);
					error = true;
				}

				if (error)
					throw new IllegalArgumentException();

				travel = this.travelService.reconstruct(travelForm, binding);
				this.travelService.save(travel);
				result = new ModelAndView("redirect:myList.do");

			} catch (final Throwable oops) {
				if (wrongSeats) {
					result = this.createModelAndView(travelForm, "travel.seats.error");
					FieldError fieldError;
					final String[] codes = {
						"travel.seats.error"
					};
					fieldError = new FieldError("travelForm", "humanSeats", travelForm.getHumanSeats(), false, codes, null, "");
					binding.addError(fieldError);
				}
				if (passengers)
					result = this.createModelAndView(travelForm, "travel.edit.error");
				else if (wrongTime)
					result = this.createModelAndView(travelForm, "travel.time.error");
				else if (notPrincipal)
					result = this.createModelAndView(travelForm, "travel.principal.error");
				else if (speciesError)
					result = this.createModelAndView(travelForm, "travel.species.error");
				else if (tooManySeats)
					result = this.createModelAndView(travelForm, "travel.tooManySeats.error");
				else
					result = this.createModelAndView(travelForm, "travel.register.error");

			}
		return result;
	}
	//Register

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam final int travelId) {
		ModelAndView result;
		TravelForm travelForm;
		try {
			final Travel travel = this.travelService.findOne(travelId);
			travelForm = this.travelService.toFormObject(travel);
			result = this.registerModelAndView(travelForm);

		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid final TravelForm travelForm, final BindingResult binding) {

		ModelAndView result;
		boolean speciesError = false;
		Breed[] breeds;
		Specie specie;
		Collection<Specie> species;
		Collection<Animal> animalsForm;

		if (binding.hasErrors())
			//System.out.println(binding.toString());
			result = this.registerModelAndView(travelForm);
		else
			try {
				animalsForm = travelForm.getAnimals();
				species = travelForm.getSpecies();

				for (final Animal a : animalsForm) {
					breeds = a.getBreeds().toArray(new Breed[a.getBreeds().size()]);
					specie = breeds[0].getSpecie();
					if (!species.contains(specie)) {
						speciesError = true;
						throw new IllegalArgumentException();
					}
				}

				this.travelService.registerTravel(travelForm);

				this.registerModelAndView(travelForm);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				if (speciesError)
					result = this.registerModelAndView(travelForm, "travel.species.error");
				else
					result = this.registerModelAndView(travelForm, "travel.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int travelId) {
		ModelAndView result;
		Travel travel;
		TravelForm travelForm;
		Transporter principal;
		boolean noPermission = false;
		final Date now = new Date();

		principal = this.transporterService.findByPrincipal();

		try {
			travel = this.travelService.findOne(travelId);
			travelForm = this.travelService.toFormObject(travel);

			if (principal.getId() != travel.getTransporterOwner().getId()) {
				noPermission = true;
				throw new IllegalArgumentException();
			}

			if (travel.getStartMoment().before(now))
				throw new IllegalArgumentException();

			result = this.createModelAndView(travelForm);
		} catch (final Throwable e) {
			if (noPermission)
				result = new ModelAndView("redirect:menu.do");
			else
				result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final TravelForm travelForm, final BindingResult binding) throws IOException {

		ModelAndView result;
		Travel travel = new Travel();
		final Date now = new Date();
		boolean error = false;
		boolean wrongTime = false;
		boolean speciesError = false;
		try {

			if (travelForm.getSpecies() == null || travelForm.getSpecies().isEmpty()) {
				speciesError = true;
				FieldError fieldError;
				final String[] codes = {
					"travel.species.error"
				};
				fieldError = new FieldError("travelForm", "species", travelForm.getSpecies(), false, codes, null, "");
				binding.addError(fieldError);
				error = true;
			}

			if ((travelForm.getAnimalSeats() == null || travelForm.getAnimalSeats() < 1) && (travelForm.getHumanSeats() == null || travelForm.getHumanSeats() < 1)) {
				FieldError fieldError;
				final String[] codes = {
					"travel.seats.error"
				};
				fieldError = new FieldError("travelForm", "humanSeats", travelForm.getHumanSeats(), false, codes, null, "");
				binding.addError(fieldError);
				error = true;
			}

			if (travelForm.getAnimalSeats() + travelForm.getAnimalSeats() > travelForm.getVehicle().getSeats()) {
				FieldError fieldError;
				final String[] codes = {
					"travel.tooManySeats.error"
				};
				fieldError = new FieldError("travelForm", "humanSeats", travelForm.getHumanSeats(), false, codes, null, "");
				binding.addError(fieldError);
				error = true;
			}

			if (travelForm.getStartDate().before(now)) {
				FieldError fieldError;
				final String[] codes = {
					"travel.time.error"
				};
				fieldError = new FieldError("travelForm", "startDate", travel.getStartMoment(), false, codes, null, "");
				binding.addError(fieldError);
				wrongTime = true;
				error = true;
			}

			if (travelForm.getHumanSeats() == 0 && travelForm.getAnimalSeats() == 0 || travelForm.getHumanSeats() == null && travelForm.getAnimalSeats() == null || travelForm.getHumanSeats() == 0 && travelForm.getAnimalSeats() == null
				|| travelForm.getHumanSeats() == null && travelForm.getAnimalSeats() == 0) {
				FieldError fieldError;
				final String[] codes = {
					"travel.seats.error"
				};
				fieldError = new FieldError("travelForm", "humanSeats", travelForm.getHumanSeats(), false, codes, null, "");
				binding.addError(fieldError);
				error = true;
			}

			if (error)
				throw new IllegalArgumentException();

			travel = this.travelService.reconstruct(travelForm, binding);

			if (binding.hasErrors()) {
				System.out.println(binding.toString());
				result = this.createModelAndView(travelForm);

			} else
				try {
					travel = this.travelService.save(travel);
					result = new ModelAndView("redirect:/travel/myList.do");

				} catch (final Throwable oops) {
					result = this.createModelAndView(travelForm, "travel.commit.error");
				}
		} catch (final Throwable e) {
			if (wrongTime)
				result = this.createModelAndView(travelForm, "travel.time.error");
			else if (speciesError)
				result = this.createModelAndView(travelForm, "travel.species.error");
			else
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
			result = new ModelAndView("error");
		}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Travel> travels_all;
		Collection<Travel> travelsByPrincipal;
		Collection<Animal> myAnimals;
		try {
			final Collection<Travel> travels = new ArrayList<Travel>();
			final Integer numberNoti;
			numberNoti = this.notificationService.findNotificationWithoutRead();
			Calendar today;
			travels_all = this.travelService.findAll();
			today = Calendar.getInstance();
			Transporter principal;
			principal = this.transporterService.findByPrincipal();

			myAnimals = this.animalService.findByActorId(principal.getId());
			travelsByPrincipal = this.travelService.findTravelByTransporterId(principal.getId());

			for (final Travel aux : travels_all)
				if (today.getTime().before(aux.getStartMoment()))
					travels.add(aux);

			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("travel/list");
			result.addObject("principal", principal);
			result.addObject("myAnimals", myAnimals);
			result.addObject("travelsByPrincipal", travelsByPrincipal);
			result.addObject("travels", travels);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "travel/list.do");

			//			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			//			result.addObject("imagesRight", imagesRight);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView myList() {
		ModelAndView result;
		Collection<Travel> travels_all;
		final Collection<Travel> travels = new ArrayList<Travel>();
		Transporter principal;
		final Integer numberNoti;
		try {
			numberNoti = this.notificationService.findNotificationWithoutRead();
			Collection<Travel> travels_participated;
			Collection<Travel> travels_animals;

			Calendar today;
			principal = this.transporterService.findByPrincipal();
			travels_all = this.travelService.findTravelByTransporterId(principal.getId());
			travels_participated = principal.getTravelPassengers();
			travels_animals = this.travelService.findTravelWithAnimalsByCustomer(principal.getId());

			today = Calendar.getInstance();

			for (final Travel aux : travels_all)
				if (today.getTime().before(aux.getStartMoment()))
					travels.add(aux);

			for (final Travel aux : travels_participated)
				if (today.getTime().before(aux.getStartMoment()))
					travels.add(aux);

			for (final Travel aux : travels_animals)
				if (today.getTime().before(aux.getStartMoment()) && !travels.contains(aux))
					travels.add(aux);

			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("travel/myList");

			result.addObject("pastList", false);
			result.addObject("principal", principal);
			result.addObject("travels", travels);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "travel/myList.do");

			//			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			//			result.addObject("imagesRight", imagesRight);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	@RequestMapping(value = "/myPastList", method = RequestMethod.GET)
	public ModelAndView myPastList() {
		ModelAndView result;
		Collection<Travel> travels_all;
		Collection<Travel> travels_participated;
		final Collection<Travel> travels = new ArrayList<Travel>();
		Transporter principal;
		final Integer numberNoti;

		try {
			numberNoti = this.notificationService.findNotificationWithoutRead();
			Collection<Rating> principalRatings;
			Collection<Travel> travels_animals;

			Calendar today;
			principal = this.transporterService.findByPrincipal();
			travels_all = this.travelService.findTravelByTransporterId(principal.getId());
			travels_participated = principal.getTravelPassengers();
			principalRatings = this.ratingService.findTravelRatingByCustomer(principal.getId());
			travels_animals = this.travelService.findTravelWithAnimalsByCustomer(principal.getId());

			today = Calendar.getInstance();

			for (final Travel aux : travels_all)
				if (today.getTime().after(aux.getStartMoment()))
					travels.add(aux);

			for (final Travel aux : travels_participated)
				if (today.getTime().after(aux.getStartMoment()))
					travels.add(aux);

			for (final Travel aux : travels_animals)
				if (today.getTime().after(aux.getStartMoment()) && !travels.contains(aux))
					travels.add(aux);

			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("travel/myPastList");

			result.addObject("pastList", true);
			result.addObject("travels", travels);
			result.addObject("principalRatings", principalRatings);
			result.addObject("principal", principal);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "travel/myPastList.do");

			//			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			//			result.addObject("imagesRight", imagesRight);

		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

	// Display -------------------------------------------------------		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int travelId) {
		ModelAndView result;
		Travel travel;
		Vehicle vehicle;
		final Integer numberNoti;
		Collection<Transporter> passengers;
		Collection<Animal> animals;
		Collection<Animal> customerAnimals;
		Collection<Specie> species;
		Calendar today;
		boolean noPermission = false;
		boolean animalsInTravel = false;
		Transporter principal;

		try {
			travel = this.travelService.findOne(travelId);
			numberNoti = this.notificationService.findNotificationWithoutRead();
			vehicle = travel.getVehicle();
			passengers = this.transporterService.findPassengersByTravel(travelId);
			animals = travel.getAnimals();
			species = travel.getSpecies();
			principal = this.transporterService.findByPrincipal();
			customerAnimals = this.animalService.findByActorId(principal.getId());

			for (final Animal a : customerAnimals)
				if (animals.contains(a))
					animalsInTravel = true;

			today = Calendar.getInstance();
			if (today.getTime().after(travel.getStartMoment()) && (travel.getTransporterOwner().getId() != principal.getId() && !passengers.contains(principal)) && !animalsInTravel) {
				noPermission = true;
				throw new IllegalArgumentException();
			}

			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("travel/display");
			result.addObject("species", species);
			result.addObject("animals", animals);
			result.addObject("vehicle", vehicle);
			result.addObject("passengers", passengers);
			result.addObject("travel", travel);
			result.addObject("numberNoti", numberNoti);
			result.addObject("requestURI", "travel/display.do?travelId=" + travel.getId());

			//			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			//			result.addObject("imagesRight", imagesRight);

		} catch (final Throwable e) {
			if (noPermission)
				result = new ModelAndView("redirect:menu.do");
			else
				result = new ModelAndView("error");
		}
		return result;
	}
	// Menu -------------------------------------------------------		
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public ModelAndView menu() {
		ModelAndView result;
		final Integer numberNoti;

		try {
			numberNoti = this.notificationService.findNotificationWithoutRead();

			//			final ArrayList<String> imagesLeft = this.bannerService.getBannerByZone("izquierda");
			final ArrayList<Banner> imagesBottom = this.bannerService.getBannerByZone("abajo");
			//			final ArrayList<String> imagesRight = this.bannerService.getBannerByZone("derecha");

			result = new ModelAndView("travel/menu");
			result.addObject("numberNoti", numberNoti);

			//			result.addObject("imagesLeft", imagesLeft);
			result.addObject("imagesBottom", imagesBottom);
			//			result.addObject("imagesRight", imagesRight);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
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
		final Integer numberNoti;
		Collection<Specie> species;

		species = this.specieService.findAll();
		principal = this.transporterService.findByPrincipal();
		vehicles = this.vehicleService.findVehicleByTransporterId(principal.getId());
		numberNoti = this.notificationService.findNotificationWithoutRead();

		final ModelAndView result = new ModelAndView("travel/create");
		result.addObject("travelForm", travelForm);
		result.addObject("species", species);
		result.addObject("vehicles", vehicles);
		if (travelForm.getId() == 0)
			result.addObject("RequestURI", "travel/create.do");
		else
			result.addObject("RequestURI", "travel/edit.do?travelId=" + travelForm.getId());
		result.addObject("numberNoti", numberNoti);
		result.addObject("errorMessage", message);

		return result;
	}

	protected ModelAndView registerModelAndView(final TravelForm travelForm) {
		final ModelAndView result = this.registerModelAndView(travelForm, null);
		return result;
	}

	protected ModelAndView registerModelAndView(final TravelForm travelForm, final String message) {
		Collection<Animal> animals;
		Transporter principal;
		final Integer numberNoti;

		principal = this.transporterService.findByPrincipal();
		animals = this.animalService.findByActorId(principal.getId());
		numberNoti = this.notificationService.findNotificationWithoutRead();

		final ModelAndView result = new ModelAndView("travel/register");
		result.addObject("travelForm", travelForm);
		result.addObject("animals", animals);
		result.addObject("principal", principal);
		result.addObject("RequestURI", "travel/register.do?travelId=" + travelForm.getId());
		result.addObject("numberNoti", numberNoti);
		result.addObject("errorMessage", message);

		return result;
	}

}
