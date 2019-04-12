import java.util.Comparator;
import java.util.function.ToIntFunction;

/**
 * {@link  Comparable#compareTo(Object)}方法虽然非常常用，但并没有放在Object类里面，
 * 它是{@link Comparable}接口中唯一的方法。这个方法不仅可以实现类似equals的简单等同性比较，
 * 还能执行顺序比较，而且支持泛型。实现{@link Comparable}接口，就表示它的实例具有内在的顺序关系。
 *
 * 对于实现了该接口的对象来说，排序、搜索、计算极值、自动调整顺序等操作都变得很简单。
 * 比如当实例数组a所在的类实现了{@link Comparable}接口时，就可以用{@link java.util.Arrays#sort}，
 * 这样进行排序：Arrays.sort(a);，一句话完事儿。或者可以看这个关于TreeSet的例子{@link WordList},
 * 通过compareTo实现了去重。
 *
 * implementing Comparable接口后，我们自己编写的类就可以与所有依赖这个接口的通用算法和泛型算法协作，
 * 而不需要自己动手从头实现，省去了很多不必要的工作。Java中所有的值类(value class)和枚举类型
 * 都实现了这个接口。当我们在编写带有明显顺序特征的类的时候，比如涉及字母顺序、时间顺序、数字顺序的类，
 * 就应该implements Comparable一下。
 *
 * compareTo()的通用规约跟equals()很像：
 *      *.将对象和指定对象进行比较，根据两者大小关系返回同一个正整数、零或者负整数(通常用-1,0,1)，
 *     如果两者无法进行比较则抛出{@link ClassCastException}异常
 *     **.这里用sgn表示数学中的sig-num函数
 *     规约如下：
 *         1.自己实现的compareTo()方法对任意x,y应有sgn(x.compareTo(y)) = - sgn(y.compareTo(x))，
 *         否则就扔一个异常出来
 *         2.这种比较关系应该是可以传递的：(x.compareTo(y) > 0 && y.compare(z) > 0 )则应有
 *         (x.compare(z) > 0)对任意满足条件的x,y,z均成立
 *         3.当sgn(x.compare(y)) == 0 时，应对任意的z有sgn(x.compare(z)) == sgn(y.compare(z))
 *         4.强烈建议(x.compare(y) == 0) == x.equals(y) .虽然并不是强制要求，
 *         但如果实现该接口的类违反了这个规则，就有必要在文档中明确地予以说明，比如：
 *         “注意：该类的内置排序放方法与equals()方法不一致”。这种情况下需要非常小心。
 *         如果是一个单独的类出现这种情况可能还好，但如果一个有序集合出现了这种情况就会十分混乱，
 *         它们很可能无法遵守集合接口(Collection,Map,Set等)的通用规约。因为这些集合接口的通用规约是基于
 *         equals()方法定的，而其等同性测试却基于compareTo()方法。比如这个关于BigDecimal
 *         的例子:{@link BigDecimalTest}
 *
 * 与equals()方法不同的是，compareTo()无需对不同的类进行比较。实例的类型不同时扔个
 * {@link ClassCastException}就好。虽然通用规约确实允许实现跨类比较，但这些一般会在被比较对象
 * 所实现的接口中定义说明一下。
 *
 * 跟hashCode()类似，如果随意违反compareTo()的通用规约，就会影响那些依赖于该方法的类正常工作，
 * 例如{@link java.util.TreeSet},{@link java.util.TreeMap},{@link java.util.Collections},
 * {@link java.util.Arrays}等靠这个方法实现排序和搜索的类。
 *
 * compareTo()同样要遵循自反性、传递性、对称性，因此在equals中的说明与“权宜之计”换到这里也同样适用：
 * 无法在子类中添加新的成员变量的同时保持compareTo通用规约，除非放弃面向对象的优势；
 * 可以编写一个不相关的类，持有需要扩展的类的一个实例，然后添加新的成员变量，通过“视图方法”
 * (view method)更自由地实现compareTo()方法
 *
 * 编写compareTo()方法和编写equals()挺像的，但有几处关键的区别。由于Comparable接口是参数化的
 * (parameterized)，并且在接口中规定了要返回的类型（通过泛型），因此无需考虑类型检测、类型转换问题，
 * 如果类型不一致可以直接扔{@link ClassCastException}异常；如果传进来个空指针(null)
 * 就应该在试图访问它的内部数据之前扔{@link NullPointerException}出来。
 *
 * compareTo()方法的侧重点在于顺序关系而非是否相等，比较时应递归地、按顺序地调用两实例中所对应的、
 * 各成员变量的compareTo()方法。如果某个成员变量没有实现{@link Comparable}接口，那么可以考虑使用
 * 比较器(Comparator).可以考虑自己编写一个Comparator，也可以用现成的，实现一下
 * {@link java.util.Comparator}，比如讲equals时举的那个不分大小写字符串比较的例子。这里要注意，
 * 实现Comparator接口时需要传入一个泛型，即implements Comparator<T>,然后其只能跟同样
 * implements Comparator<T>的类进行比较，一般会要求T相同。
 *
 * !.注意，《Effective Java 2nd》或之前的版本中，会建议大于号(>)或者小于号(<)比较整型(int,long等)，
 *  用{@link Float#compare(float, float)}或者{@link Double#compare(double, double)}比较浮点型。
 *  但在Java7中，所有基本类型的装箱类都已有了静态的比较方法，比如{@link Integer#compare(int, int)}。
 * 这种情况下，使用大于小于显然更为冗长，且容易出错。
 *
 * 当有多个需要比较的字段时，顺序很重要。从最重要的那个开始，按一定的比较方式逐个比较，
 * 直到某一对字段的比较结果中出现非零值，或者全部字段比较完毕。比如：{@link PhoneNumberCompare}
 *
 * 在Java8中，{@link java.util.Comparator}接口配置了一系列构造方法，很方便就可以搞一个比较器
 * (comparator)出来，然后通过这个比较器实例实现compareTo()方法。它用很少的性能代价换去了简洁易读的代码。
 * 当使用这种方式时，可以考虑配套使用Java的静态导入(static import)工具，这样可以让程序更加清晰，
 * 比如{@link PhoneNumber2}中的比较方法。
 *
 * 但是，有时候会看到类似这种基于两者差值的实现方式{@link #hashCodeComparator1},严重不推荐使用这种方式，
 * 因为这种方式存在整形溢出和违反IEEE754浮点数标准的风险，而其效率也未见得比前面介绍的几种方法快多少。
 *
 * 总之，希望自己的类能够实现合理的排序等操作时，应该最先考虑{@link Comparable}接口；
 * 当使用{@link Comparator}进行排序时，首先考虑comparing()方法，而不要用大于或者小于号。
 *
@author LightDance
 */
public class ConsiderImplementingComparable {

    static Comparator<Object> hashCodeComparator1 = new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            return o1.hashCode() - o2.hashCode();
        }
    };

    /**用于对比一下的Comparator*/
    static Comparator<Object> hashCodeComparator2 = new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            return Integer.compare(o1.hashCode(), o2.hashCode());
        }
    };
}

