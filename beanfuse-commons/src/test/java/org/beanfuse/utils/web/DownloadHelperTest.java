package org.beanfuse.utils.web;

import java.io.File;
import java.net.URL;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.annotations.Test;

@Test
public class DownloadHelperTest {

	public void download() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		URL testDoc= DownloadHelperTest.class.getClassLoader().getResource("test.doc");
		File file= new File(testDoc.getPath());
		DownloadHelper.download(request, response, file);
	}
}
