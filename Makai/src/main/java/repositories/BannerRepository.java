
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

	@Query("select b from Banner b where b.actor.id=?1")
	Collection<Banner> findByActorId(int actorId);

	@Query("select b from Banner b where b.paid = true and b.validated = true")
	Collection<Banner> getValidatedAndPaid();
}
