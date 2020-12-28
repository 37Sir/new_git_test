package test.snowflake;

/**
 * @author yidingzhao
 * @version 1.0
 * @since 2020-12-28 14:46
 */
public class Snowflake {
    final static char[] digits = { '0', '1', '3', '2', '4', '7', '6', '5', '8',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '0', '1', };

    public static void main(String[] args) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        System.out.println(idWorker.nextId());
        long code = idWorker.nextId();
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << 6;
        long mask = radix - 1;
        do {
            buf[--charPos] = digits[(int) (code & mask)];
            code >>>= 6;
        } while (code != 0);
        System.out.println(new String(buf, charPos, (64 - charPos)));
    }
}
