
package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import domain.Customer;
import domain.Receipt;
import domain.Request;
import forms.RequestForm;

@Service
@Transactional
public class RequestService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RequestRepository	requestRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ReceiptService		receiptService;

	@Autowired
	private OfferService		offerService;

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private Validator			validator;


	// Constructors------------------------------------------------------------
	public RequestService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Request findOne(final int requestId) {
		Request result;

		result = this.requestRepository.findOne(requestId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Request> findAll() {
		Collection<Request> result;

		result = this.requestRepository.findAll();

		return result;
	}

	public Request create() {
		Request result;
		Customer principal;
		Collection<Receipt> receipts;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Request();
		result.setCustomer(principal);
		result.setIsCancelled(false);

		receipts = new ArrayList<Receipt>();
		result.setReceipts(receipts);

		return result;
	}

	public Request save(final Request request) {
		Assert.notNull(request);
		Request result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(request.getCustomer().getId() == principal.getId());

		//Comprobar de que no tiene ninguna oferta
		Assert.isTrue(!this.tieneOfferUnRequest(request));

		//SOLO PARA EL CREATE
		this.notificationService.createNotificationToTrainerWithTraining(request);

		result = this.requestRepository.save(request);

		return result;
	}

	public void delete(final Request request) {
		Customer principal;

		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(request.getCustomer().getId() == principal.getId());

		Assert.isTrue(request.getIsCancelled() == false);

		//Comprobar de que no tiene ninguna oferta aceptada
		if (this.offerService.findOfferAccepted(request) != null) {
			Assert.isTrue(this.receiptService.getPendingReceipts(principal).isEmpty());
			request.setIsCancelled(true);
			this.requestRepository.save(request);
		} else {
			this.offerService.eraseOffersWhenRequestIsDeleted(request);
			this.requestRepository.delete(request);
		}

	}

	// Other business methods -------------------------------------------------

	public Boolean tieneOfferUnRequest(final Request request) {
		Boolean res = true;

		if (this.requestRepository.findOfferWithThisRequest(request.getId()).isEmpty())
			res = false;

		return res;
	}

	public Boolean tieneOfferAceptadaUnRequest(final Request request) {
		Boolean res = true;

		if (this.requestRepository.findOfferWithThisRequestTrue(request.getId()) == null)
			res = false;

		return res;
	}

	public Collection<Request> findRequestsNotAccepted() {
		Collection<Request> result;
		Collection<Request> aceptedRequests;

		result = this.findAll();
		aceptedRequests = this.requestRepository.findRequestsAccepted();
		result.removeAll(aceptedRequests);

		return result;
	}

	public Collection<Request> findRequestByCustomer(final Customer customer) {
		return this.requestRepository.findRequestsByCustomer(customer.getId());
	}

	public Request findRequestAcceptedPendingReceipts(final Request request) {
		return this.requestRepository.findRequestAcceptedPendingReceipts(request.getId());
	}

	public Collection<Request> findRequestsWithOffer() {
		return this.requestRepository.findRequestsWithOffer();
	}

	public Request reconstruct(final RequestForm requestForm, final BindingResult binding) throws IOException {

		Assert.notNull(requestForm);

		Request result;

		if (requestForm.getId() == 0)
			result = this.create();
		else
			result = this.findOne(requestForm.getId());

		result.setDescription(requestForm.getDescription());
		result.setTags(requestForm.getTags());
		result.setCategory(requestForm.getCategory());
		result.setAnimal(requestForm.getAnimal());

		this.validator.validate(result, binding);

		return result;

	}

	public RequestForm requestToFormObject(final Request request) {
		RequestForm result;

		Assert.notNull(request);

		result = new RequestForm();

		result.setId(request.getId());
		result.setDescription(request.getDescription());
		result.setTags(request.getTags());
		result.setCategory(request.getCategory());
		result.setAnimal(request.getAnimal());

		return result;
	}

}
