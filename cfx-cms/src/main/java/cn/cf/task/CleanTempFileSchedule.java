package cn.cf.task;

import java.io.File;
import java.text.ParseException;

import javax.servlet.ServletContext;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Component
@EnableScheduling
public class CleanTempFileSchedule {



	/**
	 * 每个月定时清理临时文件
	 * @throws ParseException 
	 */
	@Scheduled(cron = "0 30 0 21 * ?")
	//@Scheduled(cron = "0 0/5 * * * ?")
	public void countCustomerApprove() throws ParseException {

		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
		delTempFile(servletContext.getRealPath("temp"));
	}
	private void delTempFile(String tempPath) {
		File file = new File(tempPath);
		if (file.exists()) {
			String[] tempList = file.list();
			File temp = null;
			for (int i = 0; i < tempList.length; i++) {
				temp = new File(tempPath + File.separator + tempList[i]);
				if (temp.isFile()) {
					temp.delete();
				}
			}
		}
	}
}
