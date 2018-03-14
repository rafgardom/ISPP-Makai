package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Transporter;

@Component
@Transactional
public class TransporterToStringConverter implements Converter<Transporter, String> {

	@Override
	public String convert(Transporter transporter) {
		String result;

		if (transporter == null){
			result = null;
		}else{
			result = String.valueOf(transporter.getId());
		}
		return result;
	}

}
