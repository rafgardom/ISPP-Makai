
package controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import services.PriceService;
import domain.Banner;
import domain.Price;
import forms.BannerForm;

@Controller
@RequestMapping("/banner")
public class BannerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private BannerService	bannerService;

	@Autowired
	private PriceService	priceService;


	// Constructors -----------------------------------------------------------

	public BannerController() {
		super();
	}

	// Creation ---------------------------------------------------------------		
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		Banner banner;
		BannerForm bannerForm;

		banner = this.bannerService.create();
		bannerForm = this.bannerService.bannerToFormObject(banner);

		result = this.createEditModelAndView(bannerForm);

		return result;
	}

	// Edition ----------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final BannerForm bannerForm, final BindingResult binding) throws IOException {

		ModelAndView result;
		Banner banner;
		byte[] savedFile;
		boolean pictureTooLong = false;
		banner = this.bannerService.reconstruct(bannerForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(bannerForm);

		} else
			try {

				if (bannerForm.getBannerImage().getSize() > 2097152 || !bannerForm.getBannerImage().getContentType().contains("image")) {
					pictureTooLong = true;
					throw new IllegalArgumentException();

				} else {
					savedFile = bannerForm.getBannerImage().getBytes();
					banner.setPicture(savedFile);
					this.bannerService.save(banner);
				}

				result = new ModelAndView("redirect:../");

			} catch (final Throwable oops) {
				System.out.println(oops);
				if (pictureTooLong == false)
					result = this.createEditModelAndView(bannerForm, "advertising.register.error");
				else
					result = this.createEditModelAndView(bannerForm, "banner.commit.error");
			}
		return result;

	}

	// Creation ---------------------------------------------------------------		
	@RequestMapping(value = "/bannerClick", method = RequestMethod.GET)
	public ModelAndView bannerClick(@RequestParam final int bannerId) {
		ModelAndView result;
		Banner banner;

		banner = this.bannerService.findOne(bannerId);
		banner.setClicksNumber(banner.getClicksNumber() + 1);
		this.bannerService.simpleSave(banner);

		result = new ModelAndView("redirect:" + banner.getUrl());

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final BannerForm bannerForm) {
		ModelAndView result;

		result = this.createEditModelAndView(bannerForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final BannerForm bannerForm, final String message) {
		ModelAndView result;
		Price price;

		price = this.priceService.findOne();

		result = new ModelAndView("banner/create");
		result.addObject("bannerForm", bannerForm);
		result.addObject("bannerPrice", price.getBannerPrice());
		result.addObject("requestURI", "banner/create.do");
		result.addObject("errorMessage", message);

		return result;
	}
}
