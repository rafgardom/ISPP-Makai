
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Trainer;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

	@Query("select a from Trainer a where a.userAccount.id = ?1")
	Trainer findByUserAccountId(int userAccountId);

	@Query("select t.trainer from Training t where t.category = ?1")
	Collection<Trainer> findTrainerSameCategory(Category category);
}
