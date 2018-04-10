
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Advertising;

@Repository
public interface AdvertisingRepository extends JpaRepository<Advertising, Integer> {

	@Query("select a from Advertising a where a.userAccount.id = ?1")
	Advertising findByUserAccountId(int userAccountId);
}
