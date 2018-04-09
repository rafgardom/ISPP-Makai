
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccountId(int userAccountId);

	@Query("select a from Actor a where a.id not in (select admin.id from Administrator admin)")
	Collection<Actor> findAllNotAdmin();

	@Query("select a from Actor a where a.userAccount.username = ?1")
	Actor findByusername(String username);

	@Query("select a from Actor a join a.userAccount.authorities auth where auth.authority = 'ADMIN'")
	Collection<Actor> findAdministrator();
}
