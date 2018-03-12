
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Travel;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Integer> {

}
