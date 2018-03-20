
package paypal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
@RequestMapping("/paypal")
public class PaymentController {

	public static final String	PAYPAL_SUCCESS_URL	= "pay/success";
	public static final String	PAYPAL_CANCEL_URL	= "pay/cancel";

	private final Logger		log					= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PaypalService		paypalService;


	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public String pay(final HttpServletRequest request) {
		final String cancelUrl = URLUtils.getBaseURl(request) + "/" + PaymentController.PAYPAL_CANCEL_URL;
		final String successUrl = URLUtils.getBaseURl(request) + "/" + PaymentController.PAYPAL_SUCCESS_URL;
		try {
			final Payment payment = this.paypalService.createPayment(4.00, "USD", PaypalPaymentMethod.paypal, PaypalPaymentIntent.sale, "payment description", cancelUrl, successUrl);
			for (final Links links : payment.getLinks())
				if (links.getRel().equals("approval_url"))
					return "redirect:" + links.getHref();
		} catch (final PayPalRESTException e) {
			this.log.error(e.getMessage());
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = PaymentController.PAYPAL_CANCEL_URL)
	public String cancelPay() {
		return "cancel";
	}

	@RequestMapping(method = RequestMethod.GET, value = PaymentController.PAYPAL_SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") final String paymentId, @RequestParam("PayerID") final String payerId) {
		try {
			final Payment payment = this.paypalService.executePayment(paymentId, payerId);
			if (payment.getState().equals("approved"))
				return "success";
		} catch (final PayPalRESTException e) {
			this.log.error(e.getMessage());
		}
		return "redirect:/";
	}

}
