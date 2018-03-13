
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import domain.Coordinates;
import domain.Customer;
import forms.CustomerForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CustomerServiceTest {

	//Services under test
	@Autowired
	private CustomerService	customerService;


	@Test
	public void create() {
		final CustomerForm customerForm = this.customerService.createForm();

		customerForm.setAcceptCondition(true);

		final Coordinates coordinates = new Coordinates();
		coordinates.setCity("City");
		coordinates.setCountry("Country");
		coordinates.setProvince("Province");
		coordinates.setState("State");
		coordinates.setZip_code("zip code");

		customerForm.setCoordinates(coordinates);
		customerForm.setEmail("testing@test.com");
		customerForm.setName("name");
		customerForm.setNid("nid");
		customerForm.setPhone("phone");
		customerForm.setPassword("password");

		final Byte[] picture = {
			new Byte((byte) 2), new Byte((byte) 3)
		};
		customerForm.setPicture(picture);
		customerForm.setRepeatPassword("password");
		customerForm.setSurname("surname");

		final Customer customer = this.customerService.reconstruct(customerForm, null);
		this.customerService.save(customer);

	}
}
