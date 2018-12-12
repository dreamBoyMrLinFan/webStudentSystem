package cn.itcast.pojo;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable{

	private int startIndex;//起始索引  需要计算出来
	
	private int pageSize;//一页显示条数 固定赋值
	
	private int pageNumber;//当前页  前台传递
	
	private List<T> list;//分页查询出来的数据
	
	private int totalRecord;//总记录数  需要查询出来
	
	private int totalPage;//总页数   Math.ceil

	public int getStartIndex() {
//		return startIndex;
		return startIndex = (this.getPageNumber()-1)*this.getPageSize();
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	
	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	
	
	public int getTotalPage() {
//		return totalPage;
		return totalPage = (this.getTotalRecord()%this.getPageSize()==0?(this.getTotalRecord()/this.getPageSize()):(this.getTotalRecord()/this.getPageSize()+1));
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public PageBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
