/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.samples.petclinic.cause;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.donation.Donation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {

	private CauseRepository causeRepository;


	@Autowired
	public CauseService(CauseRepository causeRepository) {
		this.causeRepository = causeRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Cause> findAll() {
		return causeRepository.findAll();
	}

	@Transactional
	public void saveCause(Cause cause) throws DataAccessException {
		causeRepository.save(cause);
	}

	@Transactional(readOnly = true)
	public Cause findCauseById(int causeId) {
		return causeRepository.findById(causeId);
	}

	@Transactional(readOnly = true)
	public Collection<Donation> findDonationListOfCauseById(int causeId) {
		return causeRepository.findDonationListOfCauseById(causeId);
	}

	@Transactional
	public void deleteCause(Cause cause) {
		causeRepository.deleteById(cause.getId());
	}

	@Transactional
	public void deleteAllCauses() {
		causeRepository.deleteAll();
	}


}
