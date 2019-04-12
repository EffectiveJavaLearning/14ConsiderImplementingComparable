import java.math.BigDecimal;
import java.util.HashSet;

/**
 * 用于说明compareTo(),equals()两个方法不一致时对集合类造成的影响.
 *
 * 由打印结果可知，equals的结果为false，
 * 但compareTo的结果为0，两者实际上是相等的，
 * 导致set中存在了两个值相等的元素。
 *
 * @author LightDance
 */
public class BigDecimalTest {
    public static void main(String[] args) {
        HashSet set = new HashSet();
        BigDecimal decimal1 = new BigDecimal("1.0");
        BigDecimal decimal2 = new BigDecimal("1.00");

        System.out.println(decimal1.equals(decimal2));
        System.out.println(decimal1.compareTo(decimal2));

        set.add(decimal1);
        set.add(decimal2);
        System.out.println(set);
    }

}
