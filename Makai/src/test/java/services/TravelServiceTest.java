
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TravelServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ActorService	actorService;

	@Autowired
	private TravelService	travelService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driverCreateTravel() {
		final Object testingData[][] = {
			{
				"customer1", "vehicle3", "SpainOrigen", "AndaluciaOrigen", "CadizOrigen", "CadizOrigen", "123654", "SpainDestino", "AndaluciaDestino", "SevillaDestino", "SevillaDestino", "04/20/2018 9:00", "05/20/2018 9:00", 1, 2, null
			},
			{
				"customer1", "vehicle3", "SpainOrigen", "AndaluciaOrigen", "CadizOrigen", "CadizOrigen", "123654", "SpainDestino", "AndaluciaDestino", "SevillaDestino", "SevillaDestino", "04/20/2018 9:00", "05/20/2018 9:00", 1, 2, null
			},
			{
				"professional1", "vehicle2", "SpainOrigen", "AndaluciaOrigen", "CadizOrigen", "CadizOrigen", "123654", "SpainDestino", "AndaluciaDestino", "SevillaDestino", "SevillaDestino", "04/20/2018 9:00", "05/20/2018 9:00", 1, 2, null
			},
			{
				"professional1", "vehicle1", "SpainOrigen", "AndaluciaOrigen", "CadizOrigen", "CadizOrigen", "123654", "SpainDestino", "AndaluciaDestino", "SevillaDestino", "SevillaDestino", "04/20/2018 9:00", "05/20/2018 9:00", 1, 2, null
			},
			{
				"customer1", "vehicle3", " ", "AndaluciaOrigen", "CadizOrigen", "CadizOrigen", "SpainDestino", "123654", "AndaluciaDestino", "SevillaDestino", "SevillaDestino", "04/20/2018 9:00", "05/20/2018 9:00", 1, 2, ConstraintViolationException.class
			},
			{
				"customer2", "vehicle3", "SpainOrigen", "AndaluciaOrigen", "CadizOrigen", "CadizOrigen", "123654", "SpainDestino", "AndaluciaDestino", "SevillaDestino", "SevillaDestino", "04/20/2018 9:00", "05/20/2018 9:00", 1, 2,
				IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createTravel((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (int) testingData[i][13], (int) testingData[i][14], (Class<?>) testingData[i][15]);
	}
	protected void createTravel(final String username, final String vehicle, final String paisOrigin, final String estadoOrigen, final String provinciaOrigen, final String ciudadOrigen, final String zipCode, final String paisDestino,
		final String estadoDestino, final String provinciaDestino, final String ciudadDestino, final String fechaSalida, final String fechaLlegada, final int asientosAnimales, final int asientosPersonas, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
