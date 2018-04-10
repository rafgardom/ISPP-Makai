
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Advertising;

@Component
@Transactional
public class AdvertisingToStringConverter implements Converter<Advertising, String> {

	@Override
	public String convert(final Advertising advertising) {
		String result;

		if (advertising == null)
			result = null;
		else
			result = String.valueOf(advertising.getId());
		return result;
	}

}
