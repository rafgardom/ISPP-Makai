
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
import controllers.AbstractController;
import domain.Banner;
import forms.BannerForm;

@Controller
@RequestMapping("/banner/admin")
public class BannerAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private BannerService		bannerService;

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
		Double bannersTotalBenefit;
		Double bannersAvgBenefit;
		Double bannersMonthlyBenefit;
		final Integer numberNoti;

		try {
			bannerMoreViews = this.bannerService.findMoreViews();
			bannerLessViews = this.bannerService.findLessViews();
			bannerMoreClicks = this.bannerService.findMoreClicks();
			bannerLessClicks = this.bannerService.findLessClicks();
			bannersTotalBenefit = this.bannerService.findSumPricesPaid();
			bannersAvgBenefit = this.bannerService.findAvgPricesPaid();
			bannersMonthlyBenefit = this.bannerService.findMonthlyBenefitsPaid();

			numberNoti = this.notificationService.findNotificationWithoutRead();

			result = new ModelAndView("banner/dashboard");
			result.addObject("requestURI", "banner/admin/dashboard.do");
			result.addObject("numberNoti", numberNoti);
			result.addObject("bannerMoreViews", bannerMoreViews);
			result.addObject("bannerLessViews", bannerLessViews);
			result.addObject("bannerMoreClicks", bannerMoreClicks);
			result.addObject("bannerLessClicks", bannerLessClicks);
			result.addObject("bannersTotalBenefit", bannersTotalBenefit);
			result.addObject("bannersAvgBenefit", bannersAvgBenefit);
			result.addObject("bannersMonthlyBenefit", bannersMonthlyBenefit);
		} catch (final Throwable oops) {
			result = new ModelAndView("error");
		}
		return result;
	}
}
