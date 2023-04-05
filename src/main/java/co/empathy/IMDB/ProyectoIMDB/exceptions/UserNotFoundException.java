package co.empathy.IMDB.ProyectoIMDB.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(long id) {
        super("User not found with id " + id);
    }
}
