
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Rating;
import domain.Request;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RatingServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private RatingService	ratingService;

	@Autowired
	private RequestService	requestService;


	// Tests ------------------------------------------------------------------
	@Test
	public void driveCreateNotification() {
		final Object testingData[][] = {
			{
				"customer1", 111, "Descripcion1", 0, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createRating((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void createRating(final String username, final Integer requestId, final String comment, final Integer stars, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Rating rating;
			Request request;

			request = this.requestService.findOne(requestId);

			rating = this.ratingService.createToRequest(request);
			rating.setComment(comment);
			rating.setStars(stars);

			this.ratingService.save(rating);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
