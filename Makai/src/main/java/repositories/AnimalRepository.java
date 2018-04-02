
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {

	@Query("select a from Animal a where a.animalShelter.id = ?1 or a.customer.id=?1")
	Collection<Animal> findByActorId(int actorId);

	@Query("select a from Animal a where a.animalShelter != null")
	Collection<Animal> findAnimalFromAnimalShelter();

	@Query("select a from Animal a where a.isHidden=false and (a.animalShelter.id = ?1 or a.customer.id=?1)")
	Collection<Animal> findByActorIdNotHidden(int actorId);
}
