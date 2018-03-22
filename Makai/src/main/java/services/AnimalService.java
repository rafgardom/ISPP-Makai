
package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import repositories.AnimalRepository;
import security.Authority;
import domain.Actor;
import domain.Animal;
import domain.AnimalShelter;
import domain.Breed;
import domain.Customer;
import forms.AnimalForm;

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

	@Autowired
	private Validator				validator;


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

	// Other business methods -------------------------------------------------

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(20971520);   // 20MB
		multipartResolver.setMaxInMemorySize(1048576);  // 1MB
		return multipartResolver;
	}

	public Animal reconstruct(final AnimalForm animalForm, final BindingResult binding) throws IOException {

		Assert.notNull(animalForm);

		Animal result;

		if (animalForm.getId() == 0 && animalForm.getAnimalImage().getSize() == 0) {
			FieldError fieldError;
			final String[] codes = {
				"animal.picture.empty.error"
			};
			fieldError = new FieldError("animalForm", "animalImage", animalForm.getAnimalImage(), false, codes, null, "");
			binding.addError(fieldError);

		} else if (animalForm.getAnimalImage().getSize() > 5242880) {
			FieldError fieldError;
			final String[] codes = {
				"animal.picture.tooLong.error"
			};
			fieldError = new FieldError("animalForm", "animalImage", animalForm.getAnimalImage(), false, codes, null, "");
			binding.addError(fieldError);
		}

		if (!animalForm.getAnimalImage().getContentType().contains("image")) {
			FieldError fieldError;
			final String[] codes = {
				"animal.picture.extension.error"
			};
			fieldError = new FieldError("animalForm", "animalImage", animalForm.getAnimalImage(), false, codes, null, "");
			binding.addError(fieldError);
		}

		if (animalForm.getId() == 0)
			result = this.create();
		else
			result = this.findOne(animalForm.getId());

		result.setName(animalForm.getName());
		result.setChipNumber(animalForm.getChipNumber());
		result.setAge(animalForm.getAge());
		result.setSex(animalForm.getSex());
		result.setBreeds(animalForm.getBreeds());

		if (animalForm.getPicture() != null)
			result.setPicture(animalForm.getPicture());

		this.validator.validate(result, binding);

		return result;

	}
	public AnimalForm animalToFormObject(final Animal animal) {
		final AnimalForm result;
		byte[] base64;
		StringBuilder imageString;
		String image;

		Assert.notNull(animal);

		result = new AnimalForm();

		if (animal.getPicture() != null) {
			base64 = Base64.encode(animal.getPicture());
			imageString = new StringBuilder();
			imageString.append("data:image/png;base64,");
			imageString.append(new String(base64));
			image = imageString.toString();
		} else
			image = null;

		result.setId(animal.getId());
		result.setName(animal.getName());
		result.setChipNumber(animal.getChipNumber());
		result.setAge(animal.getAge());
		result.setSex(animal.getSex());
		result.setPicture(animal.getPicture());
		result.setBreeds(animal.getBreeds());
		result.setStringImage(image);

		return result;
	}

	public Collection<Animal> findByActorId(final int actorId) {
		Collection<Animal> animals;

		animals = this.animalRepository.findByActorId(actorId);

		return animals;
	}

	public Collection<Animal> findAnimalFromAnimalShelter() {
		return this.animalRepository.findAnimalFromAnimalShelter();
	}
}
