package io;

public class Config {

  Integer rounds = null;
  String inputFile = null;
  String outputFile = null;

  public boolean isQuiet() {
    return quiet;
  }

  boolean quiet = false;

  public Integer getRounds() {
    return rounds;
  }

  public String getInputFile() {
    return inputFile;
  }

  public String getOutputFile() {
    return outputFile;
  }

  @Override
  public String toString() {
    return String
        .format("{rounds: %s, inputFile: %s, outputFile:%s}", rounds, inputFile, outputFile);
  }
}
