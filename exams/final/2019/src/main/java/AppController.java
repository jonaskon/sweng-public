// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods/constructors
// You CAN add new private methods/constructors
// You CANNOT add interface implementations unless explicitly instructed to do so
// You CANNOT add new public, package-private or protected methods/constructors
// You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
// You CANNOT delete existing methods/constructors
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class AppController {
    private final Directory directory;

    /**
     * Creates a new controller that uses the given directory.
     *
     * @throws IllegalArgumentException if and only if the directory is null.
     */
    AppController(Directory directory) {
        if (directory == null) {
            throw new IllegalArgumentException("Directory cannot be null.");
        }

        this.directory = directory;
    }

    /**
     * Handles the given user input, returning the corresponding view.
     * Ignores leading and trailing spaces in the input.
     * If the input is invalid, returns null.
     * --
     * The input can either be empty, which leads to the main view, or non-empty, in which case it must be a command followed by text.
     * The command and the text are separated by one or more spaces; the text itself can contain spaces.
     * If the text is empty even though the input is non-empty, the input is invalid.
     * If the command is unknown, the input is invalid.
     *
     * @throws IllegalArgumentException if and only if the input is null.
     */
    AppView handle(String input) {
        if (input == null) {
            throw new IllegalArgumentException("The input cannot be null.");
        }
        String[] in = input.trim().split(" ");
        String cmd = null;
        AppView res = null;
        if (in.length == 0) {
            cmd = "goToMain";
        }
        if (in.length != 1) {
            cmd = in[0];
        }
        if (cmd == "goToMain") {
            res = new AppView("Welcome!", Arrays.asList("show", "search"));
        } else if (cmd == "show") {
            StringBuilder searchTerm = new StringBuilder();
            for (int i=1; i<in.length; ++i) {
                searchTerm.append(in[i]);
                searchTerm.append(" ");
            }
            List<Person> searchRes = directory.search(searchTerm.toString());
            String description = "The search did not return exactly one person.";
            if (searchRes.size() == 1) {
                description = searchRes.get(0).toString();
            }
            res = new AppView(description, Arrays.asList("show", "search"));
        } else if (cmd == "search") {
            StringBuilder searchTerm = new StringBuilder();
            for (int i=1; i<in.length; ++i) {
                searchTerm.append(in[i]);
                searchTerm.append(" ");
            }
            List<Person> searchRes = directory.search(searchTerm.toString());
            String description = "The search returned no results.";
            StringBuilder descriptionBuilder = new StringBuilder();
            if (!searchRes.isEmpty()) {
                for (Person pers:searchRes) {
                    descriptionBuilder.append(pers.name + " ");
                }
                description = descriptionBuilder.toString().trim();
            }
            res = new AppView(description, Arrays.asList("show", "search"));
        }
        return res;
    }
}
