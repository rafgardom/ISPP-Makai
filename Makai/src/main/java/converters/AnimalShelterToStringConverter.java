
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.AnimalShelter;

@Component
@Transactional
public class AnimalShelterToStringConverter implements Converter<AnimalShelter, String> {

	@Override
	public String convert(final AnimalShelter animalShelter) {
		String result;

		if (animalShelter == null)
			result = null;
		else
			result = String.valueOf(animalShelter.getId());
		return result;
	}

}
