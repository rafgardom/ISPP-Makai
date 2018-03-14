
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
import domain.Offer;
import domain.Rating;
import domain.Request;
import domain.Travel;

@Service
@Transactional
public class RatingService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RatingRepository	ratingRepository;

	@Autowired
	private RequestRepository	requestRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private CustomerService		customerService;

	@Autowired
	private OfferService		offerService;


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
		Assert.isTrue(principal.getTravelPassenger().equals(travel));

		today = Calendar.getInstance();
		Assert.isTrue(today.getTime().after(travel.getEndMoment()));

		result = new Rating();
		result.setCustomer(principal);
		result.setTravel(travel);

		return result;
	}

	public Rating createToRequest(final Request request) {
		Rating result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		//Comprobamos que la request es del principal
		Assert.isTrue(request.getCustomer().equals(principal));

		//Comprobar de que ha sido aceptada la oferta
		Assert.isTrue(!this.requestRepository.findOfferWithThisRequestTrue(request.getId()).equals(null));

		result = new Rating();
		result.setCustomer(principal);
		result.setRequest(request);

		//Sacamos la offer de nuestro request y a partir de la offer (aceptada) sacamos el trainer que la ha creado.
		final Offer offer = this.requestRepository.findOfferWithThisRequestTrue(request.getId());
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
}
