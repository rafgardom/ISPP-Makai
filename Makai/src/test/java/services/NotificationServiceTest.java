
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Actor;
import domain.Notification;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class NotificationServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private ActorService		actorService;


	// Tests ------------------------------------------------------------------
	@Test
	public void driveCreateNotification() {
		final Object testingData[][] = {
			{
				"customer1", "Reason Test", "Descripcion1", null
			}, {
				"customer2", "Reason Test", "Descripcion2", null
			}, {
				"trainer1", "Reason Test", "Descripcion3", null
			}, {
				"trainer1", " ", "Descripcion3", ConstraintViolationException.class
			}, {
				"trainer1", " ", " ", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createNotification((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void createNotification(final String username, final String reason, final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Notification notification;
			final Collection<Actor> actors = new ArrayList<Actor>();
			actors.add(this.actorService.findByPrincipal());

			notification = this.notificationService.create(actors);
			notification.setReason(reason);
			notification.setDescription(description);

			this.notificationService.save(notification);

			this.notificationService.findAll();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
