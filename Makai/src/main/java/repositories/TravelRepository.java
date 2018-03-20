
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Travel;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Integer> {

	@Query("select t from Travel t where t.transporterOwner.id=?1")
	Collection<Travel> findTravelByTransporterId(int transporterId);

}
