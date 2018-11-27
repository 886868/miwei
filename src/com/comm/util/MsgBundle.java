package com.comm.util;

public class MsgBundle {
    //登录用户表
    public static final String LOGINLIST_ACCOUNT = "account";//
    
    //与手机端的交互
	public static final String MSG_TYPE = "msgType";//信息类型
	public static final String TYPE_ERR_UPLOAD = "typeErrUpload";//错误文件上传
	public static final String TYPE_PIC_USER_UPLOAD = "typePicUserUpload";//功能设置图片上传
    public static final String RET_SUC = "1";//后台处理成功
    public static final String RET_FAILURE = "0";//后台处理失败
    
    //功能设置
    public static final String RET_PWD_ERR = "2";//修改密码时旧密码不对
    
	//照片本地缓存名称
	public static final String PIC_NAME_SMALL = "picNameSmall.jpg";
	public static final String PIC_NAME_BIG = "picNameBig.jpg";
    
    //百度推送
    public static final String BD_PUSH_TYPE_FAMILY_UPDATE = "bdPushTypeFamilyUpdate";//亲友更新推送
    public static final String BD_DINGWEI = "dingwei";//定位推送
    public static final String BD_PK = "pk";//定位推送
    
    //返回结果
    public static final String RESULT_SUCCESS = "resultSuccess";//成功
    public static final String RESULT_FAILURE = "resultFailure";//失败
    public static final String RESULT = "result";//字段名
    public static final String RES = "res";//字段名
    public static final String RESULT_TWO = "2";//失败,带原因字段
    public static final String RESULT_ONE = "1";//成功
    public static final String RESULT_ZERO = "0";//失败
    public static final String REASON = "reason";//失败原因
    public static final String COL_OPERATOR_TYPE = "colOptType";//对字段的操作类型
    public static final String COL_OPERATOR_TYPE_ADD = "0";//新增
    public static final String COL_OPERATOR_TYPE_DEL = "1";//删除
    public static final String COL_OPERATOR_TYPE_UPD = "2";//修改

    //亲友
    public static final String RESULT_FAMILY_ADD_EXISTS = "-1";//要添加的亲友和您已经是亲友了
    public static final String RESULT_FAMILY_DEL_SUCCESS = "resultFamilyDelSuccess";//删除亲友信息成功
    public static final String RESULT_FAMILY_DEL_FAILURE = "resultFamilyDelFailure";//删除亲友信息失败
    
    //号百
    public static final String BESTTONE_RESULT = "result";//返回的字段名
    public static final String BESTTONE_VALIDATE_SUC = "0";//验证码发送成功
    public static final String BESTTONE_PAY_SUC = "0";//支付成功
    public static final String BESTTONE_PAY_FAILURE = "1";//支付失败
    
    public static final String BESTTONE_AUTHORIZE_TYPE = "2";//申请短信授权
    
    //表格
    public static final String TAB_FAMILY = "family";
    public static final String TAB_RAIL = "Rail";
	public static final String TAB_MESSAGE = "message";
	public static final String TAB_MONITOR = "monitor";
	public static final String TAB_PARAM = "param";
	public static final String TAB_LOGINLIST = "loginlist";
	public static final String TAB_TIMING_LOCATION = "timinglocation";

	//表公用
    public static final String SEQ = "seq";
    public static final String UPTIME = "upTime";
    public static final String FACCOUNT = "f_account";
    public static final String ACCOUNT = "account";

    //表:亲友
    public static final String F_AUTHORIZE_YES = "1";
    public static final String F_AUTHORIZE_NO = "0";
    public static final String F_AUTHORIZE_YES_DES = "授权";
    public static final String F_AUTHORIZE_NO_DES = "未授权";
    public static final String PAYFLG_YES = "1";
    public static final String PAYFLG_NO = "0";
    public static final String PAYFLG_YES_DES = "已支付";
    public static final String PAYFLG_NO_DES = "未支付";
    public static final String DELETE_FLG_YES = "1";//已删除
    public static final String DELETE_FLG_NO = "0";//未删除
    public static final String SEND_FLG_NO = "0";//不发送
    public static final String SEND_FLG_YES = "1";//发送
    
    //表:用户
    public static final int LOGINLIST_EDITTYPE_ZERO = 0;//修改密码
    public static final int LOGINLIST_EDITTYPE_ONE = 1;//修改昵称
    public static final int LOGINLIST_EDITTYPE_TWO = 2;//修改邮箱
    public static final String LOGINLIST_EDITTYPE = "loginlistEditType";//修改用户信息
}
