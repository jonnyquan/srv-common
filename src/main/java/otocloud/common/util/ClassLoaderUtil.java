package otocloud.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ClassLoaderUtil {

	/**
	 * 　*得到类加载器 　*@return 　
	 */
	public static ClassLoader getClassLoader() {
		return ClassLoaderUtil.class.getClassLoader();
	}

	/**
	 * 　*提供相对于classpath的资源路径，返回文件的输入流
	 * 　*@paramrelativePath必须传递资源的相对路径。是相对于classpath的路径
	 * 。如果需要查找classpath外部的资源，需要使用　../来查找 　*@return 文件输入流 　*@throwsIOException 　*@throwsMalformedURLException
	 * 　
	 */
	public static InputStream getStream(String relativePath)
			throws MalformedURLException, IOException {
		if (!relativePath.contains("../")) {
			return getClassLoader().getResourceAsStream(relativePath);
		} else {
			return ClassLoaderUtil.getStreamByExtendResource(relativePath);
		}
	}

	/**
	 * 　* 　*@paramurl 　*@return 　*@throwsIOException 　
	 */
	public static InputStream getStream(URL url) throws IOException {
		if (url != null) {
			return url.openStream();
		} else {
			return null;
		}
	}

	/**
	 * 　* 　*@paramrelativePath必须传递资源的相对路径。是相对于classpath的路径。如果需要查找classpath外部的资源，
	 * 需要使用　../来查找 　*@return 　*@throwsMalformedURLException 　*@throwsIOException
	 * 　
	 */
	public static InputStream getStreamByExtendResource(String relativePath)
			throws MalformedURLException, IOException {
		return ClassLoaderUtil.getStream(ClassLoaderUtil
				.getExtendResource(relativePath));
	}

	/**
	 * 　*得到本Class所在的ClassLoader的Classpat的绝对路径。 　*URL形式的 　*@return 　
	 */
	public static String getAbsolutePathOfClassLoaderClassPath() {
		return ClassLoaderUtil.getClassLoader().getResource("").toString();
	}

	/**
	 * 　* 　*@paramrelativePath
	 * 必须传递资源的相对路径。是相对于classpath的路径。如果需要查找classpath外部的资源，需要使　用../来查找 　*@return资源的绝对URL
	 * 　*@throwsMalformedURLException 　
	 */
	public static URL getExtendResource(String relativePath) throws MalformedURLException {
		if (!relativePath.contains("../")) {
			return ClassLoaderUtil.getResource(relativePath);
		}
		String classPathAbsolutePath = ClassLoaderUtil
				.getAbsolutePathOfClassLoaderClassPath();
		if (relativePath.substring(0, 1).equals("/")) {
			relativePath = relativePath.substring(1);
		}
		String wildcardString = relativePath.substring(0,
				relativePath.lastIndexOf("../") + 3);
		relativePath = relativePath
				.substring(relativePath.lastIndexOf("../") + 3);
		int containSum = ClassLoaderUtil.containSum(wildcardString, "../");
		classPathAbsolutePath = ClassLoaderUtil.cutLastString(
				classPathAbsolutePath, "/", containSum);
		String resourceAbsolutePath = classPathAbsolutePath + relativePath;
		URL resourceAbsoluteURL = new URL(resourceAbsolutePath);
		return resourceAbsoluteURL;
	}

	/**
	 * 　* 　*@paramsource 　*@paramdest 　*@return 　
	 */
	private static int containSum(String source, String dest) {
		int containSum = 0;
		int destLength = dest.length();
		while (source.contains(dest)) {
			containSum = containSum + 1;
			source = source.substring(destLength);
		}
		return containSum;
	}

	/**
	 * 　* 　*@paramsource 　*@paramdest 　*@paramnum 　*@return 　
	 */
	private static String cutLastString(String source, String dest, int num) {
		// String cutSource=null;
		for (int i = 0; i < num; i++) {
			source = source.substring(0,
					source.lastIndexOf(dest, source.length() - 2) + 1);
		}
		return source;
	}

	/**
	 * 　* 　*@paramresource 　*@return 　
	 */
	public static URL getResource(String resource) {
		return ClassLoaderUtil.getClassLoader().getResource(resource);
	}

}
