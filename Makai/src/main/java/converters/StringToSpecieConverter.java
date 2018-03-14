
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SpecieRepository;
import domain.Specie;

@Component
@Transactional
public class StringToSpecieConverter implements Converter<String, Specie> {

	@Autowired
	SpecieRepository	specieRepository;


	@Override
	public Specie convert(final String text) {
		Specie result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.specieRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
