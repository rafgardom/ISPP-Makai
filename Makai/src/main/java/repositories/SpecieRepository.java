
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Specie;

@Repository
public interface SpecieRepository extends JpaRepository<Specie, Integer> {

}
