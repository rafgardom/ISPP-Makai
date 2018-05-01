
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PriceRepository;
import domain.Administrator;
import domain.Price;

@Service
@Transactional
public class PriceService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PriceRepository			priceRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


	// Constructors------------------------------------------------------------
	public PriceService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Price findOne(final int priceId) {
		Price result;

		result = this.priceRepository.findOne(priceId);
		Assert.notNull(result);

		return result;
	}

	public Price findOne() {
		List<Price> prices;
		Price result;

		prices = this.priceRepository.findAll();
		result = prices.get(0);

		return result;
	}

	public Collection<Price> findAll() {
		Collection<Price> result;

		result = this.priceRepository.findAll();

		return result;
	}

	public Price save(final Price price) {
		Assert.notNull(price);
		Price result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.priceRepository.save(price);

		return result;
	}

}
