package demo.jpa;

import java.util.List;

import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/people")
public class PeopleController {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PeopleController.class);
	
	@Autowired
	private PersonRepository personRepository;
	
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public HttpEntity<Person> registerPerson(@RequestBody Person person)
	{
		Person newPerson = personRepository.save(person);
		return new ResponseEntity<>(newPerson, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public List<Person> listPeople()
	{
		LOGGER.info("List People");
		return personRepository.findAll();
	}
	
	@RequestMapping(value="detail/{personId}", method=RequestMethod.GET)
	public HttpEntity<Person> detailPerson(@PathVariable(value="personId") Long id)
	{
		LOGGER.info("Retrieve person with id{}", id);
		if (!personRepository.exists(id)) {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Person>(personRepository.findOne(id), HttpStatus.OK);
	}
	
}
