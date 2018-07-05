package cn.mpsit.StudentDemo.controller;

import cn.mpsit.StudentDemo.bean.UserBean;
import cn.mpsit.StudentDemo.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 账号登录
     *
     * @param request
     * @param response
     * @return json <br> code 值：200：登录成功；400：用户名密码错误，401 ：输入无效
     */
    @RequestMapping(value = "/login.do", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String LogIn(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserBean user = null;
        JSONObject result = new JSONObject();
        if (username != null && password != null) {
            user = userService.logIn(username, password);
            if (user != null) {
                request.getSession().setAttribute("stuid", user.getStudentid());
                result.put("code", 200);
                result.put("msg", "登录成功");
            } else {
                result.put("code", 400);
                result.put("msg", "用户名或密码错误");
            }
        } else {
            result.put("code", 401);
            result.put("msg", "您输入的账户密码有误");
        }
        return result.toJSONString();
    }

    @RequestMapping(value = "/logout.do", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String logOut(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        JSONObject result = new JSONObject();
        result.put("code", 201);
        result.put("msg", "您已成功退出");
        return result.toJSONString();
    }


    @RequestMapping(value = "/tostuMag.do")
    public String toStuManager(HttpServletRequest request, HttpServletResponse response) {
        return "regist";
    }

    @RequestMapping(value = "/download.do")
    public ResponseEntity<byte[]> download(HttpServletRequest request) {
        String filename = request.getParameter("fileName");
        byte[] body = null;
        String path = request.getSession().getServletContext().getRealPath("video");
        System.out.println(path);
        //String filename="bg.jpeg";
        File file = new File(path, filename);
        HttpHeaders headers = new HttpHeaders();//http头信息
        String downloadFileName = null;//设置编码
        ResponseEntity<byte[]> entity = null;
        try {
            headers.setContentDispositionFormData("attachment", filename);
//       二进制流，不知道下载文件类型（这样设置 如果是.exe文档会报错）
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            InputStream inputStream = new FileInputStream(file);
            body = new byte[inputStream.available()];
            entity = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;
//        return  null;
    }

}
