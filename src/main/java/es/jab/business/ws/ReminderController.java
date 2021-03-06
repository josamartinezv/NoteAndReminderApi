package es.jab.business.ws;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.jab.business.filter.TokenValidator;
import es.jab.persistence.dao.ReminderDao;
import es.jab.persistence.dao.UserDao;
import es.jab.persistence.model.Reminder;
import es.jab.persistence.model.Token;
import es.jab.persistence.model.User;
import es.jab.utils.json.JsonTransformer;

@Controller
public class ReminderController {

	@Autowired
	private JsonTransformer jsonTransformer;
	
	@Autowired
	private ReminderDao reminderDao;
	
	@Autowired
	private TokenValidator tokenValidator;
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = "/Reminder/{idReminder}", method = RequestMethod.GET, produces = "application/json")
    public void read(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idReminder") int idReminder) {
		try {
			Reminder reminder = reminderDao.read(idReminder);
			String jsonSalida = jsonTransformer.toJson(reminder);
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		    httpServletResponse.setContentType("application/json; charset=UTF-8");
		    httpServletResponse.getWriter().println(jsonSalida);
		
		} catch (Exception ex) {
			ex.printStackTrace();
		    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		     
		}
    }
 
    @RequestMapping(value = "/Reminder", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void insert(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada) {
		try {
			Reminder reminder = (Reminder) jsonTransformer.fromJson(jsonEntrada, Reminder.class);
			reminder.setUser(getUserFromRequest(httpServletRequest));
			reminderDao.create(reminder);
			String jsonSalida = jsonTransformer.toJson(reminder);
			      
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
			httpServletResponse.setContentType("application/json; charset=UTF-8");
			httpServletResponse.getWriter().println(jsonSalida);
		}     
		catch (Exception ex) {
			ex.printStackTrace();
			httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);   
		}

}

    @RequestMapping(value = "/Reminder/userId={userId}", method = RequestMethod.GET, produces = "application/json")
    public void findByUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("userId") int userId) {
    	try {
		    List<Reminder> reminders = reminderDao.getByUser(userId);
		    String jsonSalida = jsonTransformer.toJson(reminders);
		     
		    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		    httpServletResponse.setContentType("application/json; charset=UTF-8");
		    httpServletResponse.getWriter().println(jsonSalida);
		} catch (Exception ex) {
			ex.printStackTrace();
		    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/Reminder/{idReminder}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void update(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonEntrada, @PathVariable("idReminder") int idReminder) {
    	try {
    		Reminder reminder = (Reminder) jsonTransformer.fromJson(jsonEntrada, Reminder.class);
    		reminder.setUser(getUserFromRequest(httpServletRequest));
    		reminderDao.update(reminder);
		    String jsonSalida = jsonTransformer.toJson(reminder);
		     
		    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		    httpServletResponse.setContentType("application/json; charset=UTF-8");
		    httpServletResponse.getWriter().println(jsonSalida);
		} catch (Exception ex) {
			ex.printStackTrace();
		    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/Reminder/{idReminder}", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idReminder") int idReminder) {
    	try {
    		reminderDao.deleteById(idReminder);
		    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		}    
		catch (Exception ex) {
			ex.printStackTrace();
		    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
    }
    
    private User getUserFromRequest(HttpServletRequest request){
    	String authorizationHeader =  request.getHeader("Authorization");
        String subject = tokenValidator.validate(authorizationHeader);
        Token token = (Token) jsonTransformer.fromJson(subject, Token.class);
        return userDao.getUserByEmail(token.getEmail());
    }
	
}
