import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * 这个类用于展示实现了{@link Comparable}接口的{@link String}
 * 类型可以很方便地实现去重等操作。设置断点追踪可以看到，通过addAll->
 * add->put->compare->compareTo，最终调用了泛型类(这里是String)的compareTo
 * 方法，然后决定是新添加元素，还是修改原有元素的值。
 *
 * @author LightDance
 */
public class WordList {

    public static void main(String[] args) {
        Set<String> set = new TreeSet<>();
        String [] stringList = new String[5];
        for (int i = 0; i < stringList.length; i++) {
            stringList[i] = "bibibi";
        }
        stringList[1] = "alalal";

        stringList[4] = "bicidi";

        Collections.addAll(set ,stringList);

        System.out.println(set);
    }
}
