package com.adv.pagin;

import java.util.List;

public class GetAllRequest {
	private int page;
	private int size;

	private Sort sort;

	private DateSearch date;
	private List<Search> search;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Sort getSort() {
		return sort;
		
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public DateSearch getDate() {
		return date;
	}

	public void setDate(DateSearch date) {
		this.date = date;
	}

	public List<Search> getSearch() {
		return search;
	}

	public void setSearch(List<Search> search) {
		this.search = search;
	}

}
