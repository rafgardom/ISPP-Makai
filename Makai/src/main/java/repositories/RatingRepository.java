
package repositories;

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
}
