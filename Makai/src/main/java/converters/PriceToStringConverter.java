package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Price;

@Component
@Transactional
public class PriceToStringConverter implements Converter<Price, String> {

	@Override
	public String convert(Price price) {
		String result;

		if (price == null){
			result = null;
		}else{
			result = String.valueOf(price.getId());
		}
		return result;
	}

}
