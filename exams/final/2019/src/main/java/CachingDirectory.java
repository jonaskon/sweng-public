// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods/constructors
// You CAN add new private methods/constructors
// You CANNOT add interface implementations unless explicitly instructed to do so
// You CANNOT add new public, package-private or protected methods/constructors
// You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
// You CANNOT delete existing methods/constructors
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Directory with caching; searches are passed through the wrapped Directory,
 * and the CachingDirectory remembers the results such that it returns
 * these same results on subsequent searches of the same name,
 * without calling the underlying directory again.
 * --
 * Name comparisons for the purpose of caching are case-insensitive, and ignore leading and trailing spaces.
 */
final class CachingDirectory implements Directory {

    private Directory directory;
    private Map<String, List<Person>> cache;

    /**
     * Creates a new CachingDirectory wrapping the given Directory.
     *
     * @throws IllegalArgumentException if and only if the wrapped directory is null.
     */
    CachingDirectory(Directory wrapped) {
        if (wrapped == null) {
            throw new IllegalArgumentException("The wrapped directory cannot be null.");
        }
        this.directory = wrapped;
        this.cache = new HashMap<>();
    }

    @Override
    public List<Person> search(String name) {
        if (name == null) {
            throw new IllegalArgumentException("The provided name cannot be null.");
        }
        String searchTerm = name.toLowerCase().trim();
        if (searchTerm.isEmpty()) {
            throw new IllegalArgumentException("The provided name cannot be empty or only contain whitespaces.");
        }
        List<Person> res = cache.get(searchTerm);
        if (res == null) {
            res = directory.search(name);
            cache.put(searchTerm, res);
        }
        return res;
    }
}
