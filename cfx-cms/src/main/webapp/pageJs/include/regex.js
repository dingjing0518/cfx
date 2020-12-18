var amount = /^\d+(\.\d+)?$/;

function regex(str){
	if(amount.test(str)){
		return true;
	}
	return false;
}