package cyclops.container.control.reader;

public interface UserRepository {

	public User get(int id);
	public User find(String username);
}
