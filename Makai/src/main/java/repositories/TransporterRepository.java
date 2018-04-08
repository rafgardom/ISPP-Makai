
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Transporter;

@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Integer> {

	@Query("select a from Transporter a where a.userAccount.id = ?1")
	Transporter findByUserAccountId(int userAccountId);

	@Query("select a from Transporter a join a.travelPassengers t where t.id=?1")
	Collection<Transporter> findPassengersByTravel(int travelId);
}
