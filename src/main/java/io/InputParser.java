package io;

import java.util.regex.Pattern;

public class InputParser {

  private static final String ARG_IN = "in";
  private static final String ARG_OUT = "out";
  private static final String ARG_ROUNDS = "rounds";
  private static final String ARG_QUIET = "quiet";
  private static final String KEY_REGEX = "-(.+)";
  private static final String OPT_REGEX = "--(.+)";
  private static final Pattern KEY_PAT = Pattern.compile(KEY_REGEX);
  private static final Pattern OPT_PAT = Pattern.compile(OPT_REGEX);

  public static Config parseConfig(String[] argv) {
    Config config = new Config();
    for (int i = 0; i < argv.length; i++) {
      String word = argv[i];
      if (word.matches(OPT_REGEX)) {
        String option = word.substring(2);
        parse(config, option);
      } else if (word.matches(KEY_REGEX)) {
        if (++i == argv.length) {
          break;
        }

        String key = word.substring(1);
        String value = argv[i];
        parse(config, key, value);
      }
    }

    return config;
  }

  private static void parse(Config config, String option) {
    switch (option) {
      case ARG_QUIET:
        config.quiet = true;
        break;
      default:
    }
  }

  private static void parse(Config config, String key, String value) {
    switch (key) {
      case ARG_IN:
        config.inputFile = value;
        break;

      case ARG_OUT:
        config.outputFile = value;
        break;

      case ARG_ROUNDS:
        config.rounds = Integer.parseInt(value);
        break;

      case ARG_QUIET:
        config.quiet = Boolean.parseBoolean(value);
      default:
    }
  }
}
