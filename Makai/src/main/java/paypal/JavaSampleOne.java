
package paypal;

import java.util.ArrayList;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;

public class JavaSampleOne {

	// Replace these values with your clientId and secret. You can use these to get started right now.
	String	clientId		= "AYeO6i3b_8MS1ppIS4GvwkCq78_M3iDjWDXYSJrRo6hbjM3ZiK_qDqujb4POTLaIZlaAtB72iQT0eqBc";
	String	clientSecret	= "EPjro4bBmlvWQWgeCW9TjPSwVOZdjllkNhDXVWxcuDDK0fKJnI5sjr8hoT4Ulx2uDZq3VVeIpudNZPnB";


	public Payment createPayment() {
		final Amount amount = new Amount();
		amount.setCurrency("EUR");
		amount.setTotal("1.00");

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
		redirectUrls.setCancelUrl("https://example.com/cancel");
		redirectUrls.setReturnUrl("https://example.com/return");
		payment.setRedirectUrls(redirectUrls);

		return payment;
	}

}
