package be.vdab.personeel.web.forms;

import javax.validation.constraints.NotNull;

import be.vdab.personeel.constraints.SocialSecurityNumberConstraint;
import be.vdab.personeel.constraints.SocialSecurityNumberFormConstraint;
import be.vdab.personeel.valueobjects.SocialSecurityNumber;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@SocialSecurityNumberFormConstraint
public class SocialSecurityNumberForm {

	@NotNull
	@DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
	private LocalDate date;
	
	@SocialSecurityNumberConstraint
	private SocialSecurityNumber socialSecurityNumber;
	
	public SocialSecurityNumberForm() {}
	public SocialSecurityNumberForm(
			final LocalDate localDate,
			final SocialSecurityNumber socialSecurityNumber) {
		setDate(localDate);
		setSocialSecurityNumber(socialSecurityNumber);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setSocialSecurityNumber(
			final SocialSecurityNumber socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}
	
	public SocialSecurityNumber getSocialSecurityNumber() {
		return socialSecurityNumber;
	}
}
