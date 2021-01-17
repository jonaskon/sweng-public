package grading;

final class StringUtils {
    static String escapeCsv(String s) {
        // CSV escape logic: double quote -> double double quote (RFC 4180)
        s = s.replace("\"", "\"\"");
        // Excel logic: CRLFs or bust
        s = s.replace(System.lineSeparator(), "\r\n");
        // Quote it just in case
        return '"' + s + '"';
    }

    static String makeMarkdownParagraph(final String text) {
        final String markdown = text
                // Add two spaces at the end of each line to make it a single paragraph
                // Weird regex trick: to append to each line, enable multi-line mode, then match the end of lines.
                .replaceAll("(?m)$", "  ")
                // Escape bullets at the start of lines (again, enabling multi-line mode)
                .replaceAll("(?m)^-", "\\\\-")
                // Escape stars and underscores (bold/italic)
                .replaceAll("\\*", "\\\\*")
                .replaceAll("_", "\\_");

        // Make sure there isn't any accidental HTML
        return escapeHtml(markdown);
    }

    // https://stackoverflow.com/a/25228492
    private static String escapeHtml(String s) {
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
                out.append("&#");
                out.append((int) c);
                out.append(';');
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
}
