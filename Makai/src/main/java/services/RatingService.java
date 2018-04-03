
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RatingRepository;
import repositories.RequestRepository;
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

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		//Comprobar que un customer esta en un viaje
		Assert.isTrue(principal.getTravelPassengers().contains(travel));

		Assert.isNull(this.findRatingByCustomerFromTravel(travel.getId(), principal.getId()));

		today = Calendar.getInstance();
		Assert.isTrue(today.getTime().after(travel.getEndMoment()));

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

		result = this.ratingRepository.save(rating);

		if (result.getTrainer() != null) {
			Double avgRating;

			avgRating = this.getAvgByTrainerId(result.getTrainer().getId());

			result.getTrainer().setAvgRating(avgRating);
			this.trainerService.save(result.getTrainer());
		}

		if (result.getStars() == 0) {
			Integer count;
			String message;

			if (result.getTrainer() != null) {
				count = this.count0starsByTrainerId(result.getTrainer().getId());
				message = "El entrenador " + result.getTrainer().getName() + ", tiene " + count + " voto(s) negativo(s).";
			} else {
				count = this.count0starsByTravelId(result.getTravel().getId());
				message = "El viaje " + result.getTravel().getId() + ", tiene " + count + " voto(s) negativo(s).";
			}

			// comprobamos si tiene alguno mas a 0, y si es asi, se crea una notificacion al admin
			if (count >= 2) {
				Notification notification;

				notification = this.notificationService.create(this.administratorService.findOne());
				notification.setType(NotificationType.RATING);
				notification.setReason("Demasiadas puntuaciones negativas.");
				notification.setDescription(message);

				this.notificationService.save(notification);
			}
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
}
