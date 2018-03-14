
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Breed;
import domain.Specie;

@Repository
public interface SpecieRepository extends JpaRepository<Specie, Integer> {

	@Query("select b from Breed b where b.specie.type=?1")
	Collection<Breed> findBreedWithThisSpecie(String specie);

}
