
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Banner;
import forms.BannerForm;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

	@Query("select b from Banner b where b.actor.id=?1")
	Collection<Banner> findByActorId(int actorId);

	@Query("select b from Banner b where b.paid = true and b.validated = true")
	Collection<Banner> getValidatedAndPaid();

	@Query("select " + "new forms.BannerForm(b, COALESCE(1.0*b.currentViews/TIMESTAMPDIFF(day, b.paidMoment, CURRENT_TIMESTAMP), 0 ), COALESCE(1.0*b.currentViews/TIMESTAMPDIFF(month, b.paidMoment, CURRENT_TIMESTAMP), 0 ))" + " from "
		+ "Banner b where b.actor.id=?1")
	Collection<BannerForm> findBannerFormsByActorId(int actorId);

	@Query("select " + "new forms.BannerForm(b, COALESCE(1.0*b.currentViews/TIMESTAMPDIFF(day, b.paidMoment, CURRENT_TIMESTAMP), 0 ), COALESCE(1.0*b.currentViews/TIMESTAMPDIFF(month, b.paidMoment, CURRENT_TIMESTAMP), 0 ))" + " from " + "Banner b")
	Collection<BannerForm> findAllBannerForms();

	@Query("select " + "new forms.BannerForm(b)" + " from " + "Banner b where b.currentViews >= ALL (select b1.currentViews from Banner b1)")
	Collection<BannerForm> findMoreViews();

	@Query("select " + "new forms.BannerForm(b)" + " from " + "Banner b where b.currentViews <= ALL (select b1.currentViews from Banner b1)")
	Collection<BannerForm> findLessViews();

	@Query("select " + "new forms.BannerForm(b)" + " from " + "Banner b where b.clicksNumber >= ALL (select b1.clicksNumber from Banner b1)")
	Collection<BannerForm> findMoreClicks();

	@Query("select " + "new forms.BannerForm(b)" + " from " + "Banner b where b.clicksNumber <= ALL (select b1.clicksNumber from Banner b1)")
	Collection<BannerForm> findLessClicks();

	@Query("select sum(b.price) from Banner b where b.paid = true")
	Double findSumPricesPaid();

	@Query("select b.picture from Banner b where b.zone = ?1 and b.paid = true and b.validated = true")
	Collection<byte[]> getBannerByZone(String zone);
}
