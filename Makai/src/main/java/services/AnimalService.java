
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AnimalRepository;
import security.Authority;
import domain.Actor;
import domain.Animal;
import domain.AnimalShelter;
import domain.Breed;
import domain.Customer;

@Service
@Transactional
public class AnimalService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AnimalRepository		animalRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private AnimalShelterService	animalShelterService;

	@Autowired
	private CustomerService			customerService;


	// Constructors------------------------------------------------------------
	public AnimalService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Animal findOne(final int animalId) {
		Animal result;

		result = this.animalRepository.findOne(animalId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Animal> findAll() {
		Collection<Animal> result;

		result = this.animalRepository.findAll();

		return result;
	}

	public Animal create() {
		Animal result;
		Actor principal;
		AnimalShelter animalShelter;
		Customer customer;
		Collection<Breed> breeds;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.CUSTOMER) || this.actorService.checkAuthority(principal, Authority.ANIMALSHELTER));

		result = new Animal();
		if (this.actorService.checkAuthority(principal, Authority.ANIMALSHELTER)) {
			animalShelter = this.animalShelterService.findByPrincipal();
			Assert.notNull(animalShelter);
			result.setAnimalShelter(animalShelter);
		} else if (this.actorService.checkAuthority(principal, Authority.CUSTOMER)) {
			customer = this.customerService.findByPrincipal();
			Assert.notNull(customer);
			result.setCustomer(customer);
		}

		breeds = new ArrayList<Breed>();
		result.setBreeds(breeds);
		result.setIsHidden(false);

		return result;
	}
	public Animal save(final Animal animal) {
		Assert.notNull(animal);
		Animal result;
		Actor principal;
		AnimalShelter animalShelter;
		Customer customer;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.CUSTOMER) || this.actorService.checkAuthority(principal, Authority.ANIMALSHELTER));
		if (this.actorService.checkAuthority(principal, Authority.ANIMALSHELTER)) {
			animalShelter = this.animalShelterService.findByPrincipal();
			Assert.notNull(animalShelter);
			Assert.isTrue(animal.getAnimalShelter().getId() == animalShelter.getId());
		} else if (this.actorService.checkAuthority(principal, Authority.CUSTOMER)) {
			customer = this.customerService.findByPrincipal();
			Assert.notNull(customer);
			Assert.isTrue(animal.getCustomer().getId() == customer.getId());
		}

		//Comprobar de que no haya una request asociada a el

		result = this.animalRepository.save(animal);

		return result;
	}
	public void delete(final Animal animal) {
		Actor principal;
		AnimalShelter animalShelter;
		Customer customer;

		Assert.notNull(animal);
		Assert.isTrue(animal.getId() != 0);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(this.actorService.checkAuthority(principal, Authority.CUSTOMER) || this.actorService.checkAuthority(principal, Authority.ANIMALSHELTER));
		if (this.actorService.checkAuthority(principal, Authority.ANIMALSHELTER)) {
			animalShelter = this.animalShelterService.findByPrincipal();
			Assert.notNull(animalShelter);
			Assert.isTrue(animal.getAnimalShelter().getId() == animalShelter.getId());
		} else if (this.actorService.checkAuthority(principal, Authority.CUSTOMER)) {
			customer = this.customerService.findByPrincipal();
			Assert.notNull(customer);
			Assert.isTrue(animal.getCustomer().getId() == customer.getId());
		}

		//Cambiamos el atributo isHidden a true cuando el animal es eliminado
		animal.setIsHidden(true);
		this.save(animal);
	}
}
