
package paypal;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

@Configuration
public class PaypalConfig {

	@Value("${paypal.client.app}")
	private String	clientId;
	@Value("${paypal.client.secret}")
	private String	clientSecret;
	@Value("${paypal.mode}")
	private String	mode;


	@Bean
	public Map<String, String> paypalSdkConfig() {
		final Map<String, String> sdkConfig = new HashMap<>();
		sdkConfig.put("mode", "sandbox");

		return sdkConfig;
	}

	@Bean
	public OAuthTokenCredential authTokenCredential() {
		return new OAuthTokenCredential("AQkquBDf1zctJOWGKWUEtKXm6qVhueUEMvXO_-MCI4DQQ4-LWvkDLIN2fGsd", "EL1tVxAjhT7cJimnz5-Nsx9k2reTKSVfErNQF-CmrwJgxRtylkGTKlU4RvrX", this.paypalSdkConfig());
	}

	@Bean
	public APIContext apiContext() throws PayPalRESTException {
		final APIContext apiContext = new APIContext(this.authTokenCredential().getAccessToken());
		apiContext.setConfigurationMap(this.paypalSdkConfig());
		return apiContext;
	}
}
