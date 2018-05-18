package com.platform.oss;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.http.entity.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 名称：QcloudCloudStorageServiceTest <br>
 * 描述：上传到腾讯云单元测试<br>
 * 注意事项：需要根据腾讯云的配置说明进行配置
 *
 * @author 李鹏军
 * @version 1.0
 * @since 1.0.0
 * 
 * 张鹏起修改
 */
public class QcloudCloudStorageServiceTest extends baseOss {
	static Logger logger = Logger.getLogger(QcloudCloudStorageServiceTest.class.getName());

	// 定义一个上传文件的例子
	String uploadfile;

	// 上传后的路径
	String uploadPath;
	// 定义测试的这个对象
	QcloudCloudStorageService QcloudStorage;

	@Before
	public void setUp() throws Exception {
		if(skipTest) {
			assertTrue(skipTest);
			return;
		}
		init();
		// 配置为腾讯云存储
		config.setType(3);

		QcloudStorage = new QcloudCloudStorageService(config);

		String prefix = updateFilePath.substring(updateFilePath.lastIndexOf(".") + 1);
		// 获得本次上传后的路径，是为了测试完毕后打扫战场
		// 例如路径: /images/demo/20180425/2345044164a069.png
		uploadPath = QcloudStorage.getPath(config.getQcloudPrefix()) + "." + prefix;

		uploadfile = this.getClass().getClassLoader().getResource(updateFilePath).getFile().toString();

	}

	@After
	public void tearDown() throws Exception {
		
		if(!skipTest) {
		// 每次干完活记得打扫战场，不要留下脏数据
		logger.info("删除："+uploadPath);
		QcloudStorage.delete(uploadPath);
		}
	}

	@Test
	public void testUploadMultipartFile() {
		if(skipTest) {
			assertTrue(skipTest);
			return;
		}
		String result = "";
		
		File file = new File(uploadfile);
		if (!file.exists()) {
			fail("not found " + uploadfile);
		}
		try {
			MultipartFile newfile = new MockMultipartFile(file.getName(), 
					file.getName(),
					ContentType.APPLICATION_OCTET_STREAM.getMimeType(),
					new FileInputStream(file)
					);
			result = QcloudStorage.upload(newfile);
		} catch (FileNotFoundException e) {
			logger.info("FileNotFoundException!==" + e.getMessage());
			fail("FileNotFoundException");
		} catch (IOException e) {
			logger.info("IOException!==" + e.getMessage());
			fail("IOException");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("QcloudStorage!==" + e.getMessage());
			fail("QcloudStorage,Exception");
		}
		assertFalse(result.isEmpty());
		if (!result.isEmpty()) {
			//因为 upload(MultipartFile file)方法是随即产生的路径，所以在这里重新处理以下，为了打扫战场
			uploadPath=result.replaceFirst(config.getQcloudDomain(), "");
			
			logger.info("上传后URL:"+result);
		}

	}

	@Test
	public void testUploadInputStreamString() {
		
		if(skipTest) {
			assertTrue(skipTest);
			return;
		}
		String result = "";

		File file = new File(uploadfile);

		if (!file.exists()) {
			fail("not found " + uploadfile);
		}

		try {
			result = QcloudStorage.upload(new FileInputStream(file), uploadPath);
		} catch (FileNotFoundException e) {
			logger.info("FileNotFoundException!==" + e.getMessage());
			fail("FileNotFoundException");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("QcloudStorage!==" + e.getMessage());
			fail("QcloudStorage,Exception");
		}
		logger.info("上传后URL:"+result);
		assertFalse(result.isEmpty());

	}

}
