
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RatingRepository;
import repositories.RequestRepository;
import domain.Administrator;
import domain.Animal;
import domain.Customer;
import domain.Notification;
import domain.NotificationType;
import domain.Offer;
import domain.Rating;
import domain.Request;
import domain.Travel;

@Service
@Transactional
public class RatingService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RatingRepository		ratingRepository;

	@Autowired
	private RequestRepository		requestRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private TrainerService			trainerService;

	@Autowired
	private AnimalService			animalService;

	@Autowired
	private NotificationService		notificationService;


	// Constructors------------------------------------------------------------
	public RatingService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Rating findOne(final int ratingId) {
		Rating result;

		result = this.ratingRepository.findOne(ratingId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Rating> findAll() {
		Collection<Rating> result;

		result = this.ratingRepository.findAll();

		return result;
	}

	public Rating createToTravel(final Travel travel) {
		Rating result;
		Customer principal;
		Calendar today;
		Collection<Animal> animals;
		Boolean hasAnimal = false;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		//Comprobar que un customer tiene un animal en el viaje
		animals = this.animalService.findByActorId(principal.getId());
		for (final Animal a : travel.getAnimals())
			if (animals.contains(a))
				hasAnimal = true;

		//Comprobar que un customer esta en un viaje
		if (!hasAnimal)
			Assert.isTrue(principal.getTravelPassengers().contains(travel));

		Assert.isNull(this.findRatingByCustomerFromTravel(travel.getId(), principal.getId()));

		today = Calendar.getInstance();

		result = new Rating();
		result.setCustomer(principal);
		result.setTravel(travel);
		result.setMoment(today.getTime());

		return result;
	}
	public Rating createToRequest(final Request request) {
		Rating result;
		Customer principal;
		Calendar today;
		Offer offer;

		today = Calendar.getInstance();

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		//Comprobamos que la request es del principal
		Assert.isTrue(request.getCustomer().equals(principal));

		//Comprobar que ha sido aceptada la oferta
		offer = this.requestRepository.findOfferWithThisRequestTrue(request.getId());
		Assert.isTrue(!offer.equals(null));

		result = new Rating();
		result.setCustomer(principal);
		result.setRequest(request);
		result.setMoment(today.getTime());

		//Sacamos la offer de nuestro request y a partir de la offer (aceptada) sacamos el trainer que la ha creado.
		result.setTrainer(offer.getTrainer());

		return result;
	}

	public Rating save(final Rating rating) {
		Assert.notNull(rating);
		Rating result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(rating.getCustomer().getId() == principal.getId());

		if (rating.getComment().length() > 0 && rating.getComment().trim().length() == 0)
			throw new IllegalArgumentException("only whiteSpaces");

		result = this.ratingRepository.save(rating);

		Double avgRating, oldAvgRating;

		if (result.getTrainer() != null) {

			oldAvgRating = result.getTrainer().getAvgRating();
			avgRating = this.getAvgByTrainerId(result.getTrainer().getId());

			result.getTrainer().setAvgRating(avgRating);
			this.trainerService.save(result.getTrainer());
		} else {
			oldAvgRating = result.getTravel().getTransporterOwner().getAvgRating();
			avgRating = this.getAvgByCustomerId(result.getTravel().getTransporterOwner().getId());

			result.getTravel().getTransporterOwner().setAvgRating(avgRating);
			this.customerService.save((Customer) result.getTravel().getTransporterOwner());
		}

		if ((oldAvgRating >= 3.0 || oldAvgRating == 0.0) && avgRating < 3.0) {
			String message;

			if (result.getTrainer() != null)
				message = "trainer#RN0" + result.getTrainer().getName();
			else
				message = "travel#RN0" + result.getTravel().getTransporterOwner().getName();

			Notification notification;
			final Administrator admin = this.administratorService.findOne();
			notification = this.notificationService.create(admin);
			notification.setType(NotificationType.RATING);
			notification.setReason("#RN0");
			notification.setDescription(message);

			this.notificationService.save(notification);

		}

		return result;
	}
	public void delete(final Rating rating) {
		Customer principal;

		Assert.notNull(rating);
		Assert.isTrue(rating.getId() != 0);

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(rating.getCustomer().getId() == principal.getId());

		//Comprobar de que no tiene ninguna oferta aceptada

		this.ratingRepository.delete(rating);
	}

	// Other business methods -------------------------------------------------

	public Integer count0starsByTrainerId(final int trainerId) {
		Integer result;

		result = this.ratingRepository.count0starsByTrainerId(trainerId);

		return result;
	}

	public Integer count0starsByTravelId(final int travelId) {
		Integer result;

		result = this.ratingRepository.count0starsByTravelId(travelId);

		return result;
	}

	public Rating findRatingByCustomerFromTravel(final int travelId, final int customerId) {
		return this.ratingRepository.findRatingByCustomerFromTravel(travelId, customerId);
	}

	public Collection<Rating> findTravelRatingByCustomer(final int customerId) {
		return this.ratingRepository.findTravelRatingByCustomer(customerId);
	}

	public Collection<Rating> findByTransporterId(final int transporterId) {
		Collection<Rating> result;

		result = this.ratingRepository.findByTransporterId(transporterId);

		return result;
	}

	public Collection<Rating> findByTrainerId(final int trainerId) {
		Collection<Rating> result;

		result = this.ratingRepository.findByTrainerId(trainerId);

		return result;
	}

	public Double getAvgByTrainerId(final int trainerId) {
		Double result;

		result = this.ratingRepository.getAvgByTrainerId(trainerId);

		return result;
	}

	public Double getAvgByCustomerId(final int customerId) {
		Double result;

		result = this.ratingRepository.getAvgByCustomerId(customerId);

		return result;
	}

	public Boolean getFindByRequestId(final int requestId) {
		final Rating rating = this.ratingRepository.findByRequestId(requestId);
		return rating == null ? false : true;
	}

	public int getFindByRequestIdNumber(final int requestId) {
		final Rating rating = this.ratingRepository.findByRequestId(requestId);
		return rating != null ? rating.getStars() : 0;
	}
}
