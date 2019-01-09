public class main {
    public static void main(String args[]) {
        String test1 = "hi";
        String test2 = "hi";
        String something="hi1";
        if(test1==something.substring(0,2))
            System.out.println("HELLO");
        System.out.println("it is " + (test1 == test2));
        System.out.println("it is " + test1 + " " + test2);
        String s1 = "testString";
        String s2 = "testString";
        if(s1 == s2)System.out.println("equals!");
    }
}
