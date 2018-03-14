package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Travel;

@Component
@Transactional
public class TravelToStringConverter implements Converter<Travel, String> {

	@Override
	public String convert(Travel travel) {
		String result;

		if (travel == null){
			result = null;
		}else{
			result = String.valueOf(travel.getId());
		}
		return result;
	}

}
