package cn.cf;



import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页模型，主要用于存储查询到的数据的分页信息。 <br>
 * 以及从 dao -> service -> controler 传递硬加载入的数据。 br 可以存储json，html等数据，便于从dao获取数据，
 * 在service进行处理返回到 controler直接呈现，避免在controler进行过多的业务处理。
 */
public class PageModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public PageModel() {
	}
	
	public PageModel(List<T> dataList) {
		this.setDataList(dataList);
	}
	
	public PageModel(List<T> dataList, int totalCount) {
		this.setDataList(dataList);
		this.setTotalCount(totalCount);
	}
	// 存储数据记录
	private List<T> dataList;
	
	// 针对 oracle 等查询数据库时，记录开始的索引位置。如果set 该值，会算出pageNo
	private int startIndex;
	
	private String pageName;//页面名称
	
	// 当前页号，第几页，如果 set 该值，则自动算出 startIndex 值。
	private int pageNo;
	
	// 页面大小,limit
	private int pageSize;
	
	// 总记录数
	private int totalCount;
	
	// 总页数
	private int totalPage;
	
	// 前一页
	private int prevPage;
	
	// 后一页
	private int nextPage;
	
	// 最后一页
	private int lastPage;
	
	// 首页
	private int firstPage;
	
	// 上一组索引
	private int prevIndex;
	// 下一组索引
	private int nextIndex;
	
	// 单独对象。
	private Object object;
	
	// 记录 已经做好的 JSON 字符串数据
	private String dataJson;
	
	// 记录 已经做好的 html 字符串数据
	private String dataHtml;
	
	// 记录 已经做好的 json 数据集。
	private List<String> jsonList;
	
	// 其他需要存放的数据
	private List<Object> otherData;
	//其他需求存放的map
	private Map<String,Object> otherMap;
	public static int[] getStartEnd(int pageNo, int pageSize) {
		int start = (pageNo - 1) * pageSize;
		int end = start + pageSize - 1;
		return new int[] { start, end };
	}
	
	public int getPageNo() {
		if (this.pageNo <= 0) {
			if (this.startIndex >= 0) {
				if(this.pageSize>0){
				this.pageNo = this.startIndex / this.pageSize + 1;
				}else{
					this.pageNo =1;
				}
			} else {
				this.pageNo = 1;
			}
		}
		if (this.pageNo >= getTotalPage()) {
			this.pageNo = getTotalPage();
		}
		return this.pageNo;
	}
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public Map<String, Object> getOtherMap() {
		return otherMap;
	}

	public void setOtherMap(Map<String, Object> otherMap) {
		this.otherMap = otherMap;
	}

	public List<T> getDataList() {
		return dataList;
	}
	
	public int getStartIndex() {
		if (this.startIndex >= this.totalCount) {
			this.startIndex = this.totalCount - this.pageSize;
		}
		if (this.startIndex <= 0) {
			this.startIndex = 0;
		}
		return startIndex;
	}
	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
	public int getPrevPage() {
		prevPage = pageNo - 1;
		if (prevPage < 1) {
			prevPage = 1;
		}
		if (prevPage > getTotalPage()) {
			prevPage = getTotalPage();
		}
		return prevPage;
	}
	
	public int getNextPage() {
		nextPage = pageNo + 1;
		if (nextPage > getTotalPage()) {
			nextPage = getTotalPage();
		}
		if (nextPage < 1) {
			nextPage = 1;
		}
		return nextPage;
	}
	
	public int getLastPage() {
		lastPage = getTotalPage();
		return lastPage;
	}
	
	public int getFirstPage() {
		firstPage = 1;
		return firstPage;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getTotalPage() {
		if (pageSize == 0) {
			return 0;
		}
		totalPage = (totalCount + pageSize - 1) / pageSize;
		return totalPage;
	}
	
	public int getPrevIndex() {
		this.prevIndex = getStartIndex() - this.getPageSize();
		if (this.prevIndex <= 0) {
			this.prevIndex = 0;
		}
		return prevIndex;
	}
	
	public int getNextIndex() {
		this.nextIndex = getStartIndex() + this.getPageSize();
		if (this.nextIndex >= this.getTotalCount()) {
			this.nextIndex = this.nextIndex - this.getPageSize();
		}
		return nextIndex;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
	
	public List<String> getJsonList() {
		return jsonList;
	}
	
	public void setJsonList(List<String> jsonList) {
		this.jsonList = jsonList;
	}
	
	public List<Object> getOtherData() {
		return otherData;
	}
	
	public void setOtherData(List<Object> otherData) {
		this.otherData = otherData;
	}
	
	public String getDataJson() {
		return dataJson;
	}
	
	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}
	
	public String getDataHtml() {
		return dataHtml;
	}
	
	public void setDataHtml(String dataHtml) {
		this.dataHtml = dataHtml;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	
}

