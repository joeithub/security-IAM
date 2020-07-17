package com.security.common.utils;
import com.security.exception.RequestException;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: tongq
 * @Date: 2020/3/20 14:30
 * @since：1.0.1
 */

@Slf4j
@Component
public class SendEmailUtil {
        // 发件人邮箱协议
        private static String protocol ;
        // 发件人邮箱协议
        private static String port ;
        // 发件人邮箱地址
        private static String from ;
        // 发件人称号，同邮箱地址
        private static String user ;
        // 发件人邮箱客户端授权码
        private static String password ;
        //发件人的邮箱服务器
        private static String mailHost ;
        //邮件内容
        private static String mailContent;
        //有效时间
        private static String validate;
        // 标题
        private static String title;
        /**
         * @param to
         * @param text
         * @param title
         */
        /* 发送验证信息的邮件 */
        public static boolean sendMail(String to, String text, String title) {
            String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern p = Pattern.compile(regEx1);
            if ( !p.matcher(to).matches()){
                throw new RequestException("403","邮箱格式不正确");
            }
            Properties props = new Properties();
// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
//            props.put("mail.smtp.host", mailHost);
            props.setProperty("mail.transport.protocol", getProtocol());
            props.put("mail.smtp.port", getPort());
            props.put("mail.smtp.host", getMailHost());
// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
            props.put("mail.smtp.auth", "true");
// 用刚刚设置好的props对象构建一个session
            MailSSLSocketFactory sf = null;
            try {
                sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            props.put("mail.smtp.ssl.socketFactory", sf);
            props.put("mail.smtp.ssl.enable", "true");

            Session session = Session.getDefaultInstance(props);
// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使用（你可以在控制台（console)上看到发送邮件的过程）
// session.setDebug(true);
// 用session为参数定义消息对象
            MimeMessage message = new MimeMessage(session);
            try {
// 加载发件人地址
                message.setFrom(new InternetAddress(getFrom()));
// 加载收件人地址
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
// 加载标题
                message.setSubject(title);
// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
                Multipart multipart = new MimeMultipart();
// 设置邮件的文本内容
                BodyPart contentPart = new MimeBodyPart();
                contentPart.setContent(text, "text/html;charset=utf-8");
                multipart.addBodyPart(contentPart);
                message.setContent(multipart);
                message.saveChanges(); // 保存变化
// 连接服务器的邮箱
                Transport transport = session.getTransport("smtp");
// 把邮件发送出去
                transport.connect(getMailHost(), getUser(), getPassword());
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                log.info("邮件发送成功");
            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        @Value("${email.protocol}")
        public void setProtocol(String protocal){
            SendEmailUtil.protocol = protocal;
        }
        public static String getProtocol(){
            return protocol;
        }
        @Value("${email.port}")
        public void setPort(String port){
            SendEmailUtil.port = port;
        }
        public static String getPort(){
            return port;
        }
        @Value("${email.from}")
        public void setFrom(String from){
            SendEmailUtil.from = from;
        }
        public static String getFrom(){
            return from;
        }
        @Value("${email.user}")
        public void setUser(String user){
            SendEmailUtil.user =user;
        }
        public static String getUser(){
            return user;
        }
        @Value("${email.authCode}")
        public void setPassword(String password){
            SendEmailUtil.password = password;
        }
        public static String getPassword(){
            return password;
        }
        @Value("${email.mailHost}")
        public void setMailHost(String mailHost){
            SendEmailUtil.mailHost = mailHost;
        }
        public static String getMailHost(){
            return mailHost;
        }
        @Value("${email.mailContent}")
        public void setMailContent(String mailContent){
            SendEmailUtil.mailContent = mailContent;
        }
        public static String getMailContent(){
            return mailContent;
        }
        @Value("${email.validate}")
        public void setValidate(String validate){
            SendEmailUtil.validate = validate;
        }
        public static String getValidate(){
            return validate;
        }
        @Value("${email.title}")
        public void setTitle(String title){
            SendEmailUtil.title = title;
        }
        public static String getTitle(){
            return title;
        }
}
