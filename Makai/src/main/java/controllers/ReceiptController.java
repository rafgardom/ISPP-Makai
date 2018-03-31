
package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import paypal.PaypalEnvironment;
import services.CustomerService;
import services.NotificationService;
import services.ReceiptService;

import com.paypal.base.rest.APIContext;

import domain.Customer;
import domain.Receipt;

@Controller
@RequestMapping("/receipt")
public class ReceiptController extends AbstractController {

	//Related services
	@Autowired
	private ReceiptService		receiptService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private NotificationService	notificationService;

	private PaypalEnvironment	paypalEnvironment;

	String						clientId		= "AVg7HUxM_8rUkR1uomU_nVUgRPQmRtdEQcY3iaplKwDaOVYboHUofbIln77eimIpcy0HvWlS_kFKRWHi";
	String						clientSecret	= "EBKr5W44tQkZYjqLyyDs0iwlwA4gdPa9bb3oF195Pa-6k93SpTjujesWw01YtdtSBd7Ya45fhnl-GqwP";

	APIContext					apiContext		= new APIContext(this.clientId, this.clientSecret, "sandbox");

	Map<String, String>			map				= new HashMap<String, String>();


	//Constructor
	public ReceiptController() {
		super();
	}

	//List pending receipt
	@RequestMapping(value = "/customer/pending", method = RequestMethod.GET)
	public ModelAndView listPending() {
		ModelAndView result;
		try {
			final Customer customer = this.customerService.findByPrincipal();
			final Collection<Receipt> pendingReceipts = this.receiptService.getPendingReceipts(customer);
			final Integer numberNoti;
			numberNoti = this.notificationService.findNotificationWithoutRead();

			result = new ModelAndView("receipt/customer/pending");
			result.addObject("receipts", pendingReceipts);
			result.addObject("numberNoti", numberNoti);
			result.addObject("RequestURI", "receipt/customer/pending.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
			result.addObject("errorMessage", "receipt.pending.list.error");
		}

		return result;
	}

	//Payment

	//	@RequestMapping(value = "/customer/payment", method = RequestMethod.GET)
	//	public ModelAndView payment(@RequestParam final double receiptAmount, final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
	//		ModelAndView result;
	//		result = new ModelAndView();
	//		try {
	//			this.createPayment(req, resp);
	//			//req.getRequestDispatcher("receipt/list.jsp").forward(req, resp);
	//			result = new ModelAndView("redirect:" + req.getAttribute("redirectURL"));
	//
	//		} catch (final Throwable oops) {
	//			result = new ModelAndView("redirect:/");
	//			result.addObject("errorMessage", "receipt.pay.error");
	//		}
	//		return result;
	//	}

	//List paid receipt
	@RequestMapping(value = "/customer/paid", method = RequestMethod.GET)
	public ModelAndView listpaid(@RequestParam(required = false) final String token) {
		ModelAndView result;
		try {
			final Customer customer = this.customerService.findByPrincipal();
			final Collection<Receipt> paidReceipts = this.receiptService.getPaidReceipts(customer);
			final Integer numberNoti;
			numberNoti = this.notificationService.findNotificationWithoutRead();

			result = new ModelAndView("receipt/customer/paid");
			result.addObject("receipts", paidReceipts);
			result.addObject("numberNoti", numberNoti);
			result.addObject("RequestURI", "receipt/customer/paid.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
			result.addObject("errorMessage", "receipt.paid.list.error");
		}

		return result;
	}

}
