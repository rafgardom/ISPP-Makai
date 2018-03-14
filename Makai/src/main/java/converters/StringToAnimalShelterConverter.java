
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AnimalShelterRepository;
import domain.AnimalShelter;

@Component
@Transactional
public class StringToAnimalShelterConverter implements Converter<String, AnimalShelter> {

	@Autowired
	AnimalShelterRepository	animalShelterRepository;


	@Override
	public AnimalShelter convert(final String text) {
		AnimalShelter result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.animalShelterRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
