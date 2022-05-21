package org.springframework.samples.petclinic.configuration;

import java.io.IOException;
import java.time.LocalDate;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.convert.ConversionService;
import org.springframework.samples.petclinic.clinicowner.ClinicOwner;
import org.springframework.samples.petclinic.clinicowner.ClinicOwnerService;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class SendMail implements ApplicationListener<AuthenticationSuccessEvent> {

	private final ClinicOwnerService clinicOwnerService;
	private ConversionService conversionService;

	@Autowired
	public SendMail(ClinicOwnerService clinicOwnerService, ConversionService conversionService) {
		this.clinicOwnerService = clinicOwnerService;
		this.conversionService = conversionService;
	}


	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		String userName = ((UserDetails) event.getAuthentication().getPrincipal()).getUsername();
		ClinicOwner clinicOwner = this.clinicOwnerService.findClinicOwnerByUserName(userName);
		try {
			if (clinicOwner != null) {
				String email = clinicOwner.getEmail().strip().toLowerCase();
				sendMail(userName, email);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMail(String username, String email) throws IOException {
		Email from = new Email("artrodase@alum.us.es"); // no cambiar, va a dar error
		String subject = "Nuevo inicio de sesión";
		Email to = new Email(email);
		String sistemaOperativo = System.getProperty("os.name");
		String date = conversionService.convert(LocalDate.now(), String.class);

		Content content = new Content("text/html", "<!DOCTYPE html>\r\n"
		+ "<html>\r\n"
		+ "<head>\r\n"
		+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
		+ "<style>\r\n"
		+ "body {\r\n"
		+ "    font-family: Arial;\r\n"
		+ "}\r\n"
		+ "\r\n"
		+ ".coupon {\r\n"
		+ "    border: 5px dotted #bbb;\r\n"
		+ "    width: 80%;\r\n"
		+ "    border-radius: 15px;\r\n"
		+ "    margin: 0 auto;\r\n"
		+ "    max-width: 600px;\r\n"
		+ "}\r\n"
		+ "\r\n"
		+ ".container {\r\n"
		+ "    padding: 2px 16px;\r\n"
		+ "    background-color: #f1f1f1;\r\n"
		+ "}\r\n"
		+ "\r\n"
		+ ".promo {\r\n"
		+ "    background: #ccc;\r\n"
		+ "    padding: 3px;\r\n"
		+ "}\r\n"
		+ "\r\n"
		+ ".expire {\r\n"
		+ "    color: red;\r\n"
		+ "}\r\n"
		+ "</style>\r\n"
		+ "</head>\r\n"
		+ "<body>\r\n"
		+ "\r\n"
		+ "<div class=\"coupon\">\r\n"
		+ "  <div class=\"container\">\r\n"
		+ "    <h3>Petclinic PSG2 G4-43</h3>\r\n"
		+ "  </div>\r\n"
		+ "  <div class=\"container\" style=\"background-color:white\">\r\n"
		+ "    <h2><b>Hola, "+username+".</b></h2> \r\n"
		+ "    <p>¡Parece que alguien ha iniciado sesión en tu cuenta! Si no has sido tú, por favor, cambia la contraseña.</p>\r\n"
		+ "  </div>\r\n"
		+ "  <div class=\"container\">\r\n"
		+ "    <p>Sistema operativo: <span class=\"promo\">"+sistemaOperativo+"</span></p>\r\n"
		+ "    <p class=\"expire\">Fecha: "+date+"</p>\r\n"
		+ "  </div>\r\n"
		+ "</div>\r\n"
		+ "\r\n"
		+ "</body>\r\n"
		+ "</html> ");

		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(
				"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");
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());

			sg.api(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

