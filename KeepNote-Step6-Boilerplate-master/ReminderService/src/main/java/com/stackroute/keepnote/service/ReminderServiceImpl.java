package com.stackroute.keepnote.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.ReminderNotCreatedException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.repository.ReminderRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class ReminderServiceImpl implements ReminderService {

	/*
	 * Autowiring should be implemented for the ReminderRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	
	private ReminderRepository reminderRepository;
	
	@Autowired
	public ReminderServiceImpl(ReminderRepository reminderRepository) {
		this.reminderRepository = reminderRepository;
	}
	
	/*
	 * This method should be used to save a new reminder.Call the corresponding
	 * method of Respository interface.
	 */
	
	public Reminder createReminder(Reminder reminder) throws ReminderNotCreatedException {

		if(reminder != null) {
			Reminder savedReminder = reminderRepository.insert(reminder);
			if(savedReminder != null)
				return savedReminder;
			else
				throw new ReminderNotCreatedException("Reminder not inserted");
		}else
			throw new ReminderNotCreatedException("Reminder not inserted");
	}

	/*
	 * This method should be used to delete an existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	
	public boolean deleteReminder(String reminderId) throws ReminderNotFoundException {
		boolean flag = false;
		Reminder reminder = reminderRepository.findById(reminderId).get();		
		if(reminder != null) {
			reminderRepository.delete(reminder);
			flag = true;
		}else
			throw new ReminderNotFoundException("Reminder not found");
		return flag;
	}

	/*
	 * This method should be used to update a existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder updateReminder(Reminder reminder, String reminderId) throws ReminderNotFoundException {
		
		Reminder reminderFound = reminderRepository.findById(reminderId).get();
		if(reminder != null && reminderFound != null) {
		if(reminderFound.getReminderId().equals(reminder.getReminderId())) {
			reminderRepository.save(reminder);
			return reminder;
		}else {
			throw new ReminderNotFoundException("Reminder not found");
		}
		}else
			throw new ReminderNotFoundException("Reminder not found");
	}

	/*
	 * This method should be used to get a reminder by reminderId.Call the
	 * corresponding method of Respository interface.
	 */
	
	public Reminder getReminderById(String reminderId) throws ReminderNotFoundException {

		return reminderRepository.findById(reminderId).orElseThrow(() -> new ReminderNotFoundException("Reminder not found"));
	}

	/*
	 * This method should be used to get all reminders. Call the corresponding
	 * method of Respository interface.
	 */

	public List<Reminder> getAllReminders() {

		return (List<Reminder>) reminderRepository.findAll();
	}

}
