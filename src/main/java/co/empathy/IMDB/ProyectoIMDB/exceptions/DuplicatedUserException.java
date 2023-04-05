package co.empathy.IMDB.ProyectoIMDB.exceptions;

public class DuplicatedUserException extends Exception{
    public DuplicatedUserException(long id) {
        super("User already exists with id " + id);
    }
}

