package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Specie;

@Component
@Transactional
public class SpecieToStringConverter implements Converter<Specie, String> {

	@Override
	public String convert(Specie specie) {
		String result;

		if (specie == null){
			result = null;
		}else{
			result = String.valueOf(specie.getId());
		}
		return result;
	}

}
