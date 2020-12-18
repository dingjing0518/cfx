package cn.cf.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cf.common.RestCode;
import cn.cf.exception.ArraySizeException;
import cn.cf.exception.FinanceRecordsNullException;
import cn.cf.exception.InvoiceNullException;
import cn.cf.exception.NetWorkException;
import cn.cf.exception.NoDataException;
import cn.cf.exception.OrderNullException;
import cn.cf.exception.ResponseErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String handleException(Exception e) {
		String result = RestCode.CODE_S999.toJson();
		if (e instanceof NetWorkException) {
			logger.error("NetWorkException", e);
			result = RestCode.CODE_I002.toJson();
		} else if (e instanceof NoDataException) {
			logger.error("NoDataException", e);
			result = RestCode.CODE_I002.toJson();
		} else if (e instanceof ArraySizeException) {
			logger.error("ArraySizeException", e);
			result = RestCode.CODE_I002.toJson();
		}

		else if (e instanceof FinanceRecordsNullException) {
			logger.error("FinanceRecordsNullException", e);
			result = RestCode.CODE_I002.toJson();
		}

		else if (e instanceof InvoiceNullException) {
			logger.error("InvoiceNullException", e);
			result = RestCode.CODE_I002.toJson();
		}

		else if (e instanceof OrderNullException) {
			logger.error("OrderNullException", e);
			result = RestCode.CODE_I002.toJson();
		}

		else if (e instanceof ResponseErrorException) {
			logger.error("ResponseErrorException", e);
			result = RestCode.CODE_I002.toJson();
		}

		logger.error("Exception", e);

		return result;
	}
}
