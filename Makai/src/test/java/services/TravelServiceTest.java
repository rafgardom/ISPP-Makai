
package services;

import java.util.Calendar;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Coordinates;
import domain.Travel;
import domain.Vehicle;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TravelServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private TravelService		travelService;

	@Autowired
	private VehicleService		vehicleService;

	@Autowired
	private TransporterService	transporterService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driverCreateTravel() {
		final Object testingData[][] = {
			{
				"customer1", 123, "SpainOrigen", "AndaluciaOrigen", "CadizOrigen", "CadizOrigen", "123654", "SpainDestino", "AndaluciaDestino", "SevillaDestino", "SevillaDestino", "04/20/2018 9:00", 90, 1, 2, null
			}

			, {
				"customer1", 123, " ", "AndaluciaOrigen1", "CadizOrigen1", "CadizOrigen1", "123254", "SpainDestino1", "AndaluciaDestino1", "SevillaDestino1", "SevillaDestino1", "05/20/2018 9:00", 90, 2, 2, ConstraintViolationException.class
			}
		/*
		 * , {
		 * "professional1", 122, "SpainOrigen", "AndaluciaOrigen", "CadizOrigen", "CadizOrigen", "123654", "SpainDestino", "AndaluciaDestino", "SevillaDestino", "SevillaDestino", "04/20/2018 9:00", "05/20/2018 9:00", 1, 2, null
		 * }, {
		 * "professional1", 121, "SpainOrigen", "AndaluciaOrigen", "CadizOrigen", "CadizOrigen", "123654", "SpainDestino", "AndaluciaDestino", "SevillaDestino", "SevillaDestino", "04/20/2018 9:00", "05/20/2018 9:00", 1, 2, null
		 * }, {
		 * "customer1", 123, " ", "AndaluciaOrigen", "CadizOrigen", "CadizOrigen", "SpainDestino", "123654", "AndaluciaDestino", "SevillaDestino", "SevillaDestino", "04/20/2018 9:00", "05/20/2018 9:00", 1, 2, ConstraintViolationException.class
		 * }, {
		 * "customer2", 123, "SpainOrigen", "AndaluciaOrigen", "CadizOrigen", "CadizOrigen", "123654", "SpainDestino", "AndaluciaDestino", "SevillaDestino", "SevillaDestino", "04/20/2018 9:00", "05/20/2018 9:00", 1, 2, IllegalArgumentException.class
		 * }
		 */
		};

		for (int i = 0; i < testingData.length; i++)
			this.createTravel((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (int) testingData[i][12], (int) testingData[i][13], (int) testingData[i][14], (Class<?>) testingData[i][15]);
	}
	protected void createTravel(final String username, final int vehicle, final String paisOrigin, final String estadoOrigen, final String provinciaOrigen, final String ciudadOrigen, final String zipCode, final String paisDestino,
		final String estadoDestino, final String provinciaDestino, final String ciudadDestino, final String fechaSalida, final int duracion, final int asientosAnimales, final int asientosPersonas, final Class<?> expected) {
		Class<?> caught;

		final Coordinates coordinateOrigen = new Coordinates();
		final Coordinates coordinateDestino = new Coordinates();

		/* Moment salida */
		String[] dateSalida = null;
		String[] timeSalida = null;

		/* Moment llegada */
		final String[] dateLlegada = null;
		final String[] timeLlegada = null;

		final Calendar calendarSalida = Calendar.getInstance();

		caught = null;
		try {
			this.authenticate(username);

			final Travel travel = this.travelService.create();

			/* Coordenadas origen */
			coordinateOrigen.setCountry(paisOrigin);
			coordinateOrigen.setState(estadoOrigen);
			coordinateOrigen.setProvince(provinciaOrigen);
			coordinateOrigen.setZip_code(zipCode);

			/* Coordenadas destino */
			coordinateDestino.setCountry(paisDestino);
			coordinateDestino.setState(estadoDestino);
			coordinateDestino.setProvince(provinciaDestino);
			coordinateDestino.setZip_code(zipCode);

			/* Start Moment */
			dateSalida = fechaSalida.split(" ");
			timeSalida = dateSalida[1].split(":");
			dateSalida = dateSalida[0].split("/");
			calendarSalida.set(Integer.parseInt(dateSalida[2]), Integer.parseInt(dateSalida[1]), Integer.parseInt(dateSalida[0]), Integer.parseInt(timeSalida[0]), Integer.parseInt(timeSalida[1]));

			/* Duration */

			/* Vehiculo */
			Vehicle vehiculo = null;
			for (final Vehicle aux : this.vehicleService.findAll())
				if (aux.getId() == vehicle)
					vehiculo = aux;

			travel.setOrigin(coordinateOrigen);
			travel.setDestination(coordinateDestino);
			travel.setStartMoment(calendarSalida.getTime());
			travel.setDuration(duracion);
			travel.setAnimalSeats(asientosAnimales);
			travel.setHumanSeats(asientosPersonas);
			travel.setVehicle(vehiculo);

			this.travelService.save(travel);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
