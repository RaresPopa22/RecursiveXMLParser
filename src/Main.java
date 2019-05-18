import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {

    try {
      RecursiveDOMRefactor recursiveDOM = new RecursiveDOMRefactor("./classes.xml");
      Map<String, Integer> map = recursiveDOM.getFields();

      Map.Entry maxEntry = map.entrySet().stream()
              .max(Map.Entry.comparingByValue())
              .get();


      for (int i = 0; i < (int) maxEntry.getValue(); i++) {
        System.out.println("*** AT DEPTH " + i);
        int constant = i;
        map.entrySet().stream()
                .filter(entry -> entry.getValue() == constant)
                .forEach(entry -> System.out.println(entry.getKey()));
      }

    } catch (IOException | SAXException | ParserConfigurationException e) {
      e.printStackTrace();
    }
  }

  // to get alias use this
  private static String getAlias(String className) {
    return Arrays.stream(className.split(""))
            .filter(s -> Character.isUpperCase(s.charAt(0)))
            .map(s -> s.toLowerCase())
            .collect(Collectors.joining(""));
  }
}
