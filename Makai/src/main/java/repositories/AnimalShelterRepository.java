
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AnimalShelter;

@Repository
public interface AnimalShelterRepository extends JpaRepository<AnimalShelter, Integer> {

	@Query("select a from AnimalShelter a where a.userAccount.id = ?1")
	AnimalShelter findByUserAccountId(int userAccountId);

}
