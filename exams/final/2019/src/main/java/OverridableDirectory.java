// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods/constructors
// You CAN add new private methods/constructors
// You CANNOT add interface implementations unless explicitly instructed to do so
// You CANNOT add new public, package-private or protected methods/constructors
// You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
// You CANNOT delete existing methods/constructors
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Directory with overrides for specific searches; searches are passed through the wrapped Directory,
 * and if any override matches the given name, the associated person is returned at the beginning of the results.
 * --
 * Name comparisons for the overrides are case-insensitive, and ignore leading and trailing spaces.
 */
final class OverridableDirectory implements Directory {
    Map<String, Person> overrides;
    Directory wrapped;

    /**
     * Creates a new OverridableDirectory wrapping the given Directory, with the given overrides.
     *
     * @throws IllegalArgumentException if and only if at least one of the following issues arises:
     *                                  the wrapped directory is null, or if the overrides map is null,
     *                                  or the overrides map contains null values,
     *                                  or the overrides map contains multiple keys that are equal given the comparison criteria.
     */
    OverridableDirectory(Directory wrapped, Map<String, Person> overrides) {
        if (wrapped == null || overrides == null) {
            throw new IllegalArgumentException("Neither the wrapped directory nor the overrides can be null.");
        }
        Set<String> allKeys = new HashSet<>();
        for (String key:overrides.keySet()) {
            Person val = overrides.get(key);
            if (val == null) {
                throw new IllegalArgumentException("Overrides cannot contain a null value.");
            }
            String pKey = key.toLowerCase().trim();
            if (allKeys.contains(pKey)) {
                throw new IllegalArgumentException("At least two values specified for key "+key+" in the overrides.");
            }
            allKeys.add(pKey);
        }
        this.overrides = overrides;
        this.wrapped = wrapped;
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
        List<Person> res = null;
        for (String key:overrides.keySet()) {
            if (searchTerm == key.toLowerCase().trim()) {
                res = new ArrayList<Person>();
                res.add(overrides.get(key));
            }
        }
        if (res == null) {
            res = wrapped.search(searchTerm);
        }
        return res;
    }
}
