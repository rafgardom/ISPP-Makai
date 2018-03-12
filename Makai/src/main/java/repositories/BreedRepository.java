
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Breed;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Integer> {

}
