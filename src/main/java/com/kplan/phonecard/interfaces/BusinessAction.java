package com.kplan.phonecard.interfaces;

import com.kplan.phonecard.vo.ResultMessageDTO;

public interface BusinessAction<T> {
	ResultMessageDTO<T> action();
}
