package cn.mpsit.StudentDemo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mpsit.StudentDemo.bean.FastDFSFile;
import cn.mpsit.StudentDemo.utils.FastDFSUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;



@Controller
@RequestMapping(value = "/he")
public class FileController {
	@Value("${FastHost}")
	private String FastHost;

	/**
	 * 用springMVC + common-IO 实现文件下载
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/download.do")
	public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// String path=request.getContextPath()+File.separator+"download";
		String path = request.getServletContext().getRealPath("download");
		System.out.println(path);
		String filename = "bg.jpeg";
		File file = new File(path, filename);
		HttpHeaders headers = new HttpHeaders();
		String downloadFielName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
		// 设置响应头
		headers.setContentDispositionFormData("attachment", downloadFielName);
		HttpStatus status = HttpStatus.OK;
		byte[] body = FileUtils.readFileToByteArray(file);
		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, status);
		return entity;

	}

	/**
	 * Spring + Common—IO 实现文件上传
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/fileupload.do")
	public String getFile(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		try {
			String originalFileName = file.getOriginalFilename();
			String path = request.getSession().getServletContext().getRealPath("WEB-INF/upload/");
			File dir = new File(path, originalFileName);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			file.transferTo(dir);
		} catch (IllegalStateException e) {
			// 这边捕捉不到文件过大的异常
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	@RequestMapping("/down")
	public void down(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 模拟文件，myfile.txt为需要下载的文件
		String fileName = request.getSession().getServletContext().getRealPath("upload") + "/myfile.txt";
		// 获取输入流
		InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
		// 假如以中文名下载的话
		String filename = "下载文件.txt";
		// 转码，免得文件名中文乱码
		filename = URLEncoder.encode(filename, "UTF-8");
		// 设置文件下载头
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		int len = 0;
		while ((len = bis.read()) != -1) {
			out.write(len);
			out.flush();
		}
		out.close();
	}

	@RequestMapping(value = "/uploadfile.do", method = RequestMethod.POST)
	public String upload(MultipartFile file) {
		try {
			FastDFSFile fastDFSFile = new FastDFSFile(file.getBytes(), file.getOriginalFilename(), file.getSize());
			String path = FastDFSUtils.uploadFile(fastDFSFile);
			String imgPath = FastHost + "/" + path;
			// System.out.println(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
