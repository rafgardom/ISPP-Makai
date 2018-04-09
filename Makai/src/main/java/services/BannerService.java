
package services;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import repositories.BannerRepository;
import utilities.Utilities;
import domain.Administrator;
import domain.Banner;
import domain.Notification;
import forms.BannerForm;

@Service
@Transactional
public class BannerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private BannerRepository		bannerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private NotificationService		notificationService;

	@Autowired
	private Validator				validator;


	// Constructors------------------------------------------------------------
	public BannerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Banner findOne(final int bannerId) {
		Banner result;

		result = this.bannerRepository.findOne(bannerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Banner> findAll() {
		Collection<Banner> result;

		result = this.bannerRepository.findAll();

		return result;
	}

	public Banner create() {
		Banner result;

		result = new Banner();
		result.setTotalViews(0);

		return result;
	}

	public Banner save(final Banner banner) {
		Assert.notNull(banner);
		Banner result;

		if (banner.getId() != 0)
			//Solo se puede modificar si el current y el total view son iguales
			Assert.isTrue(banner.getCurrentViews().equals(banner.getTotalViews()));

		banner.setPrice(0.01 * banner.getTotalViews());

		result = this.bannerRepository.save(banner);

		if (banner.getId() == 0) {
			Notification notification;

			notification = this.notificationService.createForBanner();

			notification.setReason("Banner creado");
			notification.setDescription("La empresa " + banner.getEnterprise() + " con correco electrónico " + banner.getEmail() + " ha creado un banner nuevo.");

			this.notificationService.save(notification);
		}

		return result;
	}

	public void delete(final Banner banner) {
		Administrator principal;

		Assert.notNull(banner);
		Assert.isTrue(banner.getId() != 0);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		this.bannerRepository.delete(banner);
	}

	// Other business methods -------------------------------------------------

	public Banner reconstruct(final BannerForm bannerForm, final BindingResult binding) throws IOException {

		Assert.notNull(bannerForm);

		Banner result;

		if (bannerForm.getId() == 0 && bannerForm.getBannerImage().getSize() == 0) {
			FieldError fieldError;
			final String[] codes = {
				"banner.picture.empty.error"
			};
			fieldError = new FieldError("bannerForm", "bannerImage", bannerForm.getBannerImage(), false, codes, null, "");
			binding.addError(fieldError);

		} else if (bannerForm.getBannerImage().getSize() > 5242880) {
			FieldError fieldError;
			final String[] codes = {
				"banner.picture.tooLong.error"
			};
			fieldError = new FieldError("bannerForm", "bannerImage", bannerForm.getBannerImage(), false, codes, null, "");
			binding.addError(fieldError);
		}

		if (bannerForm.getBannerImage().getSize() != 0 && !bannerForm.getBannerImage().getContentType().contains("image")) {
			FieldError fieldError;
			final String[] codes = {
				"banner.picture.extension.error"
			};
			fieldError = new FieldError("bannerForm", "bannerImage", bannerForm.getBannerImage(), false, codes, null, "");
			binding.addError(fieldError);
		}

		if (bannerForm.getId() == 0)
			result = this.create();
		else
			result = this.findOne(bannerForm.getId());

		result.setEnterprise(bannerForm.getEnterprise());
		result.setEmail(bannerForm.getEmail());
		result.setTotalViews(bannerForm.getTotalViews());
		result.setCurrentViews(bannerForm.getCurrentViews());
		result.setPrice(bannerForm.getPrice());

		if (bannerForm.getPicture() != null)
			result.setPicture(bannerForm.getPicture());

		this.validator.validate(result, binding);

		return result;

	}
	public BannerForm bannerToFormObject(final Banner banner) {
		final BannerForm result;
		String image;

		Assert.notNull(banner);

		result = new BannerForm();

		if (banner.getPicture() != null)
			image = Utilities.showImage(banner.getPicture());
		else
			image = null;

		result.setId(banner.getId());
		result.setEnterprise(banner.getEnterprise());
		result.setEmail(banner.getEmail());
		result.setPrice(banner.getPrice());
		result.setTotalViews(banner.getTotalViews());
		result.setCurrentViews(banner.getCurrentViews());
		result.setPicture(banner.getPicture());
		result.setStringImage(image);

		return result;
	}

}
