
package services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
import domain.NotificationType;
import domain.Price;
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
	private PriceService			priceService;

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

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		result = new Banner();
		result.setTotalViews(0);
		result.setCurrentViews(0);
		result.setActor(actor);
		result.setValidated(false);
		result.setPaid(false);
		result.setClicksNumber(0);
		result.setEditionsNumber(0);
		result.setTotalBenefit(0.0);
		//Por dejecto todos son en la zona baja
		result.setZone("abajo");

		return result;
	}

	public Banner save(final Banner banner) {
		Assert.notNull(banner);
		Banner result;
		Actor actor;
		double bannerPriceVal;
		Price price;

		actor = this.actorService.findByPrincipal();
		Assert.isTrue(banner.getActor().equals(actor));

		Assert.isTrue(banner.getZone().equals("izquierda") || banner.getZone().equals("derecha") || banner.getZone().equals("abajo"));

		if (banner.getId() != 0) {
			banner.setEditionsNumber(banner.getEditionsNumber() + 1);
			banner.setCurrentViews(0);
			banner.setPaid(false);
			banner.setValidated(false);
			banner.setClicksNumber(0);
			banner.setPaidMoment(null);
		}

		price = this.priceService.findOne();
		bannerPriceVal = price.getBannerPrice() * banner.getTotalViews();
		bannerPriceVal = Math.round(bannerPriceVal * 100);
		banner.setPrice(bannerPriceVal / 100);

		// siempre que se haga una modificacion en el banner el admin debe volver a validarlo y se debe volver a realizar el pago
		banner.setPaid(false);
		banner.setValidated(false);
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
		bannerForm.setZone("abajo");
		Banner result;

		if (bannerForm.getTotalViews() < 1) {
			FieldError fieldError;
			final String[] codes = {
				"banner.totalViews.error"
			};
			fieldError = new FieldError("bannerForm", "totalViews", bannerForm.getTotalViews(), false, codes, null, "");
			binding.addError(fieldError);

		}
		if (bannerForm.getId() == 0 && bannerForm.getBannerImage().getSize() == 0) {
			FieldError fieldError;
			final String[] codes = {
				"banner.picture.empty.error"
			};
			fieldError = new FieldError("bannerForm", "bannerImage", bannerForm.getBannerImage(), false, codes, null, "");
			binding.addError(fieldError);

		} else if (bannerForm.getBannerImage().getSize() > 2097152) {
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
		} else if (bannerForm.getBannerImage().getSize() != 0) {
			BufferedImage bufferedImage;

			bufferedImage = ImageIO.read(bannerForm.getBannerImage().getInputStream());
			// Esto me vale para controlar el tamaño de los bannners
			if (bufferedImage.getWidth() > 3000 || bufferedImage.getWidth() < 900) {
				FieldError fieldError;
				final String[] codes = {
					"banner.picture.width.error"
				};
				fieldError = new FieldError("bannerForm", "bannerImage", bannerForm.getBannerImage(), false, codes, null, "");
				binding.addError(fieldError);

			}
			if (bufferedImage.getHeight() > 150 || bufferedImage.getHeight() < 100) {
				FieldError fieldError;
				final String[] codes = {
					"banner.picture.height.error"
				};
				fieldError = new FieldError("bannerForm", "bannerImage", bannerForm.getBannerImage(), false, codes, null, "");
				binding.addError(fieldError);
			}
		}

		if (!bannerForm.getZone().equals("izquierda") && !bannerForm.getZone().equals("derecha") && !bannerForm.getZone().equals("abajo")) {
			FieldError fieldError;
			final String[] codes = {
				"banner.zone.error"
			};
			fieldError = new FieldError("bannerForm", "zone", bannerForm.getZone(), false, codes, null, "");
			binding.addError(fieldError);
		}

		if (bannerForm.getId() == 0)
			result = this.create();
		else {
			result = this.findOne(bannerForm.getId());
			//Solo se puede modificar si el current y el total view son iguales
			Assert.isTrue(result.getCurrentViews().equals(result.getTotalViews()));
		}

		result.setTotalViews(bannerForm.getTotalViews());
		result.setCurrentViews(bannerForm.getCurrentViews());
		result.setPrice(bannerForm.getPrice());
		result.setZone(bannerForm.getZone());
		result.setPaid(bannerForm.isPaid());
		result.setValidated(bannerForm.isValidated());
		result.setUrl(bannerForm.getUrl());

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
		result.setValidated(banner.isValidated());
		result.setActor(banner.getActor());
		result.setUrl(banner.getUrl());

		return result;
	}

	public Collection<Banner> findByActorId(final int actorId) {
		Collection<Banner> result;

		result = this.bannerRepository.findByActorId(actorId);

		return result;
	}

	public Collection<BannerForm> findBannerFormsByActorId(final int actorId) {
		Collection<BannerForm> result;

		result = this.bannerRepository.findBannerFormsByActorId(actorId);

		return result;
	}

	public Collection<BannerForm> findAllBannerForms() {
		Collection<BannerForm> result;

		result = this.bannerRepository.findAllBannerForms();

		return result;
	}

	public Collection<BannerForm> findMoreViews() {
		Collection<BannerForm> result;

		result = this.bannerRepository.findMoreViews();

		return result;
	}

	public Collection<BannerForm> findLessViews() {
		Collection<BannerForm> result;

		result = this.bannerRepository.findLessViews();

		return result;
	}

	public Collection<BannerForm> findMoreClicks() {
		Collection<BannerForm> result;

		result = this.bannerRepository.findMoreClicks();

		return result;
	}

	public Collection<BannerForm> findLessClicks() {
		Collection<BannerForm> result;

		result = this.bannerRepository.findLessClicks();

		return result;
	}

	public Double findSumPricesPaid() {
		Double result;

		result = this.bannerRepository.findSumPricesPaid();

		return result;
	}

	public Double findAvgPricesPaid() {
		Double result;

		result = this.bannerRepository.findAvgPricesPaid();

		return 1.0 * Math.round(result * 100) / 100;
	}

	public Double findMonthlyBenefitsPaid() {
		Double result;

		result = this.bannerRepository.findMonthlyBenefitsPaid();

		return 1.0 * Math.round(result * 100) / 100;
	}

	public Banner validate(final Banner banner) {
		Assert.notNull(banner);
		Banner result;
		Administrator administrator;
		Notification notification;

		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

		banner.setValidated(true);
		result = this.bannerRepository.save(banner);

		notification = this.notificationService.create(banner.getActor());

		notification.setType(NotificationType.BANNER);
		notification.setReason("Banner aceptado");
		notification.setDescription("Un banner que usted ha registrado ha sido aceptado.");

		this.notificationService.save(notification);

		return result;
	}

	//	public Banner randomBannerGenerator() {
	//		Banner result;
	//		final Collection<Banner> potentialBanners = this.bannerRepository.getValidatedAndPaid();
	//		final ArrayList<Banner> banners = new ArrayList<Banner>(potentialBanners);
	//
	//		if (banners.size() == 0)
	//			result = null;
	//		else if (potentialBanners.size() == 1)
	//			result = banners.get(0);
	//		else {
	//			final SecureRandom random = new SecureRandom();
	//			final int index = random.nextInt(banners.size());
	//			result = banners.get(index);
	//		}
	//		return result;
	//	}

	public ArrayList<Banner> getBannerByZone(final String zone) throws UnsupportedEncodingException {
		final Collection<Banner> banners = this.bannerRepository.getBannerByZone(zone);
		final ArrayList<Banner> arrayListBanner = new ArrayList<Banner>(banners);
		//		final ArrayList<Banner> subList = null;
		ArrayList<Banner> result = null;

		if (arrayListBanner != null) {
			//			result = new ArrayList<Banner>();
			Collections.shuffle(arrayListBanner);
			if (arrayListBanner.size() <= 3)
				result = new ArrayList<Banner>(arrayListBanner);
			else
				result = new ArrayList<Banner>(arrayListBanner.subList(0, 2));
			for (final Banner banner : result) {
				banner.setCurrentViews(banner.getCurrentViews() + 1);
				this.bannerRepository.save(banner);
				//				final String image = new String(Base64.encode(banner.getPicture()), "UTF-8");
				//				result.add(image);
			}
		}

		return result;
	}
}
