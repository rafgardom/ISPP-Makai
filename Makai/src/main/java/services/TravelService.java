
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
import domain.Breed;
import domain.Customer;
import domain.Notification;
import domain.NotificationType;
import domain.Specie;
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
		Vehicle vehicle;

		vehicle = travel.getVehicle();

		principal = this.transporterService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(travel.getTransporterOwner().getId() == principal.getId());

		today = Calendar.getInstance();
		Assert.isTrue(today.getTime().before(travel.getStartMoment()));

		Assert.isTrue((travel.getAnimalSeats() != null || travel.getAnimalSeats() > 0) || (travel.getHumanSeats() != null || travel.getHumanSeats() > 0));
		Assert.isTrue(travel.getAnimalSeats() + travel.getAnimalSeats() <= vehicle.getSeats());
		Assert.isTrue(travel.getAnimalSeats() > travel.getHumanSeats());

		result = this.travelRepository.save(travel);

		return result;
	}

	public void delete(final Travel travel) {
		Actor principal;
		Calendar today;
		Collection<Travel> travels_participated;
		Collection<Transporter> transporters;
		Customer customer;
		Notification notification;

		Assert.notNull(travel);
		Assert.isTrue(travel.getId() != 0);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(this.actorService.checkAuthority(principal, "ADMIN") || this.actorService.checkAuthority(principal, "CUSTOMER") || this.actorService.checkAuthority(principal, "PROFESSIONAL"));
		//		Assert.isTrue(travel.getTransporterOwner().getId() == principal.getId());

		transporters = this.transporterService.findPassengersByTravel(travel.getId());

		today = Calendar.getInstance();
		Assert.isTrue(today.getTime().before(travel.getStartMoment()));

		if (transporters.size() != 0)
			for (final Transporter t : transporters) {
				travels_participated = t.getTravelPassengers();
				if (travels_participated.contains(travel)) {
					travels_participated.remove(travel);
					customer = this.customerService.findOne(t.getId());
					customer.setTravelPassengers(travels_participated);
					this.customerService.save(customer);
					notification = this.notificationService.create(customer);
					notification.setReason("Viaje eliminado");
					notification.setDescription("Se ha eliminado un viaje en el que estabas apuntado");
					notification.setType(NotificationType.TRAVEL);
					this.notificationService.save(notification);
				}

			}

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
		Breed[] breeds;
		Specie specie;
		Collection<Specie> species;

		travel = this.findOne(travelForm.getId());

		Assert.isTrue((travel.getAnimalSeats() != null || travel.getAnimalSeats() > 0));
		customer = this.customerService.findByPrincipal();
		Assert.isTrue(travel.getTransporterOwner().getId() != customer.getId());

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
		species = travelForm.getSpecies();

		Assert.isTrue(animalsForm.size() >= 1);

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

		for (final Animal a : animals) {
			breeds = a.getBreeds().toArray(new Breed[a.getBreeds().size()]);
			specie = breeds[0].getSpecie();
			Assert.isTrue(species.contains(specie));
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
		result.setStartMoment(this.sumDates(travelForm.getStartDate(), travelForm.getStartTime()));
		result.setDuration(travelForm.getDuration());
		result.setAnimalSeats(travelForm.getAnimalSeats());
		result.setVehicle(travelForm.getVehicle());
		result.setHumanSeats(travelForm.getHumanSeats());
		result.setSpecies(travelForm.getSpecies());

		this.validator.validate(result, binding);

		return result;
	}

	public TravelForm toFormObject(final Travel travel) {
		Assert.notNull(travel);
		final TravelForm result = new TravelForm();
		Transporter principal;
		final Collection<Animal> animals = new ArrayList<Animal>();
		Collection<Animal> animalsAux;

		principal = this.transporterService.findByPrincipal();
		animalsAux = this.animalService.findByActorId(principal.getId());
		for (final Animal a : animalsAux)
			if (travel.getAnimals().contains(a))
				animals.add(a);

		result.setDestination(travel.getDestination());
		result.setOrigin(travel.getOrigin());
		result.setStartDate(travel.getStartMoment());
		result.setStartTime(travel.getStartMoment());
		result.setDuration(travel.getDuration());
		result.setAnimalSeats(travel.getAnimalSeats());
		result.setHumanSeats(travel.getHumanSeats());
		result.setVehicle(travel.getVehicle());
		result.setId(travel.getId());
		result.setAnimals(animals);
		result.setSpecies(travel.getSpecies());

		if (principal.getTravelPassengers().contains(travel))
			result.setPrincipalPassenger(true);
		else
			result.setPrincipalPassenger(false);

		return result;
	}

	public Date sumDates(final Date date, final Date time) {
		Date res = null;
		final Calendar caltime = Calendar.getInstance();
		final Calendar caldate = Calendar.getInstance();
		caltime.setTime(time);
		caldate.setTime(date);
		final int hour = caltime.get(Calendar.HOUR_OF_DAY);
		final int minutes = caltime.get(Calendar.MINUTE);

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
