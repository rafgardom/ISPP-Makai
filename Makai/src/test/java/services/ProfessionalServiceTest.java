
package services;

import java.io.IOException;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import utilities.AbstractTest;
import domain.Coordinates;
import domain.Professional;
import forms.ProfessionalForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ProfessionalServiceTest extends AbstractTest {

	//Services under test
	@Autowired
	private ProfessionalService	professionalService;


	//Tests 
	@Test
	public void create() throws IOException {
		final ProfessionalForm professionalForm = this.professionalService.createForm();

		professionalForm.setAcceptCondition(true);

		final Coordinates coordinates = new Coordinates();
		coordinates.setCity("City");
		coordinates.setCountry("Country");
		coordinates.setProvince("Province");
		coordinates.setState("State");
		coordinates.setZip_code("zip code");

		professionalForm.setCoordinates(coordinates);
		professionalForm.setEmail("testing@test.com");
		professionalForm.setName("name");
		professionalForm.setPhone("phone");
		professionalForm.setPassword("password");

		final byte[] picture = {
			new Byte((byte) 2), new Byte((byte) 3)
		};
		professionalForm.setPicture(picture);
		professionalForm.setRepeatPassword("password");

		final Professional professional = this.professionalService.reconstruct(professionalForm, null);
		this.professionalService.save(professional);

	}
}
