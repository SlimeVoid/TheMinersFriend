function getBurnTime() {
	if ( level == 0 )
		return 1600;
		
	var out = (Math.log(level+1)*2300)+1600;
	if ( out > 6400 )
		out = 6400;
		
	return Math.round(out);
}

function getBurnSpeed() {
	if ( level == 0 )
		return 200;
		
	var out = Math.log(1.47+(1.25/level))*100;
	if ( out < 50 )
		out = 50;
		
	return Math.round(out);
}

function getBurnWidth() {
	var out = level+1;
	if ( out > 9 )
		out = 9;
	
	return out;
}
