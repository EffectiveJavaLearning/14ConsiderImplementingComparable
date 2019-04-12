/**
 * 跟{@link PhoneNumberCompare}配套的电话号码类
 *
 * @author LightDance
 */
class PhoneNumber implements Comparable<PhoneNumber> {
    private short areaCode;
    private short prefix;
    private short lineNum;

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

    public PhoneNumber(short areaCode, short prefix, short lineNum) {
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNum = lineNum;
    }

    @Override
    public String toString() {
        return areaCode + "-" + prefix + "-" + lineNum;
    }

    @Override
    public int compareTo(PhoneNumber pn) {
        int result = Short.compare(areaCode, pn.areaCode);
        System.out.println("areaCode 比较完毕");
        if (result == 0) {
            result = Short.compare(prefix, pn.prefix);
            System.out.println("prefix 比较完毕");
            if (result == 0) {
                result = Short.compare(lineNum, pn.lineNum);
                System.out.println("lineNum 比较完毕");
            }
        }
        return result;
    }
}