
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Professional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {

	@Query("select a from Professional a where a.userAccount.id = ?1")
	Professional findByUserAccountId(int userAccountId);

}
