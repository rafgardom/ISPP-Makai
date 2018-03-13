
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RatingRepository;
import domain.Customer;
import domain.Rating;
import domain.Request;
import domain.Travel;

@Service
@Transactional
public class RatingService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RatingRepository	ratingRepository;

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

		//Comprobar de que ha sido aceptada la oferta

		result = new Rating();
		result.setCustomer(principal);
		result.setRequest(request);

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
