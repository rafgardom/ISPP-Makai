
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.ReceiptService;
import domain.Customer;
import domain.Receipt;

@Controller
@RequestMapping("/receipt")
public class ReceiptController extends AbstractController {

	//Related services
	@Autowired
	private ReceiptService	receiptService;

	@Autowired
	private CustomerService	customerService;


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

			result = new ModelAndView("receipt/customer/pending");
			result.addObject("receipts", pendingReceipts);
			result.addObject("RequestURI", "receipt/customer/pending.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
			result.addObject("errorMessage", "receipt.pending.list.error");
		}

		return result;
	}

	//List pending receipt
	@RequestMapping(value = "/customer/paid", method = RequestMethod.GET)
	public ModelAndView listpaid() {
		ModelAndView result;
		try {
			final Customer customer = this.customerService.findByPrincipal();
			final Collection<Receipt> paidReceipts = this.receiptService.getPaidReceipts(customer);

			result = new ModelAndView("receipt/customer/paid");
			result.addObject("receipts", paidReceipts);
			result.addObject("RequestURI", "receipt/customer/paid.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
			result.addObject("errorMessage", "receipt.paid.list.error");
		}

		return result;
	}

}
