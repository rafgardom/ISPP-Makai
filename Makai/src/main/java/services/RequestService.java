
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Customer;
import domain.Receipt;
import domain.Request;

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
	private OfferService		offerService;


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

		//Comprobar de que no tiene ninguna oferta ¿aceptada?
		Assert.isTrue(!this.tieneOfferUnRequest(request));

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

		//Comprobar de que no tiene ninguna oferta aceptada
		Assert.isTrue(!this.tieneOfferUnRequest(request));

		this.requestRepository.delete(request);
	}

	public Boolean tieneOfferUnRequest(final Request request) {
		Boolean res = true;

		if (this.requestRepository.findOfferWithThisRequest(request.getId()).isEmpty())
			res = false;

		return res;
	}
}
