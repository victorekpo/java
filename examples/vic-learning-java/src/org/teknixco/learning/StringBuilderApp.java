package org.teknixco.learning;

public class StringBuilderApp {
        public static void main(String[] args) {
                // Inefficient every time you create a string, it does not get replaced but a new string is created when you add to it, this can lead to memory issues.
                String info = "";
                info += "My name is Vic.";
                info += " ";
                info += "I am a software engineer.";
                System.out.println(info);

                //More efficient.
                StringBuilder sb = new StringBuilder(); //StringBuffer (for multiple threads)
                sb.append("My name is Vic.");
                sb.append(" ");
                sb.append("I am a software engineer.");
                System.out.println(sb.toString());


        }
}