
package paypal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PaypalService {

	@Autowired
	private APIContext	apiContext;


	public Payment createPayment(final Double total, final String currency, final PaypalPaymentMethod method, final PaypalPaymentIntent intent, final String description, final String cancelUrl, final String successUrl) throws PayPalRESTException {
		final Amount amount = new Amount();
		amount.setCurrency(currency);
		amount.setTotal(String.format("%.2f", total));

		final Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);

		final List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		final Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());

		final Payment payment = new Payment();
		payment.setIntent(intent.toString());
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		final RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);

		return payment.create(this.apiContext);
	}

	public Payment executePayment(final String paymentId, final String payerId) throws PayPalRESTException {
		final Payment payment = new Payment();
		payment.setId(paymentId);
		final PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(this.apiContext, paymentExecute);
	}
}
