package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Professional;

@Component
@Transactional
public class ProfessionalToStringConverter implements Converter<Professional, String> {

	@Override
	public String convert(Professional professional) {
		String result;

		if (professional == null){
			result = null;
		}else{
			result = String.valueOf(professional.getId());
		}
		return result;
	}

}
