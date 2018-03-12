
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

}
