package com.taotao.rest.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {

	//添加文档
	@Test
	public void addDocument() throws Exception{
		//创建一个连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.244.128:8080/solr");
		//创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title", "测试商品2");
		document.addField("item_price", 222);
		//把文档对象写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	//删除文档
	@Test
	public void deleteDocument() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.244.128:8080/solr");
		solrServer.deleteById("test001");
//		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
}
