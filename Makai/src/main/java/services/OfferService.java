
package services;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.OfferRepository;
import repositories.RequestRepository;
import domain.Offer;
import domain.Request;
import domain.Trainer;
import forms.OfferForm;

@Service
@Transactional
public class OfferService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private OfferRepository		offerRepository;

	@Autowired
	private RequestRepository	requestRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private TrainerService		trainerService;


	// Constructors------------------------------------------------------------
	public OfferService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Offer findOne(final int offerId) {
		Offer result;

		result = this.offerRepository.findOne(offerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Offer> findAll() {
		Collection<Offer> result;

		result = this.offerRepository.findAll();

		return result;
	}

	//Creamos una offer a partir de una request que ya tiene asignado un animal
	public Offer createWithAnimal(final Request request) {
		Offer result;
		Trainer principal;

		principal = this.trainerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Offer();
		result.setTrainer(principal);
		result.setRequest(request);
		result.setIsAccepted(false);

		Assert.isTrue(request.getAnimal() != null);
		result.setAnimal(request.getAnimal());

		//Comprobar de que no tiene ninguna oferta aceptada
		//Assert.isTrue(this.requestRepository.findOfferWithThisRequestTrue(request.getId()).equals(null));

		return result;
	}

	//Creamos una offer a partir de una request que no tiene asignado un animal
	public Offer createWithoutAnimal(final Request request) {
		Offer result;
		Trainer principal;

		principal = this.trainerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Offer();
		result.setTrainer(principal);
		result.setRequest(request);
		result.setIsAccepted(false);

		//Comprobar de que no tiene ninguna oferta aceptada
		//	Assert.isTrue(this.requestRepository.findOfferWithThisRequestTrue(request.getId()).equals(null));

		return result;
	}

	public Offer save(final Offer offer) {
		Assert.notNull(offer);
		Offer result;
		Trainer principal;

		principal = this.trainerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(offer.getTrainer().getId() == principal.getId());

		Assert.isTrue(!offer.getIsAccepted());

		result = this.offerRepository.save(offer);

		return result;
	}

	public void delete(final Offer offer) {
		Trainer principal;

		Assert.notNull(offer);
		Assert.isTrue(offer.getId() != 0);

		principal = this.trainerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(offer.getTrainer().getId() == principal.getId());

		Assert.isTrue(!offer.getIsAccepted());

		this.offerRepository.delete(offer);
	}

	//Other business service

	public Collection<Offer> findOffersByTrainer(final Trainer trainer) {
		return trainer.getOffers();
	}

	public Offer findOfferAccepted(final Request request) {
		return this.offerRepository.findAcceptedOffer(request.getId());
	}

	public Collection<Offer> findNonAcceptedOffers(final Request request) {
		return this.offerRepository.findNonAcceptedOffers(request.getId());
	}

	//Erase all offers not accepted that are linked to a request which has already an accepted offer
	public void eraseNonAcceptedOffers(final Request request) {
		final Offer acceptedOffer = this.findOfferAccepted(request);
		Assert.notNull(acceptedOffer);

		final Collection<Offer> nonAcceptedOffers = this.findNonAcceptedOffers(request);
		if (!nonAcceptedOffers.isEmpty())
			this.offerRepository.delete(nonAcceptedOffers);

	}

	public Offer reconstruct(final OfferForm offerForm, final BindingResult binding) throws IOException {
		Assert.notNull(offerForm);
		Offer result;

		if (offerForm.getId() == 0)
			if (offerForm.getRequest().getAnimal() == null)
				result = this.createWithoutAnimal(offerForm.getRequest());
			else
				result = this.createWithAnimal(offerForm.getRequest());
		else
			result = this.findOne(offerForm.getId());

		result.setDestination(offerForm.getDestination());
		result.setStartMoment(offerForm.getStartMoment());
		result.setPrice(offerForm.getPrice());
		result.setComment(offerForm.getComment());
		result.setDuration(offerForm.getDuration());
		result.setAnimal(offerForm.getAnimal());
		result.setRequest(offerForm.getRequest());

		return result;
	}

	public OfferForm offerToFormObject(final Offer offer) {
		Assert.notNull(offer);
		final OfferForm result = new OfferForm();

		result.setId(offer.getId());
		result.setDestination(offer.getDestination());
		result.setStartMoment(offer.getStartMoment());
		result.setPrice(offer.getPrice());
		result.setComment(offer.getComment());
		result.setDuration(offer.getDuration());
		result.setAnimal(offer.getAnimal());
		result.setRequest(offer.getRequest());

		return result;
	}
}
