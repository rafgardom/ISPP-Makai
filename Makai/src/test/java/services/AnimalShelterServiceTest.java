
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import utilities.AbstractTest;
import domain.AnimalShelter;
import domain.Coordinates;
import forms.AnimalShelterForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class AnimalShelterServiceTest extends AbstractTest {

	//Services under test
	@Autowired
	private AnimalShelterService	animalShelterService;


	//Tests 
	@Test
	public void create() {
		final AnimalShelterForm animalShelterForm = this.animalShelterService.createForm();

		animalShelterForm.setAcceptCondition(true);

		final Coordinates coordinates = new Coordinates();
		coordinates.setCity("City");
		coordinates.setCountry("Country");
		coordinates.setProvince("Province");
		coordinates.setState("State");
		coordinates.setZip_code("zip code");

		animalShelterForm.setCoordinates(coordinates);
		animalShelterForm.setEmail("testing@test.com");
		animalShelterForm.setName("name");
		animalShelterForm.setPhone("phone");
		animalShelterForm.setPassword("password");

		final byte[] picture = {
			new Byte((byte) 2), new Byte((byte) 3)
		};
		animalShelterForm.setPicture(picture);
		animalShelterForm.setRepeatPassword("password");

		final AnimalShelter animalShelter = this.animalShelterService.reconstruct(animalShelterForm, null);
		this.animalShelterService.save(animalShelter);

	}
}
