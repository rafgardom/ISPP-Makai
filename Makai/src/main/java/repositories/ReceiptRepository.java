
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

	@Query("select r from Receipt r where r.isPaid = false and r.request.customer.id = ?1")
	Collection<Receipt> getPendingReceipts(int customerId);

	@Query("select r from Receipt r where r.isPaid = true and r.request.customer.id = ?1")
	Collection<Receipt> getPaidReceipts(int customerId);
}
