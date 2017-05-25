/*package edu.mum.rideshare.component.mail;

import java.util.Locale;

import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mum.rideshare.model.SysUser;
import edu.mum.rideshare.util.AppContextUtil;

public class EmailServiceTest {

	private EmailService emailService = null;

	@Before
	public void init() {
		System.out.println("init.");
		this.emailService = AppContextUtil.getInstance().getBean("emailService",
				EmailService.class);
	}

	@After
	public void destroy() {

	}
	
	@Test
	public void testSendOrderReceivedMail() {
        SysUser user = new SysUser();
        user.setUserId(1000);
        user.setFirstName("michale");
        user.setLastName("z");
    	
        try {
			emailService.sendResetPasswordMail("Kemosabe", "zhjjava@126.com",user,new Locale("en"));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        System.out.println("                      The Email is on the WAY!!!");
	}

}
*/