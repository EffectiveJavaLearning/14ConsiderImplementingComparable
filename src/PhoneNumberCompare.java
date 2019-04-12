/**
 * 用于演示多个字段的顺序比较
 *
 * @author LightDance
 */
public class PhoneNumberCompare {

    public static void main(String[] args) {
        PhoneNumber pn1 = new PhoneNumber((short) 339,(short)448,(short)5566);
        PhoneNumber pn2 = new PhoneNumber((short) 339,(short)448,(short)4566);

        System.out.println(pn1.compareTo(pn2));
    }
}
