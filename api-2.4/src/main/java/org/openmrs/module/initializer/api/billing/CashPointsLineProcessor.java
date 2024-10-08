package org.openmrs.module.initializer.api.billing;

import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.api.LocationService;
import org.openmrs.module.billing.api.model.CashPoint;
import org.openmrs.module.initializer.api.BaseLineProcessor;
import org.openmrs.module.initializer.api.CsvLine;
import org.openmrs.module.initializer.api.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@OpenmrsProfile(modules = { "billing:1.1.0 - 9.*" })
public class CashPointsLineProcessor extends BaseLineProcessor<CashPoint> {
	
	protected static final String HEADER_DESCRIPTION = "description";
	
	protected static final String HEADER_LOCATION = "location";
	
	private final LocationService locationService;
	
	@Autowired
	public CashPointsLineProcessor(@Qualifier("locationService") LocationService locationService) {
		super();
		this.locationService = locationService;
	}
	
	@Override
	public CashPoint fill(CashPoint cashPoint, CsvLine line) throws IllegalArgumentException {
		cashPoint.setName(line.get(HEADER_NAME, true));
		cashPoint.setDescription(line.getString(HEADER_DESCRIPTION));
		String location = line.get(HEADER_LOCATION, true);
		cashPoint.setLocation(Utils.fetchLocation(location, locationService));
		
		return cashPoint;
	}
}
