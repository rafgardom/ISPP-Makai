
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
import org.springframework.validation.FieldError;

import repositories.OfferRepository;
import domain.Actor;
import domain.Animal;
import domain.Customer;
import domain.Notification;
import domain.NotificationType;
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

	// Supporting services ----------------------------------------------------

	@Autowired
	private RequestService		requestService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private TrainerService		trainerService;

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private AnimalService		animalService;


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
		Assert.isNull(this.findOfferAccepted(request));

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
		Assert.isNull(this.findOfferAccepted(request));

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

		if (offer.getPrice() == null)
			offer.setPrice(0.0);

		if (offer.getId() == 0)
			this.notificationService.createNotificationForRequestWithOffer(offer.getRequest());

		/* Comprobar que el animal no ha sido adoptado por otro customer */
		Assert.isTrue(this.elAnimalNoHaSidoAdoptado(offer.getAnimal()));

		result = this.offerRepository.save(offer);

		return result;
	}

	public void delete(final Offer offer) {
		Actor principal;

		Assert.notNull(offer);
		Assert.isTrue(offer.getId() != 0);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(this.actorService.checkAuthority(principal, "CUSTOMER") || this.actorService.checkAuthority(principal, "ANIMALSHELTER") || (this.actorService.checkAuthority(principal, "TRAINER") && offer.getTrainer().getId() == principal.getId()));

		Assert.isTrue(!offer.getIsAccepted());

		this.offerRepository.delete(offer);
	}

	//Other business service

	public Collection<Offer> findOffersByTrainer(final Trainer trainer) {
		return trainer.getOffers();
	}

	public Offer findOfferAccepted(final Request request) {
		Offer offer;
		offer = this.offerRepository.findAcceptedOffer(request.getId());
		return offer;
	}
	public Collection<Offer> findNonAcceptedOffers(final Request request) {
		return this.offerRepository.findNonAcceptedOffers(request.getId());
	}

	public Collection<Offer> findAcceptedOffersByCustomer(final Customer customer) {
		return this.offerRepository.findAcceptedOffersByCustomer(customer.getId());
	}

	public Collection<Offer> findAcceptedOffersPendingReceipts(final Customer customer) {
		Collection<Offer> offers;
		Collection<Offer> result;

		result = new ArrayList<Offer>();
		offers = this.offerRepository.findAcceptedOffersByCustomer(customer.getId());
		for (final Offer o : offers)
			if (this.requestService.findRequestAcceptedPendingReceipts(o.getRequest()) != null)
				result.add(o);
		return result;
	}

	//Erase all offers not accepted that are linked to a request which has already an accepted offer
	public void eraseNonAcceptedOffers(final Request request) {
		final Offer acceptedOffer = this.findOfferAccepted(request);
		Assert.notNull(acceptedOffer);

		final Collection<Offer> nonAcceptedOffers = this.findNonAcceptedOffers(request);
		if (!nonAcceptedOffers.isEmpty())
			this.offerRepository.delete(nonAcceptedOffers);

	}

	public void eraseOffersWhenRequestIsDeleted(final Request request) {
		Assert.notNull(request);
		final Offer acceptedOffer = this.findOfferAccepted(request);
		Assert.isNull(acceptedOffer);

		final Collection<Offer> nonAcceptedOffers = this.findNonAcceptedOffers(request);
		if (!nonAcceptedOffers.isEmpty())
			this.offerRepository.delete(nonAcceptedOffers);

	}

	public void acceptedOffer(Offer offer) {
		Assert.notNull(offer);
		Customer customer;

		customer = this.customerService.findByPrincipal();
		Assert.isTrue(customer.getId() == offer.getRequest().getCustomer().getId());

		offer.setIsAccepted(true);
		offer = this.offerRepository.save(offer);

		this.notificationService.createNotificationOfferAcceptedTrainer(offer);

		this.eraseNonAcceptedOffers(offer.getRequest());

		/* Eliminamos las ofertas no aceptadas que tengan el animal */
		this.eliminatedOffersWithThisAnimal(offer);

		/* Modificamos la fecha de finalizacion del entrenamiento en la entidad Animal */
		this.animalService.editFinishTraining(offer);
	}

	public Offer reconstruct(final OfferForm offerForm, final BindingResult binding) throws IOException {
		Assert.notNull(offerForm);
		Offer result;

		if (offerForm.getDuration().getDay() == null && offerForm.getDuration().getMonth() == null && offerForm.getDuration().getYear() == null) {
			FieldError fieldError;
			final String[] codes = {
				"offer.duration.empty.error"
			};
			fieldError = new FieldError("offerForm", "duration", offerForm.getDuration(), false, codes, null, "");
			binding.addError(fieldError);
		}
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

	public Collection<Offer> findOfferByRequest(final Request request) {
		return this.offerRepository.findOfferByRequestId(request.getId());
	}

	public Collection<Animal> findAnimalWithOfferAccept() {
		return this.offerRepository.findOfferAccept();
	}

	public Collection<Offer> findNotAcceptedOffersByAnimalId(final int animalId) {
		return this.offerRepository.findNotAcceptedOffersByAnimalId(animalId);
	}

	public Date calculateWhenFinishOffer(final Offer offer) {
		Assert.notNull(offer);

		final Calendar comienzo = Calendar.getInstance();
		comienzo.setTime(offer.getStartMoment());

		/* Añadimos la duracion */
		comienzo.add(Calendar.DAY_OF_MONTH, offer.getDuration().getDay());
		comienzo.add(Calendar.MONTH, offer.getDuration().getMonth());
		comienzo.add(Calendar.YEAR, offer.getDuration().getYear());

		return comienzo.getTime();

	}

	public Collection<Offer> offersAccepted() {
		return this.offerRepository.offersAccepted();
	}

	public Boolean elAnimalNoHaSidoAdoptado(final Animal animal) {
		Boolean result = true;
		if (animal.getFinishTraining() != null)
			if (animal.getFinishTraining().after(Calendar.getInstance().getTime()))
				result = false;
		return result;
	}

	public void eliminatedOffersWithThisAnimal(final Offer offer) {
		final Collection<Offer> ofertasRelacionadas = this.findNotAcceptedOffersByAnimalId(offer.getAnimal().getId());
		Notification notification;

		for (final Offer o : ofertasRelacionadas) {
			notification = this.notificationService.create(o.getTrainer());
			notification.setReason("Lo sentimos, pero " + offer.getAnimal().getName() + " ya no está disponible");
			notification.setDescription("El animal que intentaba solicitar ya no se encuentra disponible");
			notification.setType(NotificationType.GENERAL);
			this.notificationService.save(notification);
			this.delete(o);
		}
	}

}
