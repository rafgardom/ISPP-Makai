
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.ReceiptService;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

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

	private final String	clientID		= "AcfmnPWoOp2x6x5DbmGjyl0gUCWxWEvCTOEnQNsl4QJWQwaL-QzspwL9vbDXTd4xCZHuEIcm6lPYpq0_";
	private final String	clientSecret	= "EDTVo6vMPwwd78s2Dvj21VwsnD1eUY7Rd98KJtnGMmd5Q9_nAG6rIteY4k8nUaEZnSC-EUJ0FOqgy9J6";
	private final String	mode			= "live";


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

	//Payment

	@RequestMapping(value = "/customer/payment", method = RequestMethod.GET)
	public ModelAndView payment(@RequestParam final double receiptAmount) {
		ModelAndView result;
		try {
			final Customer customer = this.customerService.findByPrincipal();
			//			result = new ModelAndView("receipt/customer/pending");
			//			result.addObject("receipts", pendingReceipts);
			//			result.addObject("RequestURI", "receipt/customer/pending.do");

			final Amount amount = new Amount();
			amount.setCurrency("EUR");
			amount.setTotal(Double.toString(receiptAmount));

			final Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			final List<Transaction> transactions = new ArrayList<Transaction>();
			transactions.add(transaction);

			final Payer payer = new Payer();
			payer.setPaymentMethod("paypal");

			final Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);

			final RedirectUrls redirectUrls = new RedirectUrls();
			redirectUrls.setCancelUrl("/");
			redirectUrls.setReturnUrl("/");
			payment.setRedirectUrls(redirectUrls);

			System.out.println(payment.getLinks());

			result = new ModelAndView();

			try {
				final APIContext apiContext = new APIContext(this.clientID, this.clientSecret, this.mode);
				final Payment createdPayment = payment.create(apiContext);
				System.out.println(createdPayment.toString());
				result = new ModelAndView(createdPayment.getLinks().get(1).getHref());
			} catch (final PayPalRESTException e) {
				System.out.println(e);
			} catch (final Exception ex) {
				// Handle errors
				System.out.println(ex);
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
			result.addObject("errorMessage", "receipt.pay.error");
		}

		return result;
	}

	//List paid receipt
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
