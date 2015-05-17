package es.jab.persistence.model;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name="NOTE")
public class Note extends Annotation {
	
	private String description;
	
	public Note(){
		this(0,"", null, new Date(), "");
	}
	
	public Note(String title, User user, String descripcion){
		this(0,title, user, new Date(), descripcion);
	}

	private Note(int id, String title, User user, Date creationDate, String description) {
		super(id, title, user, creationDate);
		this.setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setUser(User user) {
		if(user != null){
			user.addNote(this);
		}
		
	}
	
	@Override
	public void setCategory(Category category){
		if(category != null){
			category.addNote(this);
		}
		
	}

}
