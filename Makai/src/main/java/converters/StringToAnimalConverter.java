
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AnimalRepository;
import domain.Animal;

@Component
@Transactional
public class StringToAnimalConverter implements Converter<String, Animal> {

	@Autowired
	AnimalRepository	animalRepository;


	@Override
	public Animal convert(final String text) {
		Animal result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.animalRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
