package test.snowflake;

import org.apache.commons.lang3.RandomUtils;

import java.util.Date;

/**
 * @author yidingzhao
 * @version 1.0
 * @since 2020-12-28 14:48
 */
public class SnowflakeIdWorker {
    private static SnowflakeIdWorker idWorker;

    // TODO 閮ㄧ讲澶氬彴鏈嶅姟,杩欓噷鍙互浜ょ粰 Spring 绠＄悊,娉ㄥ叆workerId
    static {
        idWorker = new SnowflakeIdWorker(1);
    }

    /** 寮�濮嬫椂闂存埅 (1980-01-01) FIXME 淇濊瘉鐢熸垚鐨勭紪鍙锋湁13浣�*/
//    private static final long twepoch = 315504000000L;
    /**
     * 寮�濮嬫椂闂存埅 (2019-07-01)
     */
    private static final long twepoch = 1561910400000L;

    /**
     * 鏃堕棿浣嶅彇&
     */
    private static final long timeBit = 0b1111111111111111111111111111111111111111110000000000000000000000L;

    /**
     * 鏈哄櫒id鎵�鍗犵殑浣嶆暟
     */
    private final long workerIdBits = 10L;

    /**
     * 鏁版嵁鏍囪瘑id鎵�鍗犵殑浣嶆暟
     */
    private final long datacenterIdBits = 0L;

    /**
     * 鏀寔鐨勬渶澶ф満鍣╥d锛岀粨鏋滄槸1023 (杩欎釜绉讳綅绠楁硶鍙互寰堝揩鐨勮绠楀嚭鍑犱綅浜岃繘鍒舵暟鎵�鑳借〃绀虹殑鏈�澶у崄杩涘埗鏁�)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 鏀寔鐨勬渶澶ф暟鎹爣璇唅d锛岀粨鏋滄槸0
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * 搴忓垪鍦╥d涓崰鐨勪綅鏁�
     */
    private final long sequenceBits = 12L;

    /**
     * 鏈哄櫒ID鍚戝乏绉�12浣�
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 鏁版嵁鏍囪瘑id鍚戝乏绉�22浣�(12+10)
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 鏃堕棿鎴悜宸︾Щ22浣�(10+0+12)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 鐢熸垚搴忓垪鐨勬帺鐮侊紝杩欓噷涓�4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 宸ヤ綔鏈哄櫒ID(0~1023)
     */
    private long workerId;

    /**
     * 鏁版嵁涓績ID(0)
     */
    private long datacenterId;

    /**
     * 姣鍐呭簭鍒�(0~4095)
     */
    private long sequence;

    private long initSequence;

    /**
     * 涓婃鐢熸垚ID鐨勬椂闂存埅
     */
    private long lastTimestamp = -1L;

    //==============================Constructors=====================================

    /**
     * 鏋勯�犲嚱鏁�
     *
     * @param workerId 宸ヤ綔ID (0~1023)
     */
    private SnowflakeIdWorker(long workerId) {
        this(workerId, 0);
    }

    /**
     * 鏋勯�犲嚱鏁�
     *
     * @param workerId     宸ヤ綔ID (0~1023)
     * @param datacenterId 鏁版嵁涓績ID (0)
     */
    public SnowflakeIdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = 0;
        //闅忔満鐢熸垚2000绉嶅彲鑳�
        this.initSequence = RandomUtils.nextInt(0, 2000);
        this.sequence = initSequence;
        //FIXME 1ms鍐呮湁2000绉嶅彲鐚滄祴
    }

    // ==============================Methods==========================================

    /**
     * 鑾峰緱涓嬩竴涓狪D (璇ユ柟娉曟槸绾跨▼瀹夊叏鐨�)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //濡傛灉褰撳墠鏃堕棿灏忎簬涓婁竴娆D鐢熸垚鐨勬椂闂存埑锛岃鏄庣郴缁熸椂閽熷洖閫�杩囪繖涓椂鍊欏簲褰撴姏鍑哄紓甯�
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //濡傛灉鏄悓涓�鏃堕棿鐢熸垚鐨勶紝鍒欒繘琛屾绉掑唴搴忓垪
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //姣鍐呭簭鍒楁孩鍑�
            if (sequence == 0) {
                //闃诲鍒颁笅涓�涓绉�,鑾峰緱鏂扮殑鏃堕棿鎴�
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //鏃堕棿鎴虫敼鍙橈紝姣鍐呭簭鍒楅噸缃�
        else {
            sequence = initSequence;
        }

        //涓婃鐢熸垚ID鐨勬椂闂存埅
        lastTimestamp = timestamp;

        //绉讳綅骞堕�氳繃鎴栬繍绠楁嫾鍒颁竴璧风粍鎴�64浣嶇殑ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     * 浠嶪D涓幏鍙栨椂闂�
     *
     * @param id 鐢辨绫荤敓鎴愮殑ID
     */
    public static Date getTime(long id) {
        return new Date(((timeBit & id) >> 22) + twepoch);
    }

    /**
     * 闃诲鍒颁笅涓�涓绉掞紝鐩村埌鑾峰緱鏂扮殑鏃堕棿鎴�
     *
     * @param lastTimestamp 涓婃鐢熸垚ID鐨勬椂闂存埅
     * @return 褰撳墠鏃堕棿鎴�
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 杩斿洖浠ユ绉掍负鍗曚綅鐨勫綋鍓嶆椂闂�
     *
     * @return 褰撳墠鏃堕棿(姣)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 鑾峰彇鍞竴ID
     */
    public static Long getId() {
        return idWorker.nextId();
    }

    /**
     * 鑾峰彇闅忔満瀛楃涓�,length=13
     */
    public static String getRandomStr() {
        return Long.toString(idWorker.nextId(), Character.MAX_RADIX);

    }
}