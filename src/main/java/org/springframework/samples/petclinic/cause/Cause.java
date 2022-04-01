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

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.samples.petclinic.model.NamedEntity;

@Entity
@Table(name = "causes")
public class Cause extends NamedEntity {

	@NotEmpty
	@NotNull
	private String description;

	private double budgetTarget;

	private double donated;

	@NotEmpty
	@NotNull
	private String organization;

	@Transient
	public boolean isClosed() {
		// A cause is closed if it has reached its target.
		return this.donated >= this.budgetTarget;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBudgetTarget(double budgetTarget) {
		this.budgetTarget = budgetTarget;
	}

	public void setDonated(double donated) {
		this.donated = donated;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getDescription() {
		return this.description;
	}

	public double getBudgetTarget() {
		return this.budgetTarget;
	}

	public double getDonated() {
		return this.donated;
	}

	public String getOrganization() {
		return this.organization;
	}
}
