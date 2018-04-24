
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import services.NotificationService;
import services.PriceService;
import controllers.AbstractController;
import domain.Banner;
import domain.Price;
import forms.BannerForm;

@Controller
@RequestMapping("/banner/admin")
public class BannerAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private BannerService		bannerService;

	@Autowired
	private PriceService		priceService;

	@Autowired
	private NotificationService	notificationService;


	// Constructors -----------------------------------------------------------

	public BannerAdministratorController() {
		super();
	}

	// Validate ----------------------------------------------------------------		
	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public ModelAndView validate(@RequestParam final int bannerId) {
		ModelAndView result;
		try {
			final Banner banner = this.bannerService.findOne(bannerId);

			this.bannerService.validate(banner);
			result = new ModelAndView("redirect:../actor/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
		}
		return result;
	}

	// Dashboard ---------------------------------------------------------------		
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;
		Collection<BannerForm> bannerMoreViews;
		Collection<BannerForm> bannerLessViews;
		Collection<BannerForm> bannerMoreClicks;
		Collection<BannerForm> bannerLessClicks;
		Price price;
		Integer numberNoti;

		try {
			bannerMoreViews = this.bannerService.findMoreViews();
			bannerLessViews = this.bannerService.findLessViews();
			bannerMoreClicks = this.bannerService.findMoreClicks();
			bannerLessClicks = this.bannerService.findLessClicks();
			price = this.priceService.findOne();

			numberNoti = this.notificationService.findNotificationWithoutRead();

			result = new ModelAndView("banner/dashboard");
			result.addObject("requestURI", "banner/admin/dashboard.do");
			result.addObject("numberNoti", numberNoti);
			result.addObject("bannerMoreViews", bannerMoreViews);
			result.addObject("bannerLessViews", bannerLessViews);
			result.addObject("bannerMoreClicks", bannerMoreClicks);
			result.addObject("bannerLessClicks", bannerLessClicks);
			result.addObject("bannersTotalBenefit", price.getBannersTotalBenefit());
			result.addObject("bannersAvgBenefit", 1.0 * Math.round(price.getBannersTotalBenefit() / price.getBannersCreated() * 100) / 100);
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
		}
		return result;
	}
}
