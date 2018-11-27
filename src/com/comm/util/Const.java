package com.comm.util;

public interface Const {
    /**
     * 外部调用HTTP请求超时时间：连接
     */
    public static final int HTTP_REQUEST_TIMEOUT_CONN = 10000;
    /**
     * 外部调用HTTP请求超时时间：读取
     */
    public static final int HTTP_REQUEST_TIMEOUT_READ = 10000;
    /**
     * 共通CONTROLLER：结果KEY
     */
    public static final String COMM_CTRL_RST_KEY = "result";
    /**
     * 共通CONTROLLER：结果-成功
     */
    public static final String COMM_CTRL_RST_VAL_SUCC = "0";
    /**
     * 共通CONTROLLER：结果-失败
     */
    public static final String COMM_CTRL_RST_VAL_FAIL = "1";
    /**
     * 消息中心短信发送接口：发送成功
     */
    public static final String SMU_SEND_SUCC = "0";
    /**
     * 消息中心短信发送接口：失败
     */
    public static final String SMU_SEND_FAIL = "1";
    /**
     * 开始位置
     */
    String START = "start";
    /**
     * 显示条数
     */
    String LIMIT = "limit";
    /**
     * 开始条数
     */
    String STARTNUMBER = "0";
    /**
     * 结束条数
     */
    String LIMITNUMBER = "15";
    /**
     * 设备类型0
     */
    String DEVICETYPE_ANDROID = "Android";
    /**
     * 设备类型1
     */
    String DEVICETYPE_IOS = "iOS";
    /**
     * 资源类型_文字
     */
    String RESOURCETYPE_TEXT = "100";
    String RESOURCETYPE_STORY = "101";
    /**
     * 资源类型_图片
     */
    String RESOURCETYPE_IMG = "200";
    /**
     * 资源类型_封面图
     */
    String RESOURCETYPE_COVERIMG = "201";
    /**
     * 资源类型_相册图
     */
    String RESOURCETYPE_PHOTOIMG = "202";
    /**
     * 资源类型_声音
     */
    String RESOURCETYPE_VOICE = "300";
    /**
     * 资源类型_视频
     */
    String RESOURCETYPE_VIDEO = "400";
    /**
     * 图片保存路径
     */
    String SAVEURL_IMG = "/var/spotinfo/img/";
    /**
     * oss endpoint
     */
    String ENDPOINT = "endpoint";
    
    /**
     * oss accessKeyId
     */
    String ACCESSKEYID = "accessKeyId";
    
    /**
     * oss accessKeySecret
     */
    String ACCESSKEYSECRET = "accessKeySecret";
    
    /**
     * oss bucketName
     */
    String BUCKENNAME = "bucketName";
    
    /**
     * 滚动条导览表的iteam类型  1：小说
     */
    String SI_ITEAM_TYPE_STORY = "1";
    
    /**
     * 滚动条导览表的iteam类型  2：广告
     */
    String SI_ITEAM_TYPE_AD = "2";
    
    /**
     * 滚动条导览表的iteam类型  9：其他
     */
    String SI_ITEAM_TYPE_OTHER = "9";
    
    /**
     * 文件存放目录prefix
     */
    String LOCAL_PATH = "/var/miwei/books";
    
    
}
