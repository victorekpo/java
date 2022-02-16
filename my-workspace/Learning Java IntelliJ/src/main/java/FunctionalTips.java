import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionalTips
{
    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Person 1", Gender.MALE),
                new Person("Maria", Gender.FEMALE),
                new Person("Theodore", Gender.MALE),
                new Person("Crystal", Gender.FEMALE)
        );

        List<String> females = new ArrayList<>();
        for(Person person : people) {
            if(Gender.FEMALE.equals(person.gender)) {
                String p = returnJSON(person);
                females.add(p);
            }
        }
        System.out.println(females);


        //Declarative Approach
        people.stream()
                .filter(person -> Gender.FEMALE.equals(person.gender))
                .collect(Collectors.toList())
                .forEach(System.out::println);




        int increment2 = incrementOne.apply(1);
        System.out.println(increment2);
    }

   //OLD WAY TO DECLARE A FUNCTION
    static int increment(int number) {
        return number + 1;
    }

    //FUNCTIONAL DECLARATION
    static Function<Integer, Integer> incrementOne = number -> number + 1;

    public static String returnJSON(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        String s = null;
        try {
            String json = mapper.writeValueAsString(obj);
            s = json;
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }
    public static class Person {
        private final String name;
        private final Gender gender;

        Person(String name, Gender gender) {
            this.name = name;
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public Gender getGender() {
            return gender;
        }
    }

    enum Gender {
        MALE, FEMALE
    }
}
