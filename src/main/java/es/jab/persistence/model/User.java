package es.jab.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="USER")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String surname;
	
	private String email;
	
	private String password;
	
	@Column(length=1000)
	private String token;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Note> notes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Reminder> reminders;
	
	public User(){
		this(0, "", "", "", "");
	}
	
	public User(String name, String surname, String email, String password, String passwordAgain) throws Exception{
		this(0, name, surname, email, password);
		if(!password.equals(passwordAgain)){
			throw new Exception();
		}
	}
	
	public User(int id, String name, String surname, String email, String password) {
		super();
		this.setId(id);
		this.setName(name);
		this.setSurname(surname);
		this.setEmail(email);
		this.setPassword(password);
		this.setNotes(new ArrayList<Note>());
		this.setReminders(new ArrayList<Reminder>());
		this.setToken("");
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
	@Override
	public String toString(){
		return this.id + ": " + this.name + " " + this.surname;
	}

	public List<Note> getNotes() {
		return notes;
	}

	private void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<Reminder> getReminders() {
		return reminders;
	}

	private void setReminders(List<Reminder> reminders) {
		this.reminders = reminders;
	}
	
	public void addNote(Note note) throws NullPointerException{
		if(note == null){
			throw new NullPointerException();
		}
		this.notes.add(note);
		note.user = this;
	}
	
	public void removeNote(Note note) throws NullPointerException{
		if(note == null){
			throw new NullPointerException();
		}
		note.user = null;
		this.notes.remove(note);
	}
	
	public void addReminder(Reminder reminder) throws NullPointerException{
		if(reminder == null){
			throw new NullPointerException();
		}
		this.reminders.add(reminder);
		reminder.user = this;
	}
	
	public void removeReminder(Reminder reminder) throws NullPointerException{
		if(reminder == null){
			throw new NullPointerException();
		}
		reminder.user = null;
		this.reminders.remove(reminder);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
