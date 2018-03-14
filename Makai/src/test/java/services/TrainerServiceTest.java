
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import utilities.AbstractTest;
import domain.Coordinates;
import domain.Trainer;
import forms.TrainerForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class TrainerServiceTest extends AbstractTest {

	//Services under test
	@Autowired
	private TrainerService	trainerService;


	//Tests 
	@Test
	public void create() {
		final TrainerForm trainerForm = this.trainerService.createForm();

		trainerForm.setAcceptCondition(true);

		final Coordinates coordinates = new Coordinates();
		coordinates.setCity("City");
		coordinates.setCountry("Country");
		coordinates.setProvince("Province");
		coordinates.setState("State");
		coordinates.setZip_code("zip code");

		trainerForm.setCoordinates(coordinates);
		trainerForm.setEmail("testing@test.com");
		trainerForm.setName("name");
		trainerForm.setNid("nid");
		trainerForm.setPhone("phone");
		trainerForm.setPassword("password");

		final byte[] picture = {
			new Byte((byte) 2), new Byte((byte) 3)
		};
		trainerForm.setPicture(picture);
		trainerForm.setRepeatPassword("password");
		trainerForm.setSurname("surname");

		final Trainer trainer = this.trainerService.reconstruct(trainerForm, null);
		this.trainerService.save(trainer);
	}
}
