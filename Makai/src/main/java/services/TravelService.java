
package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TravelRepository;
import domain.Actor;
import domain.Animal;
import domain.Customer;
import domain.Notification;
import domain.NotificationType;
import domain.Transporter;
import domain.Travel;
import domain.Vehicle;
import forms.TravelForm;

@Service
@Transactional
public class TravelService {

	// Managed repository —---------------------------------------------------
	@Autowired
	private TravelRepository	travelRepository;

	// Supporting services —------------------------------------------------—
	@Autowired
	private TransporterService	transporterService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private AnimalService		animalService;

	@Autowired
	private ProfessionalService	professionalService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private Validator			validator;


	// Constructors------------------------------------------------------------
	public TravelService() {
		super();
	}

	// Simple CRUD methods —------------------------------------------------—
	public Travel findOne(final int travelId) {
		Travel result;

		result = this.travelRepository.findOne(travelId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Travel> findAll() {
		Collection<Travel> result;

		result = this.travelRepository.findAll();

		return result;
	}

	public TravelForm createForm() {
		final TravelForm result = new TravelForm();

		return result;
	}

	public Travel create() {
		Travel result;
		Transporter principal;
		final Vehicle vehicle = null;
		Collection<Animal> animals;

		principal = this.transporterService.findByPrincipal();
		Assert.notNull(principal);
		animals = new ArrayList<Animal>();

		result = new Travel();
		result.setTransporterOwner(principal);
		result.setVehicle(vehicle);
		result.setAnimals(animals);

		return result;
	}

	public Travel save(final Travel travel) {
		Assert.notNull(travel);
		Travel result;
		Transporter principal;
		Calendar today;

		principal = this.transporterService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(travel.getTransporterOwner().getId() == principal.getId());

		today = Calendar.getInstance();
		Assert.isTrue(today.getTime().before(travel.getStartMoment()));
		Assert.isTrue(travel.getStartMoment().before(travel.getEndMoment()));

		Assert.isTrue((travel.getAnimalSeats() != null || travel.getAnimalSeats() > 0) || (travel.getHumanSeats() != null || travel.getHumanSeats() > 0));

		result = this.travelRepository.save(travel);

		return result;
	}

	public void delete(final Travel travel) {
		Actor principal;
		Calendar today;

		Assert.notNull(travel);
		Assert.isTrue(travel.getId() != 0);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(this.actorService.checkAuthority(principal, "ADMIN") || this.actorService.checkAuthority(principal, "CUSTOMER") || this.actorService.checkAuthority(principal, "PROFESSIONAL"));
		if (this.actorService.checkAuthority(principal, "CUSTOMER") || this.actorService.checkAuthority(principal, "PROFESSIONAL"))
			Assert.isTrue(travel.getTransporterOwner().getId() == principal.getId());

		today = Calendar.getInstance();
		Assert.isTrue(today.getTime().before(travel.getStartMoment()));

		this.travelRepository.delete(travel);
	}

	// Other business methods —---------------------------------------------—

	public void registerTravel(final TravelForm travelForm) {
		Customer customer;
		Collection<Travel> travels;
		Collection<Animal> animalsForm;
		Collection<Animal> animals;
		Collection<Animal> animalsAux;
		Integer animalCount;
		Notification notification = null;
		Travel travel;

		travel = this.findOne(travelForm.getId());
		Assert.isTrue(travel.getAnimalSeats() > 0 || travel.getHumanSeats() > 0);

		customer = this.customerService.findByPrincipal();

		travels = customer.getTravelPassengers();

		if (travelForm.isPrincipalPassenger()) {
			if (!travels.contains(travel)) {
				travel.setHumanSeats(travel.getHumanSeats() - 1);
				travels.add(travel);
				customer.setTravelPassengers(travels);
				this.customerService.save(customer);
			}

		} else if (travels.contains(travel)) {
			travel.setHumanSeats(travel.getHumanSeats() + 1);
			travels.remove(travel);
			customer.setTravelPassengers(travels);
			this.customerService.save(customer);
		}

		animalsForm = travelForm.getAnimals();

		animals = new ArrayList<Animal>();
		animalsAux = travel.getAnimals();
		for (final Animal a : animalsAux)
			if (!this.animalService.findByActorId(customer.getId()).contains(a))
				animals.add(a);
			else
				travel.setAnimalSeats(travel.getAnimalSeats() + 1);
		if (animalsForm != null) {
			animalCount = travelForm.getAnimals().size();
			animals.addAll(animalsForm);
			Assert.isTrue((travel.getAnimalSeats() - animalCount) >= 0);
			travel.setAnimalSeats(travel.getAnimalSeats() - animalCount);
		}
		travel.setAnimals(animals);
		this.travelRepository.save(travel);

		notification = this.notificationService.create(travel.getTransporterOwner());
		notification.setReason("Nueva inscripcion a su viaje");
		notification.setDescription("Un usuario se ha apuntado a un viaje creado por usted");
		notification.setType(NotificationType.TRAVEL);
		this.notificationService.save(notification);

	}
	public Travel reconstruct(final TravelForm travelForm, final BindingResult binding) throws IOException {
		Assert.notNull(travelForm);
		Travel result;

		if (travelForm.getId() == 0)
			result = this.create();
		else
			result = this.findOne(travelForm.getId());

		result.setDestination(travelForm.getDestination());
		result.setOrigin(travelForm.getOrigin());
		result.setStartMoment(sumDates(travelForm.getStartDate(), travelForm.getStartTime()));
		result.setEndMoment(sumDates(travelForm.getEndDate(), travelForm.getEndTime()));
		result.setAnimalSeats(travelForm.getAnimalSeats());
		result.setVehicle(travelForm.getVehicle());
		//result.setAnimals(travelForm.getAnimals());
		result.setHumanSeats(travelForm.getHumanSeats());

		this.validator.validate(result, binding);

		return result;
	}

	public TravelForm toFormObject(final Travel travel) {
		Assert.notNull(travel);
		final TravelForm result = new TravelForm();

		result.setDestination(travel.getDestination());
		result.setOrigin(travel.getOrigin());
		result.setStartDate(travel.getStartMoment());
		result.setStartTime(travel.getStartMoment());
		result.setEndDate(travel.getEndMoment());
		result.setEndTime(travel.getEndMoment());
		result.setAnimalSeats(travel.getAnimalSeats());
		result.setHumanSeats(travel.getHumanSeats());
		result.setVehicle(travel.getVehicle());
		//result.setAnimals(travel.getAnimals());
		result.setId(travel.getId());

		return result;
	}
	
	public Date sumDates(Date date,Date time){
		Date res = null;
		Calendar caltime = Calendar.getInstance(); 
		Calendar caldate = Calendar.getInstance();
		caltime.setTime(time); 
        caldate.setTime(date); 
		int hour = caltime.get(Calendar.HOUR_OF_DAY);
		int minutes = caltime.get(Calendar.MINUTE);

        caldate.set(Calendar.HOUR_OF_DAY, hour);
        caldate.set(Calendar.MINUTE, minutes);
        res = caldate.getTime();
        return res;
	}

	public Collection<Travel> findTravelByTransporterId(final int transporterId) {
		return this.travelRepository.findTravelByTransporterId(transporterId);
	}

	public Collection<Travel> findTravelByVehicleId(final Vehicle vehicle) {
		return this.travelRepository.findTravelByVehicleId(vehicle.getId());
	}

	public Collection<Travel> findTravelWithAnimalsByCustomer(final int customerId) {
		return this.travelRepository.findTravelWithAnimalsByCustomer(customerId);
	}

}
