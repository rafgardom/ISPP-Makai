
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Offer;
import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select o from Offer o where o.request.id=?1")
	Collection<Offer> findOfferWithThisRequest(int request);

	@Query("select o from Offer o where o.request.id=?1 and o.isAccepted=true")
	Offer findOfferWithThisRequestTrue(int request);

	@Query("select r from Request r where r.customer.id=?1")
	Collection<Request> findRequestsByCustomer(int customer);

	@Query("select o.request from Offer o where o.isAccepted=true")
	Collection<Request> findRequestsAccepted();

	@Query("select distinct o.request from Offer o")
	Collection<Request> findRequestsWithOffer();

}
