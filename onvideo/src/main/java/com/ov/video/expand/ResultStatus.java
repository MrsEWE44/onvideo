package com.ov.video.expand;
public enum  ResultStatus {
    success(0,"请求成功"),
    failure(1,"请求失败"),
    error(2,"请求错误"),
    authorization_into_page(-101,"当前用户无权访问"),
    article_commits(32,"暂无相关数据,无法查询"),
    authorization_login(-102,"当前用户无权登录"),
    authorization_admin_change(-103,"当前用户无权操作"),
    login_failure(5,"登录失败"),
    regUser(10,"所注册用户已存在"),
    logout(4,"注销登录成功"),
    upload_file_error(44,"文件上传失败,请检查是否登录账户"),
    file_download_error(-1234,"文件没有找到或者下载出错"),
    plz_login(56,"请登录后在操作"),
    admin_search_error(54,"请选择搜索类型"),
    no_fans(55,"还有没有关注的哦!"),
    pwd_change_error(656,"密码修改错误!请排查是否存在新旧密码一样或者两次密码不一致"),
    admin_download_error(12,"当前用户无法访问或者参数输入有误"),login_max(99,"登录错误次数太多")
    ,ver_code_error(98,"请求图形验证码失败，请重试!"),ver_code_verif_error(97,"滑块验证错误");



    private Integer status;
    private String msg;

    @Override
    public String toString() {
        return "ResultStatus{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ResultStatus(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
