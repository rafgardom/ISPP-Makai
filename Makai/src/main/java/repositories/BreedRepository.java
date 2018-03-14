
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Animal;
import domain.Breed;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Integer> {

	@Query("select a from Animal a join a.breeds b where b.breed=?1")
	Collection<Animal> findAnimalWithThisBreed(String breed);

}
