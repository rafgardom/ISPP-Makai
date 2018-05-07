
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SpecieRepository;
import domain.Administrator;
import domain.Specie;

@Service
@Transactional
public class SpecieService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SpecieRepository		specieRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


	// Constructors------------------------------------------------------------
	public SpecieService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Specie findOne(final int specieId) {
		Specie result;

		result = this.specieRepository.findOne(specieId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Specie> findAll() {
		Collection<Specie> result;

		result = this.specieRepository.findAll();

		return result;
	}

	public Specie create() {
		Specie result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Specie();

		return result;
	}

	public Specie save(final Specie specie) {
		Assert.notNull(specie);
		Specie result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.specieRepository.save(specie);

		return result;
	}

	public void delete(final Specie specie) {
		Administrator principal;

		Assert.notNull(specie);
		Assert.isTrue(specie.getId() != 0);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		//Comprobar de que ning�n Animal tiene asociada ninguna Breed de esta Specie
		Assert.isTrue(!this.tieneBreedUnaSpecie(specie));

		this.specieRepository.delete(specie);
	}

	public Boolean tieneBreedUnaSpecie(final Specie specie) {
		Boolean res = true;

		if (this.specieRepository.findBreedWithThisSpecie(specie.getTypeSpa()).isEmpty())
			res = false;

		return res;
	}
}
