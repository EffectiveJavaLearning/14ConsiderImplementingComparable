import java.util.Comparator;
import java.util.function.ToIntFunction;
//静态导入简化代码
import static java.util.Comparator.comparingInt;

/**
 * 使用了{@link java.util.Comparator}实现compareTo()的电话号码类
 *
 * 它先后调用了{@link Comparator#comparingInt(ToIntFunction)}
 * 和{@link Comparator#thenComparingInt(ToIntFunction)}，通过lambda表达式提取泛型类中的int型字段，
 * 对两个实例进行比较，然后返回一个Comparator<PhoneNumber2>类型的比较器，这样就可以自由地进行任意顺序、
 * 任意个数的字段比较。而调用也十分简单，如果没什么特殊需求的话，由于Java8中引入了接口中方法的默认实现，
 * 因而可以直接调用其默认的compare(x,y)方法进行比较。
 *
 * 另外，我们无需指定传递给Comparator作参数的方法的返回类型(比如{@link #COMPARATOR}中没有使用强制转换)，
 * 因为Java的类型推断现在已经可以帮我们解决这个了。
 *
 * 对于long和double来说，Comparator同样也有类似于comparingInt和thenComparingInt的对应方法；
 * 并且，comparingInt或者comparingDouble都同样适用于更短的short型或者float型变量。
 *
 * @author LightDance
 */
class PhoneNumber2 implements Comparable<PhoneNumber2> {
    private short areaCode;
    private short prefix;
    private short lineNum;

    /**比较器，通过方法引用进一步简化代码*/
    public static final Comparator<PhoneNumber2> COMPARATOR =
            comparingInt(PhoneNumber2::getAreaCode)
                    .thenComparingInt(PhoneNumber2::getPrefix)
                    .thenComparingInt(PhoneNumber2::getLineNum);


    public short getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(short areaCode) {
        this.areaCode = areaCode;
    }

    public short getPrefix() {
        return prefix;
    }

    public void setPrefix(short prefix) {
        this.prefix = prefix;
    }

    public short getLineNum() {
        return lineNum;
    }

    public void setLineNum(short lineNum) {
        this.lineNum = lineNum;
    }

    public PhoneNumber2(short areaCode, short prefix, short lineNum) {
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNum = lineNum;
    }

    @Override
    public String toString() {
        return areaCode + "-" + prefix + "-" + lineNum;
    }

    @Override
    public int compareTo(PhoneNumber2 pn) {
        return COMPARATOR.compare(this , pn);
    }

}