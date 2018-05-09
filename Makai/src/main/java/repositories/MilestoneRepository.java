
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Milestone;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Integer> {

	@Query("select m from Milestone m where m.offer.id = ?1")
	public Collection<Milestone> findAllByOffer(int offerId);
}
