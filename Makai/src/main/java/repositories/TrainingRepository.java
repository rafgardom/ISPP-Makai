
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Integer> {

	@Query("select t from Training t where t.trainer.id=?1")
	Collection<Training> findByTrainerId(int trainerId);
}
