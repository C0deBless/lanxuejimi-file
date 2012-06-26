package com.tech.actions;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.io.FileUtils;
import com.io.InputStreamProvider;
import com.opensymphony.xwork2.ActionSupport;
import com.tech.bll.ConfigurationService;

//@InterceptorRef(value="fileUpload")
@Namespace(value="/file")
public class File extends ActionSupport {
	private java.io.File upload;
	public java.io.File getUpload() {
		return upload;
	}
	public void setUpload(java.io.File upload) {
		this.upload = upload;
	}
	private String uploadFileName;
	private String uploadContentType;
	private InputStream output;
	private static final long serialVersionUID = 1L;
	
	@Action(value="upload",params={},results={
			@Result(name="success",type="stream",params={"contentType","text/plain","inputName","output","bufferSize","1024"})//"filename","{1}","filedata","{0}",
	})
	public String upload() throws  IOException, FileUploadException{
		String savePath=ServletActionContext.getServletContext().getRealPath(ConfigurationService.getConfig("filepath")) + "\\";
		String relativePath=ServletActionContext.getServletContext().getContextPath()+ConfigurationService.getConfig("filepath")+"/";
		String newName;
		HttpServletRequest request=ServletActionContext.getRequest();
		
		if ("application/octet-stream".equals(request.getContentType())){
			String fileDisposition=request.getHeader("Content-Disposition");
			String fileExt=fileDisposition.substring(fileDisposition.lastIndexOf("."),fileDisposition.length()-1);
			int i = request.getContentLength();
	        byte buffer[] = new byte[i];
	        int j = 0;
	        while(j < i) { //获取表单的上传文件
	            int k = request.getInputStream().read(buffer, j, i-j);
	            j += k;
	        }
	        newName=UUID.randomUUID().toString().replace("-", "") + fileExt;
	        savePath+=newName;
	        FileUtils.save(buffer, savePath);
		}
		else{
			String fileName=this.getUploadFileName();
			String fileExt=fileName.substring(fileName.lastIndexOf("."),fileName.length());
			newName=UUID.randomUUID().toString().replace("-", "") + fileExt;
			savePath+=newName;
			FileUtils.save(this.upload,savePath);
		}
		this.output=InputStreamProvider.getPlainTextInputStream("{\"err\":\"\",\"msg\":\"!"+relativePath+newName+"\"}");
		return "success";
	}
	public void setOutput(InputStream output) {
		this.output = output;
	}
	public InputStream getOutput() {
		return output;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	
}
