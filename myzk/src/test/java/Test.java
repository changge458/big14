import java.util.Random;

public class Test {

    public static void main(String[] args) {
        Random r = new Random();
        //(0~123321)+123344
        int i = r.nextInt(1222322) + 123344;

        double d = (double) i / 10000000;

        System.out.println(d);
    }
}
