
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OfferRepository;
import repositories.RequestRepository;
import domain.Offer;
import domain.Request;
import domain.Trainer;

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

	public Offer create(final Request request) {
		Offer result;
		Trainer principal;

		principal = this.trainerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Offer();
		result.setTrainer(principal);
		result.setRequest(request);

		if (request.getAnimal() != null)	//Si el cliente ya ha seleccionado un animal
			result.setAnimal(request.getAnimal());

		//Comprobar de que no tiene ninguna oferta aceptada
		Assert.isTrue(this.requestRepository.findOfferWithThisRequestTrue(request.getId()).equals(null));

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
}
