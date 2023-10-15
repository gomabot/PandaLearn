package com.gomabot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static Optional<String> getFirstGroupOf(final String text, final Pattern regex) {
        final Optional<List<String>> groups = getGroupsOf(text, regex);
        return groups.map(strings -> strings.get(0));
    }

    public static Optional<List<String>> getGroupsOf(final String text, final Pattern regex) {
        final Matcher matcher = regex.matcher(text);
        if (!matcher.find()) {
            return Optional.empty();
        }
        final List<String> groups = new ArrayList<>();
        for (int i = 1; i <= matcher.groupCount(); i++) {
            groups.add(matcher.group(i));
        }
        return Optional.of(groups);
    }
}
