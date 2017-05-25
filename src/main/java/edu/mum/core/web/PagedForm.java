package edu.mum.core.web;

@SuppressWarnings("serial")
public class PagedForm {
	private int _pageNumber = 0;

	private int _pageSize = 0;

	public int get_pageNumber() {
		if(_pageNumber<1){
			return 1;
		}
		return _pageNumber;
	}

	public void set_pageNumber(int number) {
		_pageNumber = number;
	}

	public int get_pageSize() {
		return _pageSize;
	}

	public void set_pageSize(int size) {
		_pageSize = size;
	}

}
