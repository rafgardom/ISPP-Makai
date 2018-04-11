
package services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import repositories.BannerRepository;
import utilities.Utilities;
import domain.Actor;
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
	private NotificationService		notificationService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

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
		result.setCurrentViews(0);
		final Actor actor = this.actorService.findByPrincipal();
		result.setActor(actor);
		result.setActive(false);
		result.setPaid(false);

		return result;
	}

	public Banner save(final Banner banner) {
		Assert.notNull(banner);
		Banner result;
		Actor actor;
		double totalViewsVal;

		actor = this.actorService.findByPrincipal();
		Assert.isTrue(banner.getActor().equals(actor));

		if (banner.getId() != 0)
			//Solo se puede modificar si el current y el total view son iguales
			Assert.isTrue(banner.getCurrentViews().equals(banner.getTotalViews()));

		totalViewsVal = 1.0 * banner.getTotalViews();
		totalViewsVal = Math.round(totalViewsVal);
		banner.setPrice(totalViewsVal / 100);

		result = this.bannerRepository.save(banner);

		if (banner.getId() == 0) {
			Notification notification;

			notification = this.notificationService.createForBanner();

			notification.setReason("Banner creado");
			notification.setDescription("Se ha creado un anuncio por parte de " + banner.getActor().getName() + " con correco electrónico " + banner.getActor().getEmail() + " y teléfono " + banner.getActor().getPhone());

			this.notificationService.save(notification);
		}

		return result;
	}

	public void simpleSave(final Banner banner) {
		Assert.notNull(banner);
		this.bannerRepository.save(banner);
	}

	public void delete(final Banner banner) {
		Actor principal;

		Assert.notNull(banner);
		Assert.isTrue(banner.getId() != 0);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(this.actorService.checkAuthority(principal, "ADMIN") || banner.getActor().equals(principal));

		this.bannerRepository.delete(banner);
	}

	// Other business methods -------------------------------------------------

	public Banner reconstruct(final BannerForm bannerForm, final BindingResult binding) throws IOException {

		Assert.notNull(bannerForm);

		Banner result;
		BufferedImage bufferedImage;

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

		bufferedImage = ImageIO.read(bannerForm.getBannerImage().getInputStream());
		// Esto me vale para controlar el tamaño de los bannners
		System.out.println(bufferedImage.getWidth());
		System.out.println(bufferedImage.getHeight());

		if (bannerForm.getId() == 0)
			result = this.create();
		else
			result = this.findOne(bannerForm.getId());

		result.setTotalViews(bannerForm.getTotalViews());
		result.setCurrentViews(bannerForm.getCurrentViews());
		result.setPrice(bannerForm.getPrice());
		result.setZone(bannerForm.getZone());
		result.setPaid(bannerForm.isPaid());

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
		result.setPrice(banner.getPrice());
		result.setTotalViews(banner.getTotalViews());
		result.setCurrentViews(banner.getCurrentViews());
		result.setPicture(banner.getPicture());
		result.setStringImage(image);
		result.setZone(banner.getZone());
		result.setPaid(banner.isPaid());
		result.setActive(banner.isActive());

		return result;
	}

	public Collection<Banner> findByActorId(final int actorId) {
		Collection<Banner> result;

		result = this.bannerRepository.findByActorId(actorId);

		return result;
	}

	public Banner active(final Banner banner) {
		Assert.notNull(banner);
		Banner result;
		Administrator administrator;

		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

		banner.setActive(true);
		result = this.bannerRepository.save(banner);

		//		Notification notification;
		//		notification = this.notificationService.create();
		//
		//		notification.setType(NotificationType.BANNER);
		//		notification.setReason("Banner aceptado");
		//		notification.setDescription("Un banner que usted ha registrado ha sido aceptado. Si desea ver el banner haga click en el siguiente enlace: ");
		//
		//		this.notificationService.save(notification);

		return result;
	}

}
