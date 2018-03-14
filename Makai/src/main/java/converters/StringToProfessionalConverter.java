
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProfessionalRepository;
import domain.Professional;

@Component
@Transactional
public class StringToProfessionalConverter implements Converter<String, Professional> {

	@Autowired
	ProfessionalRepository	professionalRepository;


	@Override
	public Professional convert(final String text) {
		Professional result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.professionalRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
