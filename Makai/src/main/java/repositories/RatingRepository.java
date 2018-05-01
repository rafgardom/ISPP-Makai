
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

	@Query("select count(r) from Rating r where r.stars=1 and r.trainer.id=?1")
	public Integer count0starsByTrainerId(int trainerId);

	@Query("select count(r) from Rating r where r.stars=1 and r.travel.id=?1")
	public Integer count0starsByTravelId(int travelId);

	@Query("select r from Rating r where r.travel.id=?1 and r.customer.id=?2")
	public Rating findRatingByCustomerFromTravel(int travelId, int customerId);

	@Query("select r from Rating r where r.customer.id=?1 and r.travel != null")
	public Collection<Rating> findTravelRatingByCustomer(int customerId);

	@Query("select r from Rating r where r.travel!=null and r.travel.transporterOwner.id=?1")
	public Collection<Rating> findByTransporterId(int trainerId);

	@Query("select r from Rating r where r.trainer.id=?1")
	public Collection<Rating> findByTrainerId(int trainerId);

	@Query("select avg(r.stars) from Rating r where r.trainer.id=?1")
	public Double getAvgByTrainerId(int trainerId);

	@Query("select r from Rating r where r.request.id=?1")
	public Rating findByRequestId(int requestId);
}
