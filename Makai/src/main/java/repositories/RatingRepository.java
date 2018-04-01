
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

	@Query("select count(r) from Rating r where r.stars=0 and r.trainer.id=?1")
	public Integer count0starsByTrainerId(int trainerId);

	@Query("select count(r) from Rating r where r.stars=0 and r.travel.id=?1")
	public Integer count0starsByTravelId(int travelId);

	@Query("select r from Rating r where r.travel.id=?1 and r.customer.id=?2")
	public Rating findRatingByCustomerFromTravel(int travelId, int customerId);

	@Query("select r from Rating r where r.customer.id=?1 and r.travel != null")
	public Collection<Rating> findTravelRatingByCustomer(int customerId);
}
