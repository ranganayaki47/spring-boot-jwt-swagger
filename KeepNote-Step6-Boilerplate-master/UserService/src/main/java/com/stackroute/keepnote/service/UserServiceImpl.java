package com.stackroute.keepnote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.repository.UserRepository;

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
public class UserServiceImpl implements UserService {

	/*
	 * Autowiring should be implemented for the UserRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository ) {
		this.userRepository = userRepository;
	}
	/*
	 * This method should be used to save a new user.Call the corresponding method
	 * of Respository interface.
	 */

	public User registerUser(User user) throws UserAlreadyExistsException {

		if(user != null) {
			User resultUser = userRepository.insert(user);
			if(resultUser != null)
				return resultUser;
			else
				throw new UserAlreadyExistsException("User already exists exception");
		}else
			throw new UserAlreadyExistsException("User already exists exception");
	}

	/*
	 * This method should be used to update a existing user.Call the corresponding
	 * method of Respository interface.
	 */

	public User updateUser(String userId,User user) throws UserNotFoundException {

		User userFound = userRepository.findById(userId).get();
		if(userFound.getUserId().equals(user.getUserId())) {
			userRepository.save(user);
			return user;
		}else {
			throw new UserNotFoundException("User record not found");
		}
	}

	/*
	 * This method should be used to delete an existing user. Call the corresponding
	 * method of Respository interface.
	 */

	public boolean deleteUser(String userId) throws UserNotFoundException {

		boolean flag = false;
		User resultUser = userRepository.findById(userId).get();		
		if(resultUser != null) {
			userRepository.delete(resultUser);
			flag = true;
		}else
			throw new UserNotFoundException("User record not found");
		
		return flag;
	}

	/*
	 * This method should be used to get a user by userId.Call the corresponding
	 * method of Respository interface.
	 */

	public User getUserById(String userId) throws UserNotFoundException {

		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User record not found"));
	}

}
