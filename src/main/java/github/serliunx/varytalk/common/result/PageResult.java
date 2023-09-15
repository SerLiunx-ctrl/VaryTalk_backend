package github.serliunx.varytalk.common.result;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageResult<T>{

    private Integer pageNum;
    private Integer pageSize;
    private Long actualSize;
    private Integer pages;
    private Long total;
    private List<T> rows;

    public PageResult(Integer pageNum, Integer pageSize, Long actualSize, Integer pages, Long total, List<T> rows) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.actualSize = actualSize;
        this.pages = pages;
        this.total = total;
        this.rows = rows;
    }

    public static <T> PageResult<T> page(PageInfo<T> pageInfo){
        return new PageResult<>(pageInfo.getPageNum(), pageInfo.getPageSize(),
                (long) pageInfo.getSize(), pageInfo.getPages(), pageInfo.getTotal(),pageInfo.getList());
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getActualSize() {
        return actualSize;
    }

    public void setActualSize(Long actualSize) {
        this.actualSize = actualSize;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
