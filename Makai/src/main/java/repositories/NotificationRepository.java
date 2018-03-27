
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	@Query("select n from Notification n join n.actor a where a.id=?1")
	Collection<Notification> findByActorId(int actorId);
}
