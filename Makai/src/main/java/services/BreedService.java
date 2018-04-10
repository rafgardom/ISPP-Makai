
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BreedRepository;
import domain.Administrator;
import domain.Breed;
import domain.Specie;

@Service
@Transactional
public class BreedService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private BreedRepository			breedRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SpecieService			specieService;


	// Constructors------------------------------------------------------------
	public BreedService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Breed findOne(final int breedId) {
		Breed result;

		result = this.breedRepository.findOne(breedId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Breed> findAll() {
		Collection<Breed> result;

		result = this.breedRepository.findAll();

		return result;
	}

	public Breed create(final Specie specie) {
		Breed result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Breed();
		result.setSpecie(specie);

		return result;
	}

	public Breed create() {
		Breed result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Breed();

		return result;
	}

	public Breed save(final Breed breed) {
		Assert.notNull(breed);
		Breed result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.breedRepository.save(breed);

		return result;
	}

	public void delete(final Breed breed) {
		Administrator principal;

		Assert.notNull(breed);
		Assert.isTrue(breed.getId() != 0);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		//Comprobar de que ningun Animal tiene asociada a esta Breed
		Assert.isTrue(!this.tieneBreedUnAnimal(breed));

		this.breedRepository.delete(breed);
	}

	public Boolean tieneBreedUnAnimal(final Breed breed) {
		Boolean res = true;

		if (this.breedRepository.findAnimalWithThisBreed(breed.getName()).isEmpty())
			res = false;

		return res;
	}
}
