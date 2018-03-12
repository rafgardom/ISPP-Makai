
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReceiptRepository;
import domain.Customer;
import domain.Receipt;
import domain.Request;

@Service
@Transactional
public class ReceiptService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ReceiptRepository	receiptRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private CustomerService		customerService;

	@Autowired
	private OfferService		offerService;


	// Constructors------------------------------------------------------------
	public ReceiptService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Receipt findOne(final int receiptId) {
		Receipt result;

		result = this.receiptRepository.findOne(receiptId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Receipt> findAll() {
		Collection<Receipt> result;

		result = this.receiptRepository.findAll();

		return result;
	}

	public Receipt create(final Request request) {
		Receipt result;
		Customer principal;
		final Collection<Receipt> receipts;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Receipt();
		Assert.isTrue(request.getCustomer().getId() == principal.getId());

		result.setRequest(request);

		//Mostrar la Oferta aceptada de una Request dada

		return result;
	}

	public Receipt save(final Receipt receipt) {
		Assert.notNull(receipt);
		Receipt result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.receiptRepository.save(receipt);

		return result;
	}

}
