/*
 * UseDemo.java
 *
 * Copyright (c) 2014 Chengdu Lanjing Data&Information Co., Ltd
 */


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class UseDemo {
	
	public static void main(String[] args) throws IOException /*throws IOException*/ {


	/*	if( !isContentURL(args[0]) ) {
			System.out.println("输入的网页不是主题型网页，无法进行正文提取\n");
			return;
		}*/

		/* 注意：本处只为展示抽取效果，不处理网页编码问题，getHTML只能接收GBK编码的网页，否则会出现乱码 */
		/*编码问题还有待解决*/

		//System.getProperties().setProperty("httpclient.useragent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0");

		String content = new HttpClientWork().getGetResponseWithHttpClient("http://www.sohu.com");


		Document doc = Jsoup.parse(content);

		Elements body=doc.select("body");
		Elements links = doc.select("a[href]");


		String temp4=links.html();

		long nBody=body.html().length();
		long nLinks=links.html().length();


		if(nLinks>nBody/10+3000){  //单纯的比例也不靠谱,还是综合比例和数量级(即不过原点的一阶拟合)
			System.out.println("该网页为非主题型,无法抽取正文");
			return;
		}

		//String content = new UseDemo().getHTML(args[0]);
		String html = new TextExtract().parse(content);
		System.out.println(html);
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

/*	*//**
	 * Checks if is content url.
	 *
	 * @param url the url
	 *
	 * @return true, if is content url
	 *//*
	private static boolean isContentURL(String url) {
		int count = 0;
		for( int i=0; i < url.length()-1 && count < 3; i++ ) {
			if(url.charAt(i) == '/' )
				count++;
		}

		return count > 2;
	}


	public String getHTML(String strURL) throws IOException {
		URL url = new URL(strURL);
		BufferedInputStream in =  new BufferedInputStream(url.openStream());


		byte[] bytes = new byte[1024000];
		int len = -1;
		int pos = 0;
		while ((len = in.read(bytes, pos, bytes.length - pos)) != -1) {
			pos += len;
		}
		
		detectCharset(bytes); //通过查看html中的编码参数来确定编码。以后对于存在更复杂的编码问题，可加入Firefox的方案
		
		String html = null;
		if (detCharset != null)
		{
			html = new String(bytes, 0, pos, this.detCharset);
		}
		else
		{
			return new String(bytes, 0, pos);
		}
//		System.out.println("Detcharset = " + detCharset);
		return html;
	}
	
	private String detCharset = null;

	private Pattern pGB2312 = Pattern.compile("GB2312", Pattern.CASE_INSENSITIVE);
	private Pattern pUTF8 = Pattern.compile("(UTF8)|(UTF-8)", Pattern.CASE_INSENSITIVE);
	
	public void detectCharset(byte[] content)
	{
		String html = new String(content); 
		Matcher m = pGB2312.matcher(html);
		if (m.find())
		{
			detCharset = "gb2312";
			return ;
		}
		m = pUTF8.matcher(html);
		if (m.find())
		{
			detCharset = "utf-8";
			return;
		}
		
		int lang = nsPSMDetector.ALL;
		nsDetector det = new nsDetector(lang);
		det.Init(new nsICharsetDetectionObserver() {
			public void Notify(String charset) {
				detCharset = charset;
			} 
		});
		boolean isAscii = true;

		if (isAscii)
			isAscii = det.isAscii(content, content.length);

		if (!isAscii)
			det.DoIt(content, content.length, false);

		det.DataEnd();

		boolean found = false;
		if (isAscii) {
			this.detCharset = "US-ASCII";
			found = true;
		}

		if (!found && detCharset == null) {
			detCharset = det.getProbableCharsets()[0];
		}
	}*/
}