
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AdvertisingRepository;
import domain.Advertising;

@Component
@Transactional
public class StringToAdvertisingConverter implements Converter<String, Advertising> {

	@Autowired
	AdvertisingRepository	advertisingRepository;


	@Override
	public Advertising convert(final String text) {
		Advertising result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.advertisingRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
