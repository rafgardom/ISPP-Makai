
package controllers.trainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import services.NotificationService;
import services.RequestService;
import services.TrainerService;
import controllers.AbstractController;
import domain.Banner;
import domain.Request;
import domain.Trainer;

@Controller
@RequestMapping("/request/trainer")
public class RequestTrainerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RequestService		requestService;

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private BannerService		bannerService;
	
	@Autowired
	private TrainerService		trainerService;


	// Constructors -----------------------------------------------------------
	public RequestTrainerController() {
		super();
	}

	// List -------------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue="1") final int page, @RequestParam(defaultValue="5") final int nElem) {
		ModelAndView result;
		Page<Request> requests;
		final Integer numberNoti;
		final ArrayList<Banner> imagesBottom;
		List<Trainer> trainers = new ArrayList<Trainer>();
		try {
			requests = this.requestService.findRequestsNotAcceptedPaged(page-1,nElem);
			numberNoti = this.notificationService.findNotificationWithoutRead();

			imagesBottom = this.bannerService.getBannerByZone("abajo");
			
			if(requests.getContent().size()>0){
				trainers = trainerService.findTrainersByAcceptedsRequests(requests.getContent());
			}
		
			result = new ModelAndView("request/list");
			result.addObject("requestURI", "request/trainer/list.do");
			result.addObject("numberNoti", numberNoti);
			result.addObject("requests", requests.getContent());
			result.addObject("imagesBottom", imagesBottom);
			result.addObject("trainers", trainers);
			result.addObject("nElem", nElem);
			result.addObject("pageNumbers", requests.getTotalPages());
			result.addObject("page", page);
		} catch (final Throwable e) {
			result = new ModelAndView("error");
		}
		return result;
	}

}
